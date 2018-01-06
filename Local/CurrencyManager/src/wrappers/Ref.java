package wrappers;

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

	public T dereference() {
		return value;
	}

	@Override
	public T getValue() {
		return dereference();
	}

	public Ref<Ref<T>> reference() {
		return new Ref<>(this);
	}

	public void assign(T value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "*" + value.toString();
	}
}
