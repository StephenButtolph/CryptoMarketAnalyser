package platforms.exchanges.poloniex;

import java.util.HashMap;
import java.util.Map;

import platforms.currencies.Currency;
import platforms.currencies.CurrencyFactory;
import platforms.currencies.markets.CurrencyMarket;
import platforms.currencies.markets.StandardCurrencyMarket;
import utils.security.SecurityUtils;
import utils.web.WebUtils;

class Utils {
	static Map<String, String> getDefaultGetParameters() {
		return new HashMap<>();
	}

	static Map<String, String> getDefaultPostParameters() {
		Map<String, String> parameters = new HashMap<>();
		parameters.put("nonce", SecurityUtils.getNonce());

		return parameters;
	}

	static Map<?, ?> makeRequestHeaders(PoloniexAuth auth, Map<?, ?> parameters) {
		String queryArgs = WebUtils.formatUrlQuery(parameters);
		String sign = SecurityUtils.hash(queryArgs, auth.getApiSecret(), SecurityUtils.HMAC_SHA512);

		Map<String, String> headers = new HashMap<>();
		headers.put("Key", auth.getApiKey());
		headers.put("Sign", sign);

		return headers;
	}

	static CurrencyMarket parseMarket(String market) {
		String[] currencies = market.split(Constants.MARKET_DELIMITER);

		if (currencies.length != 2) {
			return null;
		}

		Currency currency = CurrencyFactory.parseSymbol(currencies[0]);
		Currency commodity = CurrencyFactory.parseSymbol(currencies[1]);
		if (currency == null || commodity == null) {
			return null;
		}
		return new StandardCurrencyMarket(currency, commodity);
	}

	static String toMarket(CurrencyMarket market) {
		if (market == null) {
			return null;
		}

		String currency = market.getCurrency().getSymbol();
		String commodity = market.getCommodity().getSymbol();

		return currency + Constants.MARKET_DELIMITER + commodity;
	}
}
