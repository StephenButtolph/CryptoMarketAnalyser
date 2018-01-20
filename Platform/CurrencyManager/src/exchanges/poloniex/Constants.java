package exchanges.poloniex;

class Constants {
	static final String PUBLIC_URL;
	static final String TRADING_URL;

	static final String COMMAND;
	static final String CURRENCY;
	static final String CURRENCY_PAIR;
	static final String ADDRESS;
	static final String START;
	static final String LIMIT;
	static final String TYPE;
	static final String RATE;
	static final String TOTAL;
	static final String AMOUNT;
	static final String BUY;
	static final String SELL;
	static final String ORDER_NUMBER;

	static final String RETURN_TICKER;
	static final String RETURN_24H_VOLUME;
	static final String RETURN_BALANCES;
	static final String RETURN_DEPOSIT_ADDRESSES;
	static final String GENERATE_NEW_ADDRESS;
	static final String RETURN_TRADE_HISTORY;
	static final String WITHDRAW;
	static final String RETURN_ORDER_TRADES;
	static final String CANCEL_ORDER;
	static final String RETURN_OPEN_ORDERS;

	static final String MAX_RETURN_TRADE_HISTORY_LIMIT;
	static final String MIN_START_TIME;

	static final String RESPONSE;
	static final String SUCCESS;

	static final Integer SUCCEEDED;

	static final String MARKET_DELIMITER;

	static {
		COMMAND = "command";
		CURRENCY = "currency";
		CURRENCY_PAIR = "currencyPair";
		ADDRESS = "address";
		START = "start";
		LIMIT = "limit";
		TYPE = "type";
		RATE = "rate";
		TOTAL = "total";
		AMOUNT = "amount";
		BUY = "buy";
		SELL = "sell";
		ORDER_NUMBER = "orderNumber";

		RETURN_TICKER = "returnTicker";
		RETURN_24H_VOLUME = "return24hVolume";
		RETURN_BALANCES = "returnBalances";
		RETURN_DEPOSIT_ADDRESSES = "returnDepositAddresses";
		GENERATE_NEW_ADDRESS = "generateNewAddress";
		RETURN_TRADE_HISTORY = "returnTradeHistory";
		WITHDRAW = "withdraw";
		RETURN_ORDER_TRADES = "returnOrderTrades";
		CANCEL_ORDER = "cancelOrder";
		RETURN_OPEN_ORDERS = "returnOpenOrders";

		MAX_RETURN_TRADE_HISTORY_LIMIT = "10000";
		MIN_START_TIME = "0";

		RESPONSE = "response";
		SUCCESS = "success";

		SUCCEEDED = 1;

		MARKET_DELIMITER = "_";
	}

	static {
		PUBLIC_URL = "https://poloniex.com/public";
		TRADING_URL = "https://poloniex.com/tradingApi";
	}
}
