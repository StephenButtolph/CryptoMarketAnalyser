package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;

import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import constants.Web;
import exceptions.AssertionException;
import exceptions.ConnectionException;

public class WebUtils {
	public static HttpResponse postRequest(String baseUrl, Map<?, ?> parameters) {
		return postRequest(baseUrl, new HashMap<>(), parameters);
	}

	public static HttpResponse postRequest(String baseUrl, Map<?, ?> headers, Map<?, ?> parameters) {
		HttpPost post = new HttpPost(baseUrl);
		addHeaders(post, headers);

		Iterable<? extends NameValuePair> params = WebUtils.convertToPairs(parameters);
		HttpEntity entity = new UrlEncodedFormEntity(params);
		post.setEntity(entity);

		return execute(post);
	}

	public static HttpResponse getRequest(String url) {
		HttpGet get = new HttpGet(url);
		return execute(get);
	}

	public static HttpResponse getRequest(String baseUrl, Map<?, ?> parameters) {
		return getRequest(baseUrl, new HashMap<>(), parameters);
	}

	public static HttpResponse getRequest(String baseUrl, Map<?, ?> headers, Map<?, ?> parameters) {
		String queryArgs = WebUtils.formatUrlQuery(parameters);
		String url = Web.POLONIEX_PUBLIC_URL + "?" + queryArgs;

		HttpGet get = new HttpGet(url);
		addHeaders(get, headers);

		return execute(get);
	}

	public static List<? extends NameValuePair> convertToPairs(Map<?, ?> parameters) {
		BiFunction<List<NameValuePair>, Entry<?, ?>, List<NameValuePair>> f = (list, entry) -> {
			NameValuePair p = new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString());
			list.add(p);
			return list;
		};

		List<NameValuePair> list = new ArrayList<>();
		if (parameters != null) {
			list = IterableUtils.fold(parameters.entrySet(), f, list);
		}
		return list;
	}

	public static String formatUrlQuery(Map<?, ?> parameters) {
		return formatUrlQuery(convertToPairs(parameters));
	}

	public static String formatUrlQuery(List<? extends NameValuePair> parameters) {
		return URLEncodedUtils.format(parameters, "UTF-8");
	}

	private static void addHeaders(HttpMessage message, Map<?, ?> headers) {
		Iterable<? extends NameValuePair> heads = WebUtils.convertToPairs(headers);
		for (NameValuePair pair : heads) {
			message.addHeader(pair.getName(), pair.getValue());
		}
	}

	private static HttpResponse execute(HttpUriRequest request) {
		HttpClient httpClient = HttpClients.createDefault();
		HttpResponse response;
		try {
			response = httpClient.execute(request);
		} catch (ClientProtocolException e) {
			throw new AssertionException();
		} catch (IOException e) {
			throw new ConnectionException();
		}
		return response;
	}
}
