package utils;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;

public class IterableUtils {
	public static <T> Iterable<T> flatten(Iterable<? extends Iterable<T>> iter) {
		return new FlattenedIterable<>(iter);
	}

	public static <T> Iterable<T> toIterable(T[] arr) {
		return new ArrayIterable<>(arr);
	}

	public static <A, B> Iterable<B> map(Iterable<A> init, Function<A, B> f) {
		return new MappedIterable<>(init, f);
	}

	public static <A, B> B fold(Iterable<A> iter, BiFunction<B, A, B> f, B acc) {
		for (A val : iter) {
			acc = f.apply(acc, val);
		}
		return acc;
	}

	
	
	
	
	private static class FlattenedIterable<T> implements Iterable<T> {
		private Iterable<? extends Iterable<T>> iter;

		private FlattenedIterable(Iterable<? extends Iterable<T>> iter) {
			this.iter = iter;
		}

		@Override
		public Iterator<T> iterator() {
			return new FlattenedIterator<T>(iter);
		}

		private static class FlattenedIterator<S> implements Iterator<S> {
			Iterator<? extends S> currentIter;
			private Iterator<? extends Iterable<S>> iters;

			private FlattenedIterator(Iterable<? extends Iterable<S>> iters) {
				this.iters = iters.iterator();
			}

			@Override
			public boolean hasNext() {
				while ((currentIter == null || !currentIter.hasNext()) && iters.hasNext()) {
					Iterable<? extends S> next = iters.next();
					if (next != null) {
						currentIter = next.iterator();
					}
				}
				return currentIter != null && currentIter.hasNext();
			}

			@Override
			public S next() {
				return currentIter.next();
			}
		}
	}

	private static class ArrayIterable<T> implements Iterable<T> {
		private T[] arr;

		private ArrayIterable(T[] arr) {
			this.arr = arr;
		}

		@Override
		public Iterator<T> iterator() {
			return new ArrayIterator<T>(arr);
		}

		private static class ArrayIterator<S> implements Iterator<S> {
			private S[] arr;
			private int index;

			private ArrayIterator(S[] arr) {
				this.arr = arr;
				this.index = 0;
			}

			@Override
			public boolean hasNext() {
				return index < arr.length;
			}

			@Override
			public S next() {
				return arr[index++];
			}
		}
	}

	private static class MappedIterable<A, B> implements Iterable<B> {
		private Iterable<A> init;
		private Function<A, B> f;

		private MappedIterable(Iterable<A> init, Function<A, B> f) {
			this.init = init;
			this.f = f;
		}

		@Override
		public Iterator<B> iterator() {
			return new MappedIterator<>(init, f);
		}

		private static class MappedIterator<X, Y> implements Iterator<Y> {
			private Iterator<X> init;
			private Function<X, Y> f;

			private MappedIterator(Iterable<X> init, Function<X, Y> f) {
				this.init = init.iterator();
				this.f = f;
			}

			@Override
			public boolean hasNext() {
				return init.hasNext();
			}

			@Override
			public Y next() {
				return f.apply(init.next());
			}
		}
	}
}
