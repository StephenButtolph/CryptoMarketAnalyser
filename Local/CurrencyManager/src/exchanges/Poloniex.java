package exchanges;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import accounts.Holding;
import arithmetic.Pfloat;
import constants.Web;
import currencies.Currency;
import currencies.CurrencyFactory;
import currencies.CurrencyMarket;
import exceptions.ConnectionException;
import offers.Offers;
import utils.SecurityUtils;
import utils.WebUtils;

public class Poloniex extends BestEffortExchange {
	private String APIKey;
	private String APISecret;

	private static final Gson gson;

	static {
		gson = new Gson();
	}

	public Poloniex(String APIKey, String APISecret) {
		this.APIKey = APIKey;
		this.APISecret = APISecret;
	}

	@Override
	public Pfloat get24HVolume(Currency currency) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Offers getRawOffers(CurrencyMarket market) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Offers adjustOffers(Offers rawOffers) {
		// TODO Auto-generated method stub
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
	public void getBalance(Currency currency) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void buy(Pfloat toSpend, CurrencyMarket market, Pfloat price) {
		// TODO Auto-generated method stub

	}

	private Map<?, ?> makeRequestHeaders(Map<?, ?> parameters) {
		String queryArgs = WebUtils.formatUrlQuery(parameters);
		String sign = SecurityUtils.hash(queryArgs, APISecret, SecurityUtils.HMAC_SHA512);

		Map<String, String> headers = new HashMap<>();
		headers.put("Key", APIKey);
		headers.put("Sign", sign);

		return headers;
	}

	private void postExample() {
		Map<String, String> parameters = new HashMap<>();
		parameters.put("command", "returnFeeInfo");

		Map<?, ?> headers = makeRequestHeaders(parameters);
		HttpResponse response = WebUtils.postRequest(Web.POLONIEX_TRADING_URL, headers, parameters);
		printResponse(response);
	}

	private void getExample() {
		Map<String, String> parameters = new HashMap<>();
		parameters.put("command", "returnTicker");

		HttpResponse response = WebUtils.getRequest(Web.POLONIEX_PUBLIC_URL, parameters);
		printResponse(response);
	}

	private static void printResponse(HttpResponse response) {
		HttpEntity responseEntity = response.getEntity();
		System.out.println(response.getStatusLine());
		try {
			System.out.println(EntityUtils.toString(responseEntity));
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// Poloniex p = new Poloniex("key", "secret");
		//
		// p.postExample();
		// p.getExample();

		Map<String, String> parameters = new HashMap<>();
		parameters.put("command", "returnTicker");

		HttpResponse response = WebUtils.getRequest(Web.POLONIEX_PUBLIC_URL, parameters);
		String json = WebUtils.getJson(response);

		Type type = new TypeToken<Map<String, ?>>() {
		}.getType();
		Map<String, ?> map = gson.fromJson(json, type);

		Collection<CurrencyMarket> markets = new HashSet<>();
		for (String exchange : map.keySet()) {
			String[] curs = exchange.split("_");

			Currency currency = CurrencyFactory.parseSymbol(curs[0]);
			if (currency == null) {
				continue;
			}
			Currency commodity = CurrencyFactory.parseSymbol(curs[1]);
			if (commodity == null) {
				continue;
			}

			CurrencyMarket market = new CurrencyMarket(currency, commodity);
			markets.add(market);
			markets.add(market.invert());

		}
		System.out.println(markets);
		System.out.println(markets.size());
	}
}
