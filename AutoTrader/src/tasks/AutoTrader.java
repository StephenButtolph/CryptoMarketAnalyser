package tasks;

import java.util.concurrent.TimeUnit;

import constants.Constants;

public class AutoTrader extends RepeatTask {
	@Override
	public void run() {
		// TODO Auto-generated method stub
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
