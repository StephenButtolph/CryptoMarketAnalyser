package platforms.tickers.coinMarketCap;

class Constants {
	static final String BASE_URL;
	static final String ALL_COINS_URL;

	static final String CLEAR_REGEX;
	static final String EMPTY;
	static final String LOW_VOLUME_REGEX;
	static final String ZERO;
	static final int NUM_ARGS;

	static final int RANK;
	static final int SYMBOL;
	static final int MARKET_CAP;
	static final int USD_PRICE;
	static final int CIRCULATING_SUPPLY;
	static final int DAY_VOLUME;
	static final int HOUR_PERCENT_CHANGE;
	static final int DAY_PERCENT_CHANGE;
	static final int WEEK_PERCENT_CHANGE;

	static {
		BASE_URL = "https://coinmarketcap.com/";
		ALL_COINS_URL = BASE_URL + "all/views/all/";

		CLEAR_REGEX = "[$,%* ]";
		EMPTY = "";
		LOW_VOLUME_REGEX = "LowVol";
		ZERO = "0";
		NUM_ARGS = 9;

		RANK = 0;
		SYMBOL = 1;
		MARKET_CAP = 2;
		USD_PRICE = 3;
		CIRCULATING_SUPPLY = 4;
		DAY_VOLUME = 5;
		HOUR_PERCENT_CHANGE = 6;
		DAY_PERCENT_CHANGE = 7;
		WEEK_PERCENT_CHANGE = 8;
	}
}
