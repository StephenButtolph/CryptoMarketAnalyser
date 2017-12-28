package utils;

/*
 * Used to be able to simulate passed by reference function calls.
 */
public class Ref<T> {
	private T value;

	public Ref() {
		this(null);
	}

	public Ref(T value) {
		this.setValue(value);
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
}
