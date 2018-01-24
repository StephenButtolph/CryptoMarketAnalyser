package utils.wrappers;

/*
 * Used to be able to simulate passed by reference function calls.
 */
public class Ref<T> implements Wrapper<T> {
	private T value;

	public Ref() {
		this(null);
	}

	public Ref(T value) {
		this.assign(value);
	}

	public T deref() {
		return value;
	}

	@Override
	public T getValue() {
		return deref();
	}

	public Ref<Ref<T>> ref() {
		return new Ref<>(this);
	}

	public void assign(T value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "*" + value.toString();
	}

	public static <T> Ref<T> ref(T value) {
		return new Ref<>(value);
	}
}
