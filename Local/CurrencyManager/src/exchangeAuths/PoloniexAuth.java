package exchangeAuths;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;

import types.TypeProducer;

public class PoloniexAuth implements TypeProducer {
	private String apiKey;
	private String apiSecret;

	public PoloniexAuth(String apiKey, String apiSecret) {
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
	}

	public String getApiKey() {
		return apiKey;
	}

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
		return new TypeToken<PoloniexAuth>() {}.getType();
	}
}
