package utils.types;

import java.lang.reflect.Type;

public class TypeToken<T> implements TypeProducer {
	private com.google.gson.reflect.TypeToken<T> token;

	public TypeToken() {
		token = new com.google.gson.reflect.TypeToken<T>() {
		};
	}

	@Override
	public Type getType() {
		return token.getType();
	}
}
