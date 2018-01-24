package utils.files;

import utils.collections.iterables.IterableUtils;

public class CSVFileUtils {
	private static final String CSV_SEPARATOR = ",";

	public static String toLine(Object... objects) {
		Iterable<Object> objectIter = IterableUtils.toIterable(objects);
		Iterable<String> stringIter = IterableUtils.map(objectIter, Object::toString);
		Iterable<String> CSVStringIter = IterableUtils.map(stringIter,
				str -> "\"" + str.replaceAll("\"", "\"\"") + "\"");

		return IterableUtils.join(CSVStringIter, CSV_SEPARATOR);
	}
}
