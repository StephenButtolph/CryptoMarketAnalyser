package constants;

import exceptions.StaticLoadException;
import exchanges.poloniex.PoloniexAuth;
import utils.files.FileUtils;
import utils.resources.ResourceUtils;

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
		String poloniexAuthPath = ResourceUtils.getResourcePath(POLONIEX_AUTH_LOCAL_PATH);
		POLONIEX_AUTH = FileUtils.load(poloniexAuthPath, PoloniexAuth.class);
		if (POLONIEX_AUTH == null) {
			throw new StaticLoadException("Poloniex authorization failed to load.");
		}
	}
}
