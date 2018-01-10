package constants;

import com.google.gson.Gson;

/**
 * @author Stephen Buttolph
 *
 *         This is used to store default Json serializers and deserializers.
 */
public class Json {
	/**
	 * Standard Json serializer and deserializer with Google's Gson library.
	 */
	public static final Gson GSON;

	static {
		GSON = new Gson();
	}
}
