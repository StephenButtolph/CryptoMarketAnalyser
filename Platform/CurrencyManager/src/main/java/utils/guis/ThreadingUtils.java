package utils.guis;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javafx.application.Platform;

public class ThreadingUtils {
	public static <T> void run(Supplier<T> producer, Consumer<T> consumer) {
		Thread thread = new Thread(() -> {
			T val = producer.get();

			Platform.runLater(() -> {
				consumer.accept(val);
			});
		});
		thread.setDaemon(true);
		thread.start();
	}
	
	public static void run(Runnable toRun) {
		Thread thread = new Thread(toRun);
		thread.setDaemon(true);
		thread.start();
	}

	public static void runForever(Runnable toRun, Duration waitTime) {
		Thread thread = new Thread(() -> {
			while (true) {
				toRun.run();

				try {
					Thread.sleep(waitTime.toMillis());
				} catch (InterruptedException e) {
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}
}
