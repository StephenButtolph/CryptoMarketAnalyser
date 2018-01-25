package platforms.authorizations;

import exceptions.StaticLoadException;
import platforms.exchanges.poloniex.PoloniexAuth;
import utils.files.FileUtils;

/**
 * Location for default authorization tokens.
 * 
 * @author Stephen Buttolph
 */
public class Auths {
	/**
	 * The default authorization token for Poloniex.
	 */
	public static final PoloniexAuth POLONIEX_AUTH;

	static {
		final String POLONIEX_AUTH_LOCAL_PATH = "/Exchanges/Poloniex/Auth.auth";
		POLONIEX_AUTH = FileUtils.loadResource(POLONIEX_AUTH_LOCAL_PATH, PoloniexAuth.class);
		if (POLONIEX_AUTH == null) {
			throw new StaticLoadException("Poloniex authorization failed to load.");
		}
	}
}
