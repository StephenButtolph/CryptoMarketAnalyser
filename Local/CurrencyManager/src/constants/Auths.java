package constants;

import exceptions.StaticLoadException;
import exchangeAuths.PoloniexAuth;
import utils.FileUtils;

public class Auths {
	public static final PoloniexAuth POLONIEX_AUTH;

	static {
		POLONIEX_AUTH = FileUtils.load(Resources.POLONIEX_AUTH, PoloniexAuth.class);
		if (POLONIEX_AUTH == null) {
			throw new StaticLoadException("Poloniex authorization failed to load.");
		}
	}
}
