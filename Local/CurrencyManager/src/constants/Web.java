package constants;

public class Web {
	public static final String COIN_MARKET_CAP_URL;
	public static final String COIN_MARKET_CAP_ALL_COINS_URL;

	static {
		COIN_MARKET_CAP_URL = "https://coinmarketcap.com/";
		COIN_MARKET_CAP_ALL_COINS_URL = COIN_MARKET_CAP_URL + "all/views/all/";
	}
}
