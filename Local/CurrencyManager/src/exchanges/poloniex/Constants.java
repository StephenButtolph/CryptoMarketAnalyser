package exchanges.poloniex;

class Constants {
	static final String PUBLIC_URL;
	static final String TRADING_URL;
	
	static final String COMMAND;
	static final String CURRENCY;
	
	static final String RETURN_TICKER;
	static final String RETURN_24H_VOLUME;
	static final String RETURN_BALANCES;
	static final String RETURN_DEPOSIT_ADDRESSES;
	static final String GENERATE_NEW_ADDRESS;

	static final String RESPONSE;
	
	static final String MARKET_DELIMITER;

	static {
		COMMAND = "command";
		CURRENCY = "currency";
		
		RETURN_TICKER = "returnTicker";
		RETURN_24H_VOLUME = "return24hVolume";
		RETURN_BALANCES = "returnBalances";
		RETURN_DEPOSIT_ADDRESSES = "returnDepositAddresses";
		GENERATE_NEW_ADDRESS = "generateNewAddress";

		RESPONSE = "response";
		
		MARKET_DELIMITER = "_";
	}

	static {
		PUBLIC_URL = "https://poloniex.com/public";
		TRADING_URL = "https://poloniex.com/tradingApi";
	}
}
