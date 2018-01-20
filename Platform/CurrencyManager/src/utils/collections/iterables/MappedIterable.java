package utils.collections.iterables;

import java.util.Iterator;
import java.util.function.Function;

class MappedIterable<A, B> implements Iterable<B> {
	private Iterable<A> init;
	private Function<? super A, ? extends B> f;

	MappedIterable(Iterable<A> init, Function<? super A, ? extends B> f) {
		this.init = init;
		this.f = f;
	}

	@Override
	public Iterator<B> iterator() {
		return new MappedIterator<>(init, f);
	}

	private static class MappedIterator<X, Y> implements Iterator<Y> {
		private Iterator<X> init;
		private Function<? super X, ? extends Y> f;

		private MappedIterator(Iterable<X> init, Function<? super X, ? extends Y> f) {
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