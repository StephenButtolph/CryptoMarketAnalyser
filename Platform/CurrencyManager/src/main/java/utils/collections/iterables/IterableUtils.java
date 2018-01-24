package utils.collections.iterables;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;

public class IterableUtils {
	public static <T> Iterable<T> flatten(Iterable<? extends Iterable<T>> iter) {
		iter = toSafeIterable(iter);
		return new FlattenedIterable<>(iter);
	}

	public static <T> Iterable<T> toIterable(T[] arr) {
		if (arr == null) {
			return new EmptyIterable<>();
		}
		return new ArrayIterable<>(arr);
	}

	public static <A, B> Iterable<B> map(Iterable<A> iter, Function<? super A, ? extends B> f) {
		iter = toSafeIterable(iter);
		return new MappedIterable<>(iter, f);
	}

	public static <A, B> B fold(Iterable<? extends A> iter, BiFunction<? super B, ? super A, ? extends B> f, B acc) {
		iter = toSafeIterable(iter);
		for (A val : iter) {
			acc = f.apply(acc, val);
		}
		return acc;
	}

	public static <T> Iterable<T> filter(Iterable<T> iter, Function<? super T, Boolean> f) {
		iter = toSafeIterable(iter);
		return new FilteredIterable<>(iter, f);
	}

	public static <T> Iterable<T> toSafeIterable(Iterable<T> iter) {
		if (iter != null) {
			return iter;
		}
		return new EmptyIterable<>();
	}

	public static String join(Iterable<String> iter, String delimiter) {
		iter = toSafeIterable(iter);
		Iterator<String> iterator = iter.iterator();

		StringBuilder sb = new StringBuilder();
		while (iterator.hasNext()) {
			sb.append(iterator.next());
			if (iterator.hasNext()) {
				sb.append(delimiter);
			}
		}
		return sb.toString();
	}
}
