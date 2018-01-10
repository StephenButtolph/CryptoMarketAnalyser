package constants;

import com.google.gson.Gson;

public class Json {
	public static final Gson GSON;

	static {
		GSON = new Gson();
	}
}
