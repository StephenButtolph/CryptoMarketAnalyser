package utils.iterables;

import java.util.Iterator;

class ArrayIterable<T> implements Iterable<T> {
	private T[] arr;

	ArrayIterable(T[] arr) {
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