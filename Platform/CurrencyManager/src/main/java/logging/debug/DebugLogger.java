package logging.debug;

import java.util.concurrent.LinkedBlockingQueue;

public class DebugLogger {
	private static LinkedBlockingQueue<DebugLog> unhandledLogs;
	private static LinkedBlockingQueue<DebugLog> logs;

	static {
		unhandledLogs = new LinkedBlockingQueue<>();
		logs = new LinkedBlockingQueue<>();
	}

	public static DebugLog getUnhandledLog() {
		DebugLog error = null;
		boolean needsTake;
		do {
			try {
				error = unhandledLogs.take();
				needsTake = false;
			} catch (InterruptedException e) {
				needsTake = true;
			}
		} while (needsTake);
		return error;
	}

	public static void addError(Throwable e) {
		addLog(new DebugLog(e));
	}

	public static void addLog(String message, DebugLevel debugLevel) {
		addLog(new DebugLog(message + "\n", debugLevel));
	}

	public static void addLog(DebugLog log) {
		addLog(log, logs);
		addLog(log, unhandledLogs);
	}

	private static void addLog(DebugLog log, LinkedBlockingQueue<DebugLog> logs) {
		boolean needsInsert;
		do {
			try {
				logs.put(log);
				needsInsert = false;
			} catch (InterruptedException e) {
				needsInsert = true;
			}
		} while (needsInsert);
	}

	public static Iterable<DebugLog> allErrors() {
		return logs;
	}
}
