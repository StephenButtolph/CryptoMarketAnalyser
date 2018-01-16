package constants;

import exceptions.StaticLoadException;
import exchanges.poloniex.PoloniexAuth;
import utils.file.FileUtils;

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
		POLONIEX_AUTH = FileUtils.load(Resources.POLONIEX_AUTH_PATH, PoloniexAuth.class);
		if (POLONIEX_AUTH == null) {
			throw new StaticLoadException("Poloniex authorization failed to load.");
		}
	}
}
