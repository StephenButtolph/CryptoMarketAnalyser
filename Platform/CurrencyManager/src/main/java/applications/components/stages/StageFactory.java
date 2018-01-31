package applications.components.stages;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import applications.components.stages.debug.Debugger;
import applications.components.stages.loggers.marketLogger.MarketLoggerStage;

public class StageFactory {
	private static MarketLoggerStage marketLoggerStage;
	private static Debugger debugger;

	private static Lock marketLoggerStageLock;
	private static Lock debuggerLock;

	static {
		marketLoggerStageLock = new ReentrantLock();
		debuggerLock = new ReentrantLock();
	}

	public static MarketLoggerStage getMarketLogger() {
		marketLoggerStageLock.lock();
		if (marketLoggerStage == null) {
			marketLoggerStage = new MarketLoggerStage();
		}
		marketLoggerStageLock.unlock();
		return marketLoggerStage;
	}

	public static Debugger getDebugger() {
		debuggerLock.lock();
		if (debugger == null) {
			debugger = new Debugger();
		}
		debuggerLock.unlock();
		return debugger;
	}
}
