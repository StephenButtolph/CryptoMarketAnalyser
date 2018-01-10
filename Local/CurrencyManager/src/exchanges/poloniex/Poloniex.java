package exchanges.poloniex;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;

import org.apache.http.HttpResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import arithmetic.Pfloat;
import constants.Web;
import currencies.Currency;
import currencies.CurrencyFactory;
import currencies.CurrencyMarket;
import exchangeAuths.PoloniexAuth;
import exchanges.BestEffortExchange;
import exchanges.Exchange;
import holdings.Holding;
import offerGroups.Offers;
import orders.Order;
import transactions.Transaction;
import utils.IterableUtils;
import utils.SecurityUtils;
import utils.WebUtils;

public class Poloniex extends BestEffortExchange {
	private static final String COMMAND;
	private static final String CURRENCY;
	
	private static final String RETURN_TICKER;
	private static final String RETURN_24H_VOLUME;
	private static final String RETURN_BALANCES;
	private static final String RETURN_DEPOSIT_ADDRESSES;
	private static final String GENERATE_NEW_ADDRESS;

	private static final String RESPONSE;
	
	private static final String MARKET_DELIMITER;

	private static final Gson GSON;

	static {
		COMMAND = "command";
		CURRENCY = "currency";
		
		RETURN_TICKER = "returnTicker";
		RETURN_24H_VOLUME = "return24hVolume";
		RETURN_BALANCES = "returnBalances";
		RETURN_DEPOSIT_ADDRESSES = "returnDepositAddresses";
		GENERATE_NEW_ADDRESS = "generateNewAddress";

		RESPONSE = "response";
		
		MARKET_DELIMITER = "_";

		GSON = new Gson();
	}

	private PoloniexAuth auth;

	public Poloniex(PoloniexAuth auth) {
		this.auth = auth;
	}

	@Override
	public Pfloat get24HVolume(Currency currency) {
		Map<String, String> parameters = getDefaultGetParameters();
		parameters.put(COMMAND, RETURN_24H_VOLUME);

		HttpResponse response = WebUtils.getRequest(Web.POLONIEX_PUBLIC_URL, parameters);
		String json = WebUtils.getJson(response);

		Type topType = new TypeToken<Map<String, Object>>() {}.getType();
		Type subType = new TypeToken<Map<String, String>>() {}.getType();
		Map<String, Object> map = GSON.fromJson(json, topType);

		BiFunction<Pfloat, Entry<String, Object>, Pfloat> f = (acc, entry) -> {
			CurrencyMarket market = parseMarket(entry.getKey());
			if (market != null && market.contains(currency)) {
				Map<String, String> subMap = GSON.fromJson(entry.getValue().toString(), subType);
				
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
		//    ^ has txFee
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
		Map<String, String> parameters = getDefaultPostParameters();
		parameters.put(COMMAND, RETURN_BALANCES);
		
		Map<?, ?> headers = makeRequestHeaders(parameters);
		HttpResponse response = WebUtils.postRequest(Web.POLONIEX_TRADING_URL, headers, parameters);
		String json = WebUtils.getJson(response);
		
		Type type = new TypeToken<Map<String, String>>() {}.getType();
		Map<String, String> map = GSON.fromJson(json, type);

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
		Map<String, String> returnParameters = getDefaultPostParameters();
		returnParameters.put(COMMAND, RETURN_DEPOSIT_ADDRESSES);
		
		Map<?, ?> returnHeaders = makeRequestHeaders(returnParameters);
		HttpResponse returnResponse = WebUtils.postRequest(Web.POLONIEX_TRADING_URL, returnHeaders, returnParameters);
		String returnJson = WebUtils.getJson(returnResponse);
		
		Type returnType = new TypeToken<Map<String, String>>() {}.getType();
		Map<String, String> returnMap = GSON.fromJson(returnJson, returnType);

		String address = returnMap.get(currency.getSymbol());
		if (address == null) {
			Map<String, String> generateParameters = getDefaultPostParameters();
			generateParameters.put(COMMAND, GENERATE_NEW_ADDRESS);
			generateParameters.put(CURRENCY, currency.getSymbol());
			
			Map<?, ?> generateHeaders = makeRequestHeaders(generateParameters);
			HttpResponse generateResponse = WebUtils.postRequest(Web.POLONIEX_TRADING_URL, generateHeaders, generateParameters);
			String generateJson = WebUtils.getJson(generateResponse);
			
			Type generateType = new TypeToken<Map<String, String>>() {}.getType();
			Map<String, String> generateMap = GSON.fromJson(generateJson, generateType);
			
			address = generateMap.get(RESPONSE);
		}
		return address;
	}

	@Override
	public Collection<CurrencyMarket> getCurrencyMarkets() {
		Map<String, String> parameters = getDefaultGetParameters();
		parameters.put(COMMAND, RETURN_TICKER);

		HttpResponse response = WebUtils.getRequest(Web.POLONIEX_PUBLIC_URL, parameters);
		String json = WebUtils.getJson(response);

		Type type = new TypeToken<Map<String, ?>>() {}.getType();
		Map<String, ?> map = GSON.fromJson(json, type);

		Iterable<CurrencyMarket> marketIter = IterableUtils.map(map.keySet(), Poloniex::parseMarket);

		Collection<CurrencyMarket> markets = new HashSet<>();
		marketIter.forEach(markets::add);
		markets.remove(null);
		return markets;
	}

	@Override
	public Order buy(Pfloat toSpend, CurrencyMarket market, Pfloat price) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Map<String, String> getDefaultGetParameters(){
		return new HashMap<>();
	}
	
	private Map<String, String> getDefaultPostParameters(){
		Map<String, String> parameters = new HashMap<>();
		parameters.put("nonce", SecurityUtils.getNonce());
		
		return parameters;
	}

	private Map<?, ?> makeRequestHeaders(Map<?, ?> parameters) {
		String queryArgs = WebUtils.formatUrlQuery(parameters);
		String sign = SecurityUtils.hash(queryArgs, auth.getApiSecret(), SecurityUtils.HMAC_SHA512);

		Map<String, String> headers = new HashMap<>();
		headers.put("Key", auth.getApiKey());
		headers.put("Sign", sign);

		return headers;
	}

	private static CurrencyMarket parseMarket(String market) {
		String[] currencies = market.split(MARKET_DELIMITER);

		if (currencies.length != 2) {
			return null;
		}

		Currency currency = CurrencyFactory.parseSymbol(currencies[0]);
		Currency commodity = CurrencyFactory.parseSymbol(currencies[1]);
		if (currency == null || commodity == null) {
			return null;
		}
		return new CurrencyMarket(currency, commodity);
	}

	private static String toMarket(CurrencyMarket market) {
		if (market == null) {
			return null;
		}
		
		String currency = market.getCurrency().getSymbol();
		String commodity = market.getCommodity().getSymbol();
		
		return currency + MARKET_DELIMITER + commodity;
	}
}
