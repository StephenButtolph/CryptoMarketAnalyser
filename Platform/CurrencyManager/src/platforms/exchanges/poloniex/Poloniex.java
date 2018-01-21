package platforms.exchanges.poloniex;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.http.HttpResponse;

import arithmetic.Pfloat;
import constants.Json;
import platforms.currencies.Currency;
import platforms.currencies.markets.CurrencyMarket;
import platforms.exchanges.BestEffortExchange;
import platforms.exchanges.Exchange;
import platforms.holdings.Holding;
import platforms.offers.offerGroups.Offers;
import utils.arithmetic.ArithmeticUtils;
import utils.collections.iterables.IterableUtils;
import utils.types.TypeProducer;
import utils.types.TypeToken;
import utils.web.WebUtils;

/**
 * This is an exchange that is backed by the Poloniex API.
 * 
 * @author Stephen Buttolph
 */
public class Poloniex extends BestEffortExchange {
	private PoloniexAuth auth;

	/**
	 * Create a new Poloniex exchange with the given authorizations [auth].
	 * 
	 * @param auth
	 *            The authorizations to access the Poloniex API.
	 */
	public Poloniex(PoloniexAuth auth) {
		this.auth = auth;
	}

	@Override
	public Pfloat get24HVolume(Currency currency) {
		Map<String, String> parameters = Utils.getDefaultGetParameters();
		parameters.put(Constants.COMMAND, Constants.RETURN_24H_VOLUME);

		TypeProducer typeProducer = new TypeToken<Map<String, Object>>();

		Map<String, Object> response = sendGet(parameters, typeProducer);
		Type subType = new TypeToken<Map<String, String>>().getType();
		BiFunction<Pfloat, Entry<String, Object>, Pfloat> f = (acc, entry) -> {
			CurrencyMarket market = Utils.parseMarket(entry.getKey());
			if (market != null && market.contains(currency)) {
				Map<String, String> subMap = Json.GSON.fromJson(entry.getValue().toString(), subType);

				String strAmount = subMap.get(currency.getSymbol());
				Pfloat amount = new Pfloat(strAmount);
				return acc.add(amount);
			}
			return acc;
		};

		return IterableUtils.fold(response.entrySet(), f, Pfloat.ZERO);
	}

	@Override
	protected Offers getRawOffers(CurrencyMarket market) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Offers adjustOffers(Offers rawOffers) {
		// TODO
		// command = returnCurrencies
		// ^ has txFee
		return null;
	}

	@Override
	public Collection<PoloniexOpenOrderTemplate> getOpenOrders(CurrencyMarket market) {
		return getOpenOrdersMap(market).values();
	}

	private Map<String, PoloniexOpenOrderTemplate> getOpenOrdersMap(CurrencyMarket market) {
		boolean shouldInvert = shouldInvert(market);

		Map<String, String> parameters = new HashMap<>();
		parameters.put(Constants.COMMAND, Constants.RETURN_OPEN_ORDERS);
		parameters.put(Constants.CURRENCY_PAIR, Utils.toMarket(market));

		TypeProducer typeProducer = new TypeToken<Map<String, String>[]>();

		Map<String, String>[] response = sendPost(parameters, typeProducer);
		Iterable<Map<String, String>> responseIter = IterableUtils.toIterable(response);
		Iterable<PoloniexOpenOrder> ordersIter = IterableUtils.map(responseIter, order -> {
			CurrencyMarket curMarket;
			String orderNumber = order.get(Constants.ORDER_NUMBER);
			String price = order.get(Constants.RATE);
			String amountCurrency = order.get(Constants.TOTAL);
			String amountCommodity = order.get(Constants.AMOUNT);

			if (shouldInvert) {
				curMarket = market.invert();
				orderNumber = order.get(Constants.ORDER_NUMBER);
				price = order.get(Constants.RATE);

				Pfloat priceFloat = new Pfloat(order.get(Constants.RATE));
				price = Pfloat.ONE.divide(priceFloat).toString();
				amountCurrency = order.get(Constants.AMOUNT);
				amountCommodity = order.get(Constants.TOTAL);
			} else {
				curMarket = market;
				orderNumber = order.get(Constants.ORDER_NUMBER);
				price = order.get(Constants.RATE);
				amountCurrency = order.get(Constants.TOTAL);
				amountCommodity = order.get(Constants.AMOUNT);
			}

			return new PoloniexOpenOrder(curMarket, orderNumber, price, amountCurrency, amountCommodity);
		});

		Map<String, PoloniexOpenOrderTemplate> orders = new HashMap<>();
		ordersIter.forEach(order -> {
			orders.put(order.getOrderID(), order);
		});
		return null;
	}

	@Override
	public Collection<PoloniexClosedOrder> getTradeHistory(CurrencyMarket market) {
		Function<Map<String, String>, PoloniexClosedOrder> getBuys = order -> {
			if (Constants.BUY.equals(order.get(Constants.TYPE))) {
				String price = order.get(Constants.RATE);
				String amountCurrency = order.get(Constants.TOTAL);
				String amountCommodity = order.get(Constants.AMOUNT);

				// TODO ensure that tax isn't relevent and is already incorporated.

				return new PoloniexClosedOrder(market, price, amountCurrency, amountCommodity);
			}
			return null;
		};
		Function<Map<String, String>, PoloniexClosedOrder> getSells = order -> {
			if (Constants.SELL.equals(order.get(Constants.TYPE))) {
				// TODO change if needed
				String amountCommodity = order.get(Constants.TOTAL);
				String amountCurrency = order.get(Constants.AMOUNT);

				// TODO ensure that tax isn't relevent and is already incorporated.

				return new PoloniexClosedOrder(market, amountCurrency, amountCommodity);
			}
			return null;
		};

		if (shouldInvert(market)) {
			return getTradeHistory(market.invert(), getSells);
		} else {
			return getTradeHistory(market, getBuys);
		}
	}

	private Collection<PoloniexClosedOrder> getTradeHistory(CurrencyMarket market,
			Function<Map<String, String>, PoloniexClosedOrder> f) {
		Map<String, String> parameters = new HashMap<>();
		parameters.put(Constants.COMMAND, Constants.RETURN_TRADE_HISTORY);
		parameters.put(Constants.CURRENCY_PAIR, Utils.toMarket(market));
		parameters.put(Constants.START, Constants.MIN_START_TIME);
		parameters.put(Constants.LIMIT, Constants.MAX_RETURN_TRADE_HISTORY_LIMIT);

		TypeProducer typeProducer = new TypeToken<Map<String, String>[]>();

		Map<String, String>[] response = sendPost(parameters, typeProducer);

		Iterable<Map<String, String>> responseIter = IterableUtils.toIterable(response);
		Iterable<PoloniexClosedOrder> orderIter = IterableUtils.map(responseIter, f);
		orderIter = IterableUtils.filter(orderIter, order -> order != null);

		Collection<PoloniexClosedOrder> orders = new ArrayList<>();
		orderIter.forEach(orders::add);
		return orders;
	}

	@Override
	public Pfloat getBalance(Currency currency) {
		Map<String, String> parameters = new HashMap<>();
		parameters.put(Constants.COMMAND, Constants.RETURN_BALANCES);

		TypeProducer typeProducer = new TypeToken<Map<String, String>>();

		Map<String, String> response = sendPost(parameters, typeProducer);
		String amount = response.get(currency.getSymbol());
		if (amount == null) {
			return Pfloat.UNDEFINED;
		}
		return new Pfloat(amount);
	}

	@Override
	public boolean sendFundsTo(Exchange exchange, Holding holding) {
		Currency currency = holding.getCurrency();

		Map<String, String> parameters = new HashMap<>();
		parameters.put(Constants.COMMAND, Constants.WITHDRAW);
		parameters.put(Constants.CURRENCY, currency.getSymbol());
		parameters.put(Constants.AMOUNT, holding.getAmount().toString());
		parameters.put(Constants.ADDRESS, exchange.getWalletAddress(currency));

		TypeProducer typeProducer = new TypeToken<Map<String, ?>>();

		Map<String, ?> response = sendPost(parameters, typeProducer);
		return response.containsKey(Constants.RESPONSE);
	}

	@Override
	public String getWalletAddress(Currency currency) {
		Map<String, String> parameters = new HashMap<>();
		parameters.put(Constants.COMMAND, Constants.RETURN_DEPOSIT_ADDRESSES);

		TypeProducer typeProducer = new TypeToken<Map<String, String>>();

		Map<String, String> response = sendPost(parameters, typeProducer);
		String address = response.get(currency.getSymbol());
		if (address == null) {
			return makeWalletAddress(currency);
		}
		return address;
	}

	private String makeWalletAddress(Currency currency) {
		Map<String, String> parameters = new HashMap<>();
		parameters.put(Constants.COMMAND, Constants.GENERATE_NEW_ADDRESS);
		parameters.put(Constants.CURRENCY, currency.getSymbol());

		TypeProducer typeProducer = new TypeToken<Map<String, String>>();

		Map<String, String> response = sendPost(parameters, typeProducer);
		return response.get(Constants.RESPONSE);
	}

	@Override
	protected Collection<CurrencyMarket> getOriginalCurrencyMarkets() {
		Map<String, String> parameters = new HashMap<>();
		parameters.put(Constants.COMMAND, Constants.RETURN_TICKER);

		TypeProducer typeProducer = new TypeToken<Map<String, ?>>();

		Map<String, ?> response = sendGet(parameters, typeProducer);
		Iterable<CurrencyMarket> marketIter = IterableUtils.map(response.keySet(), Utils::parseMarket);
		marketIter = IterableUtils.filter(marketIter, market -> market != null);

		Collection<CurrencyMarket> markets = new HashSet<>();
		marketIter.forEach(markets::add);
		return markets;
	}

	@Override
	public PoloniexOpenOrderTemplate buy(Pfloat toSpend, CurrencyMarket market, Pfloat rate) {
		String command;

		if (shouldInvert(market)) {
			command = Constants.SELL;
			rate = Pfloat.ONE.divide(rate);
			market = market.invert();
		} else {
			command = Constants.BUY;
			toSpend = ArithmeticUtils.getReturn(toSpend, rate);
		}

		return placeOrder(command, market, rate, toSpend);
	}

	private PoloniexOpenOrderTemplate placeOrder(String command, CurrencyMarket market, Pfloat rate, Pfloat amount) {
		Map<String, String> parameters = new HashMap<>();
		parameters.put(Constants.COMMAND, command);
		parameters.put(Constants.CURRENCY, Utils.toMarket(market));
		parameters.put(Constants.RATE, rate.toString());
		parameters.put(Constants.AMOUNT, amount.toString());

		TypeProducer typeProducer = new TypeToken<Map<String, ?>>();

		Map<String, ?> response = sendPost(parameters, typeProducer);
		String orderNumber = response.get(Constants.ORDER_NUMBER).toString();

		return getOpenOrdersMap(market).get(orderNumber);
	}

	private class PoloniexOpenOrder extends PoloniexOpenOrderTemplate {
		private PoloniexOpenOrder(CurrencyMarket market, String price, String amountCurrency, String amountCommodity,
				String orderID) throws NumberFormatException {
			super(market, price, amountCurrency, amountCommodity, orderID);
		}

		private PoloniexOpenOrder(CurrencyMarket market, Pfloat price, Pfloat amountCurrency, Pfloat amountCommodity,
				String orderID) {
			super(market, price, amountCurrency, amountCommodity, orderID);
		}

		@Override
		public boolean isOpen() {
			return getOpenOrders(getMarket()).contains(this);
		}

		@Override
		public boolean cancel() {
			Map<String, String> parameters = new HashMap<>();
			parameters.put(Constants.COMMAND, Constants.CANCEL_ORDER);
			parameters.put(Constants.ORDER_NUMBER, this.getOrderID());

			TypeProducer typeProducer = new TypeToken<Map<String, Object>>();

			Map<String, Object> response = sendPost(parameters, typeProducer);
			return response.get(Constants.SUCCESS).equals(Constants.SUCCEEDED);
		}

		@Override
		public Collection<PoloniexClosedOrder> getCompleted() {
			Map<String, String> parameters = new HashMap<>();
			parameters.put(Constants.COMMAND, Constants.RETURN_ORDER_TRADES);
			parameters.put(Constants.ORDER_NUMBER, this.getOrderID());

			TypeProducer typeProducer = new TypeToken<Map<String, String>[]>();
			Map<String, String>[] response = sendPost(parameters, typeProducer);

			Collection<PoloniexClosedOrder> orders = new ArrayList<>();
			for (Map<String, String> order : IterableUtils.toIterable(response)) {
				String price;
				String amountCurrency;
				String amountCommodity;

				if (Constants.BUY.equals(order.get(Constants.TYPE))) {
					price = order.get(Constants.RATE);
					amountCurrency = order.get(Constants.AMOUNT);
					amountCommodity = order.get(Constants.RATE);
				} else {
					Pfloat inversePrice = new Pfloat(order.get(Constants.RATE));

					price = Pfloat.ONE.divide(inversePrice).toString();
					amountCurrency = order.get(Constants.RATE);
					amountCommodity = order.get(Constants.AMOUNT);
				}

				// TODO ensure no need to account for taxes

				PoloniexClosedOrder newOrder = new PoloniexClosedOrder(getMarket(), price, amountCurrency,
						amountCommodity);
				orders.add(newOrder);
			}

			return orders;
		}
	}

	private <T> T sendGet(Map<String, String> params, TypeProducer typeProducer) {
		Map<String, String> parameters = Utils.getDefaultGetParameters();
		parameters.putAll(params);

		HttpResponse response = WebUtils.getRequest(Constants.PUBLIC_URL, parameters);
		String json = WebUtils.getJson(response);

		T toReturn = null;
		try {
			toReturn = Json.GSON.fromJson(json, typeProducer.getType());
		} catch (com.google.gson.JsonSyntaxException e) {
		}
		return toReturn;
	}

	private <T> T sendPost(Map<String, String> params, TypeProducer typeProducer) {
		Map<String, String> parameters = Utils.getDefaultPostParameters();
		parameters.putAll(params);

		Map<?, ?> headers = Utils.makeRequestHeaders(auth, parameters);

		HttpResponse response = WebUtils.postRequest(Constants.TRADING_URL, headers, parameters);
		String json = WebUtils.getJson(response);

		System.out.println(json);

		T toReturn = null;
		try {
			toReturn = Json.GSON.fromJson(json, typeProducer.getType());
		} catch (com.google.gson.JsonSyntaxException e) {
		}
		return toReturn;
	}
}
