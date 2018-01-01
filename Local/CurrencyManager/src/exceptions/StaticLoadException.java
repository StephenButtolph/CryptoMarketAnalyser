package exceptions;

public class StaticLoadException extends RuntimeException {
	/**
	 * Generated version ID for serialization.
	 */
	private static final long serialVersionUID = -3581807123684564260L;

	public StaticLoadException() {
		super();
	}

	public StaticLoadException(String err) {
		super(err);
	}
}
