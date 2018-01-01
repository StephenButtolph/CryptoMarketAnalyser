package constants;

public class Web {
	public static final String COIN_MARKET_CAP_URL;
	public static final String COIN_MARKET_CAP_ALL_COINS_URL;

	public static final String POLONIEX_PUBLIC_URL;
	public static final String POLONIEX_TRADING_URL;

	static {
		COIN_MARKET_CAP_URL = "https://coinmarketcap.com/";
		COIN_MARKET_CAP_ALL_COINS_URL = COIN_MARKET_CAP_URL + "all/views/all/";

		POLONIEX_PUBLIC_URL = "https://poloniex.com/public";
		POLONIEX_TRADING_URL = "https://poloniex.com/tradingApi";
	}
}
