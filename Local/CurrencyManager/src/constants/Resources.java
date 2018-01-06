package constants;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import exceptions.StaticLoadException;

public class Resources {
	private static final String POLONIEX_AUTH_LOCAL;
	public static final String POLONIEX_AUTH;

	static {
		POLONIEX_AUTH_LOCAL = "/Poloniex/Auth.auth";

		URL resource = Resources.class.getResource(POLONIEX_AUTH_LOCAL);
		try {
			POLONIEX_AUTH = Paths.get(resource.toURI()).toFile().getAbsolutePath();
		} catch (NullPointerException | URISyntaxException e) {
			throw new StaticLoadException("Poloniex authorization file was not found.");
		}
	}
}
