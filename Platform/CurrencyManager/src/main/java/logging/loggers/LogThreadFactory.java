package logging.loggers;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

class LogThreadFactory implements ThreadFactory {
	private static final AtomicInteger counter = new AtomicInteger();

	@Override
	public Thread newThread(Runnable r) {
		String name = "Logging Thread-" + counter.getAndIncrement();
		return new Thread(r, name);
	}
}
