package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class WebUtils {
	public static List<? extends NameValuePair> convertToPairs(Map<?, ?> parameters) {
		BiFunction<List<NameValuePair>, Entry<?, ?>, List<NameValuePair>> f = (list, entry) -> {
			NameValuePair p = new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString());
			list.add(p);
			return list;
		};

		return IterableUtils.fold(parameters.entrySet(), f, new ArrayList<>());
	}

	public static String formatUrlQuery(Map<?, ?> parameters) {
		return formatUrlQuery(convertToPairs(parameters));
	}

	public static String formatUrlQuery(List<? extends NameValuePair> parameters) {
		return URLEncodedUtils.format(parameters, "UTF-8");
	}
}
