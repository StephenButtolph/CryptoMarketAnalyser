package utils;

import java.util.Iterator;

public class IteratorUtils {
	public static <T> Iterable<T> flatten(Iterable<? extends Iterable<T>> iter){
		return new FlattenedWrapper<T>(iter);
	}
	
	private static class FlattenedWrapper<T> implements Iterable<T>{
		private Iterable<? extends Iterable<T>> iter;
		private FlattenedWrapper(Iterable<? extends Iterable<T>> iter) {
			this.iter = iter;
		}

		@Override
		public Iterator<T> iterator() {
			return new Flattened<T>(iter);
		}
		
		private static class Flattened<S> implements Iterator<S>{
			Iterator<? extends S> currentIter;
			private Iterator<? extends Iterable<S>> iters;
			private Flattened(Iterable<? extends Iterable<S>> iters) {
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
}
