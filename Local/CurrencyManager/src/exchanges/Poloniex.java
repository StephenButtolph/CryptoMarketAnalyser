package exchanges;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import accounts.Holding;
import arithmetic.Pfloat;
import constants.Web;
import currencies.Currency;
import exceptions.AssertionException;
import exceptions.ConnectionException;
import offers.Offers;
import utils.SecurityUtils;
import utils.WebUtils;

public class Poloniex extends BestEffortExchange {
	private String APIKey;
	private String APISecret;

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
	protected Offers getRawOffers(Currency currency, Currency commodity) {
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
	public void getTradeHistory(Currency currency, Currency commodity) {
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
	public void getTradableCurrencies() {
		// TODO Auto-generated method stub

	}

	@Override
	public void buy(Holding toSpend, Pfloat price, Currency commodity) {
		// TODO Auto-generated method stub

	}

	private Map<?, ?> makeRequestHeaders(Map<?, ?> parameters) {
		List<? extends NameValuePair> params = WebUtils.convertToPairs(parameters);
		String queryArgs = WebUtils.formatUrlQuery(params);
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
		Poloniex p = new Poloniex("key", "secret");

		p.postExample();
		p.getExample();
	}
}
