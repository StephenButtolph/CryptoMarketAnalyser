package exchanges.poloniex;

import java.lang.reflect.Type;

import types.TypeProducer;
import types.TypeToken;

/**
 * This encapsulates the authorizations needed to access the Poloniex API.
 * Specifically, an API Key and API Secret.
 * 
 * @author stephen
 *
 */
public class PoloniexAuth implements TypeProducer {
	private String apiKey;
	private String apiSecret;

	/**
	 * Create a new authorization with the given key [apiKey] and secret
	 * [apiSecret].
	 * 
	 * @param apiKey
	 *            The Poloniex API Key to use.
	 * @param apiSecret
	 *            The Poloniex API Secret to use.
	 */
	public PoloniexAuth(String apiKey, String apiSecret) {
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
	}

	/**
	 * @return This authorizations API Key.
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * @return This authorizations API Secret.
	 */
	public String getApiSecret() {
		return apiSecret;
	}

	@Override
	public String toString() {
		String format = "Api Key = %s\nApi Secret = %s";
		return String.format(format, apiKey, apiSecret);
	}

	@Override
	public Type getType() {
		return new TypeToken<PoloniexAuth>().getType();
	}
}
