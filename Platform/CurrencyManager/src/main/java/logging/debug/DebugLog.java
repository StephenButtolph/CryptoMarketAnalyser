package logging.debug;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class DebugLog {
	private String message;
	private String threadName;

	private DebugLevel debugLevel;

	public DebugLog(Throwable e) {
		this(ExceptionUtils.getStackTrace(e), DebugLevel.ERROR);
	}

	public DebugLog(String message, DebugLevel debugLevel) {
		this.message = message;
		this.threadName = Thread.currentThread().getName();

		this.debugLevel = debugLevel;
	}

	public String getMessage() {
		return message;
	}

	public String getThreadName() {
		return threadName;
	}

	public DebugLevel getDebugLevel() {
		return debugLevel;
	}
}
