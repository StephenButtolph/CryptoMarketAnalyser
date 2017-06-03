package tasks;

import java.util.concurrent.TimeUnit;

import constants.Constants;
import traders.AssistedTrader;

public class AutoTrader extends RepeatTask {
	private AssistedTrader trader;
	
	public AutoTrader(AssistedTrader trader){
		this.trader = trader;
	}
	
	@Override
	public void run() {
		trader.trade();
	}

	@Override
	public long getInitialDelay() {
		return Constants.AUTO_TRADER.INITIAL_DELAY;
	}

	@Override
	public TimeUnit getTimeUnit() {
		return Constants.AUTO_TRADER.TIME_UNIT;
	}

	@Override
	public long getRepeatDelay() {
		return Constants.AUTO_TRADER.REPEAT_DELAY;
	}
}
