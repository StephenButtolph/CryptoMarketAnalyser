package constants;

import exchangeAuths.PoloniexAuth;
import utils.FileUtils;

public class Auths {
	public static final PoloniexAuth POLONIEX_AUTH;

	static {
		POLONIEX_AUTH = FileUtils.load(Resources.POLONIEX_AUTH, PoloniexAuth.class);
	}
}
