package applications.components.stages;

import applications.components.stages.debug.Debugger;
import applications.components.stages.loggers.marketLogger.MarketLoggerStage;

public class StageFactory {
	private static MarketLoggerStage marketLoggerStage;
	private static Debugger debugger;

	public static MarketLoggerStage getMarketLogger() {
		if (marketLoggerStage == null) {
			marketLoggerStage = new MarketLoggerStage();
		}
		return marketLoggerStage;
	}

	public static Debugger getDebugger() {
		if (debugger == null) {
			debugger = new Debugger();
		}
		return debugger;
	}
}
