package exceptions;

public class AssertionException extends RuntimeException {
	/**
	 * Generated version ID for serialization.
	 */
	private static final long serialVersionUID = 3736141595879136816L;

	public AssertionException() {
		super();
	}

	public AssertionException(String err) {
		super(err);
	}
}
