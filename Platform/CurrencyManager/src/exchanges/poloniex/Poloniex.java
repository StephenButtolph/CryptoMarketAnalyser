package exchanges.poloniex;

import java.lang.reflect.Type;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;

import org.apache.http.HttpResponse;

import com.google.gson.reflect.TypeToken;

import arithmetic.Pfloat;
import constants.Json;
import currencies.Currency;
import currencyExchanges.CurrencyMarket;
import exchanges.BestEffortExchange;
import exchanges.Exchange;
import holdings.Holding;
import offerGroups.Offers;
import orders.Order;
import transactions.Transaction;
import utils.IterableUtils;
import utils.WebUtils;

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

		HttpResponse response = WebUtils.getRequest(Constants.PUBLIC_URL, parameters);
		String json = WebUtils.getJson(response);

		Type topType = new TypeToken<Map<String, Object>>() {
		}.getType();
		Type subType = new TypeToken<Map<String, String>>() {
		}.getType();
		Map<String, Object> map = Json.GSON.fromJson(json, topType);

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

		return IterableUtils.fold(map.entrySet(), f, Pfloat.ZERO);
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
	public Collection<Order> getOpenOrders(CurrencyMarket market) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Transaction> getTradeHistory(CurrencyMarket market) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pfloat getBalance(Currency currency) {
		Map<String, String> parameters = Utils.getDefaultPostParameters();
		parameters.put(Constants.COMMAND, Constants.RETURN_BALANCES);

		Map<?, ?> headers = Utils.makeRequestHeaders(auth, parameters);
		HttpResponse response = WebUtils.postRequest(Constants.TRADING_URL, headers, parameters);
		String json = WebUtils.getJson(response);

		Type type = new TypeToken<Map<String, String>>() {
		}.getType();
		Map<String, String> map = Json.GSON.fromJson(json, type);

		String amount = map.get(currency.getSymbol());
		if (amount == null) {
			return null;
		}
		return new Pfloat(amount);
	}

	@Override
	public boolean sendFundsTo(Exchange exchange, Holding holding) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getWalletAddress(Currency currency) {
		Map<String, String> returnParameters = Utils.getDefaultPostParameters();
		returnParameters.put(Constants.COMMAND, Constants.RETURN_DEPOSIT_ADDRESSES);

		Map<?, ?> returnHeaders = Utils.makeRequestHeaders(auth, returnParameters);
		HttpResponse returnResponse = WebUtils.postRequest(Constants.TRADING_URL, returnHeaders, returnParameters);
		String returnJson = WebUtils.getJson(returnResponse);

		Type returnType = new TypeToken<Map<String, String>>() {
		}.getType();
		Map<String, String> returnMap = Json.GSON.fromJson(returnJson, returnType);

		String address = returnMap.get(currency.getSymbol());
		if (address == null) {
			return makeWalletAddress(currency);
		}
		return address;
	}

	private String makeWalletAddress(Currency currency) {
		Map<String, String> generateParameters = Utils.getDefaultPostParameters();
		generateParameters.put(Constants.COMMAND, Constants.GENERATE_NEW_ADDRESS);
		generateParameters.put(Constants.CURRENCY, currency.getSymbol());

		Map<?, ?> generateHeaders = Utils.makeRequestHeaders(auth, generateParameters);
		HttpResponse generateResponse = WebUtils.postRequest(Constants.TRADING_URL, generateHeaders,
				generateParameters);
		String generateJson = WebUtils.getJson(generateResponse);

		Type generateType = new TypeToken<Map<String, String>>() {
		}.getType();
		Map<String, String> generateMap = Json.GSON.fromJson(generateJson, generateType);

		return generateMap.get(Constants.RESPONSE);
	}

	@Override
	public Collection<CurrencyMarket> getCurrencyMarkets() {
		Map<String, String> parameters = Utils.getDefaultGetParameters();
		parameters.put(Constants.COMMAND, Constants.RETURN_TICKER);

		HttpResponse response = WebUtils.getRequest(Constants.PUBLIC_URL, parameters);
		String json = WebUtils.getJson(response);

		Type type = new TypeToken<Map<String, ?>>() {
		}.getType();
		Map<String, ?> map = Json.GSON.fromJson(json, type);

		Iterable<CurrencyMarket> marketIter = IterableUtils.map(map.keySet(), Utils::parseMarket);

		Collection<CurrencyMarket> markets = new HashSet<>();
		marketIter.forEach(market -> {
			if (market != null) {
				markets.add(market);
				markets.add(market.invert());
			}
		});
		return markets;
	}

	@Override
	public Order buy(Pfloat toSpend, CurrencyMarket market, Pfloat price) {
		// TODO Auto-generated method stub
		return null;
	}
}
