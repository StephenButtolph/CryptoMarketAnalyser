package constants;

import com.google.gson.Gson;

/**
 * This is used to store default Json serializers and deserializers.
 * 
 * @author Stephen Buttolph
 */
public class Json {
	/**
	 * Standard Json serializer and deserializer with Google's Gson library.
	 */
	public static final Gson GSON;

	public static final String EMPTY_DICTIONARY;

	public static final String EMPTY_ARRAY;

	static {
		GSON = new Gson();
		EMPTY_DICTIONARY = "{}";
		EMPTY_ARRAY = "[]";
	}
}
