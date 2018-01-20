package utils.collections.maps;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

public class MapUtils {
	public static <K, V, OK, OV> Map<K, V> convertEntries(Map<OK, OV> map, Function<OK, K> keyParser,
			Function<OV, V> valueParser) {
		Map<K, V> newMap = new HashMap<>();

		for (Entry<OK, OV> entry : map.entrySet()) {
			newMap.put(keyParser.apply(entry.getKey()), valueParser.apply(entry.getValue()));
		}

		return newMap;
	}

	public static <K, V, OK> Map<K, V> convertKeys(Map<OK, V> map, Function<OK, K> keyParser) {
		Map<K, V> newMap = new HashMap<>();

		for (Entry<OK, V> entry : map.entrySet()) {
			newMap.put(keyParser.apply(entry.getKey()), entry.getValue());
		}

		return newMap;
	}

	public static <K, V, OV> Map<K, V> convertValues(Map<K, OV> map, Function<OV, V> valueParser) {
		Map<K, V> newMap = new HashMap<>();

		for (Entry<K, OV> entry : map.entrySet()) {
			newMap.put(entry.getKey(), valueParser.apply(entry.getValue()));
		}

		return newMap;
	}
}
