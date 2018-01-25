package utils.files;

import utils.collections.iterables.IterableUtils;

public class CSVFileUtils {

	public static String toLine(Object... objects) {
		Iterable<Object> objectIter = IterableUtils.toIterable(objects);
		Iterable<String> stringIter = IterableUtils.map(objectIter, Object::toString);
		Iterable<String> CSVStringIter = IterableUtils.map(stringIter, str -> Constants.QUOTE
				+ str.replaceAll(Constants.QUOTE, Constants.DOUBLE_QUOTE) + Constants.QUOTE);

		return IterableUtils.join(CSVStringIter, Constants.CSV_SEPARATOR);
	}
}
