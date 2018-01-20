package utils.collections.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

public class CollectionUtils {
	public static <O, N> Collection<N> convert(Collection<O> oldCollection, Function<O, N> mapper) {
		Collection<N> newCollection = new ArrayList<>();
		for (O entry : oldCollection) {
			newCollection.add(mapper.apply(entry));
		}
		return newCollection;
	}
}
