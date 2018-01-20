package utils.guis;

import java.util.function.Consumer;
import java.util.function.Supplier;

import javafx.application.Platform;

public class ThreadingUtils {
	public static <T> void run(Supplier<T> producer, Consumer<T> consumer) {
		new Thread(() -> {
			T val = producer.get();

			Platform.runLater(() -> {
				consumer.accept(val);
			});
		}).start();
	}
}
