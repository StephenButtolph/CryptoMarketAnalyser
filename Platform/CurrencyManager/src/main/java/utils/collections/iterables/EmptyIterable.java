package utils.collections.iterables;

import java.util.Iterator;
import java.util.NoSuchElementException;

import logging.debug.DebugLogger;

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
			RuntimeException e = new NoSuchElementException("An empty iterator can't have a next element.");
			DebugLogger.addError(e);
			throw e;
		}
	}
}