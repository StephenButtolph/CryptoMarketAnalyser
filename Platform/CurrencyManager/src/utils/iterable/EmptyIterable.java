package utils.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;

class EmptyIterable<T> implements Iterable<T> {
	private final Iterator<T> iter;

	EmptyIterable() {
		iter = new EmptyIterator<>();
	}

	@Override
	public Iterator<T> iterator() {
		return iter;
	}

	private static class EmptyIterator<S> implements Iterator<S> {
		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public S next() {
			throw new NoSuchElementException("An empty iterator can't have a next element.");
		}
	}
}