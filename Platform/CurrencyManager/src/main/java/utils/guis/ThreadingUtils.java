package utils.guis;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javafx.application.Platform;
import logging.debug.DebugLogger;

public class ThreadingUtils {
	private static final String NAME_FORMAT;
	private static int nonce;

	static {
		NAME_FORMAT = "F=%s V=%s";
		nonce = 0;
	}

	public synchronized static <T> void run(Supplier<T> producer, Consumer<T> consumer) {
		Thread thread = new Thread(() -> {
			T val = producer.get();

			Platform.runLater(() -> {
				consumer.accept(val);
			});
		});
		String name = String.format(NAME_FORMAT, "Run(PC)", nonce++);
		startThread(thread, name);
	}

	public synchronized static void run(Runnable toRun) {
		Thread thread = new Thread(toRun);
		String name = String.format(NAME_FORMAT, "Run(R)", nonce++);
		startThread(thread, name);
	}

	public synchronized static void runForever(Runnable toRun, Duration waitTime) {
		Thread thread = new Thread(() -> {
			while (true) {
				toRun.run();

				try {
					Thread.sleep(waitTime.toMillis());
				} catch (InterruptedException e) {
					DebugLogger.addError(e);
				}
			}
		});
		String name = String.format(NAME_FORMAT, "RunForever(R)", nonce++);
		startThread(thread, name);
	}

	public synchronized static <T> void runForever(Supplier<T> producer, Consumer<T> consumer, Duration waitTime) {
		Thread thread = new Thread(() -> {
			while (true) {
				T val = producer.get();

				Platform.runLater(() -> {
					consumer.accept(val);
				});

				try {
					Thread.sleep(waitTime.toMillis());
				} catch (InterruptedException e) {
					DebugLogger.addError(e);
				}
			}
		});
		String name = String.format(NAME_FORMAT, "RunForever(PC)", nonce++);
		startThread(thread, name);
	}

	private static void startThread(Thread thread, String name) {
		thread.setName(name);
		startThread(thread);
	}

	private static void startThread(Thread thread) {
		thread.setDaemon(true);
		thread.start();
	}
}
