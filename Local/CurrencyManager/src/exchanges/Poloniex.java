package exchanges;

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

import accounts.Holding;
import arithmetic.Pfloat;
import constants.Web;
import currencies.Currency;
import currencies.CurrencyFactory;
import currencies.CurrencyMarket;
import exchangeAuths.PoloniexAuth;
import offers.Offers;
import utils.IterableUtils;
import utils.SecurityUtils;
import utils.WebUtils;

public class Poloniex extends BestEffortExchange {
	private static final String COMMAND;
	private static final String RETURN_TICKER;
	private static final String RETURN_24H_VOLUME;

	private static final Gson GSON;

	static {
		COMMAND = "command";
		RETURN_TICKER = "returnTicker";
		RETURN_24H_VOLUME = "return24hVolume";

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
	public void getOpenTransactions() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getTradeHistory(CurrencyMarket market) {
		// TODO Auto-generated method stub

	}

	@Override
	public Pfloat getBalance(Currency currency) {
		// TODO Auto-generated method stub
		Map<String, String> parameters = getDefaultPostParameters();
		parameters.put("command", "returnBalances");
		

		Map<?, ?> headers = makeRequestHeaders(parameters);
		HttpResponse response = WebUtils.postRequest(Web.POLONIEX_TRADING_URL, headers, parameters);
		String json = WebUtils.getJson(response);
		System.out.println(json);
		
		Type type = new TypeToken<Map<String, String>>() {}.getType();
		Map<String, String> map = GSON.fromJson(json, type);

		String amount = map.get(currency.getSymbol());
		if (amount == null) {
			return null;
		}
		return new Pfloat(amount);
	}

	@Override
	public void sendFundsTo(Exchange exchange, Holding holding) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getWalletAddress(Currency currency) {
		// TODO Auto-generated method stub

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
	public void buy(Pfloat toSpend, CurrencyMarket market, Pfloat price) {
		// TODO Auto-generated method stub

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
		String[] currencies = market.split("_");

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
}
