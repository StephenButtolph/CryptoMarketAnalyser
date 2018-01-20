package utils.collections.iterables;

import java.util.Iterator;

class FlattenedIterable<T> implements Iterable<T> {
	private Iterable<? extends Iterable<? extends T>> iter;

	FlattenedIterable(Iterable<? extends Iterable<? extends T>> iter) {
		this.iter = iter;
	}

	@Override
	public Iterator<T> iterator() {
		return new FlattenedIterator<>(iter);
	}

	private static class FlattenedIterator<S> implements Iterator<S> {
		Iterator<? extends S> currentIter;
		private Iterator<? extends Iterable<? extends S>> iters;

		private FlattenedIterator(Iterable<? extends Iterable<? extends S>> iters) {
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