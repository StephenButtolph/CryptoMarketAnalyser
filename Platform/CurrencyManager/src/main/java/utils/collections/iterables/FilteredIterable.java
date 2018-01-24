package utils.collections.iterables;

import java.util.Iterator;
import java.util.function.Function;

class FilteredIterable<T> implements Iterable<T> {
	private Iterable<T> init;
	private Function<? super T, Boolean> f;

	FilteredIterable(Iterable<T> init, Function<? super T, Boolean> f) {
		this.init = init;
		this.f = f;
	}

	@Override
	public Iterator<T> iterator() {
		return new FilteredIterator<>(init, f);
	}

	private static class FilteredIterator<V> implements Iterator<V> {
		private Iterator<V> iter;
		private Function<? super V, Boolean> f;

		private V next;
		private boolean hasNext;

		private FilteredIterator(Iterable<V> init, Function<? super V, Boolean> f) {
			this.iter = init.iterator();
			this.f = f;

			hasNext = false;
		}

		@Override
		public boolean hasNext() {
			if (hasNext) {
				return true;
			}

			while (iter.hasNext() && !hasNext) {
				next = iter.next();
				hasNext = f.apply(next);
			}
			return hasNext;
		}

		@Override
		public V next() {
			hasNext = false;
			return next;
		}
	}
}