package exceptions;

/**
 * This exception should be thrown when a critical assumption has failed. This
 * should ideally only be thrown on 'rare' system dependent errors.
 * 
 * @author Stephen Buttolph
 */
public class AssertionException extends RuntimeException {
	/**
	 * Generated version ID for serialization.
	 */
	private static final long serialVersionUID = 3736141595879136816L;

	/**
	 * Create a new assertion exception with no message.
	 */
	public AssertionException() {
		super();
	}

	/**
	 * Create a new assertion exception with the error message [err].
	 * 
	 * @param err
	 *            The error message.
	 */
	public AssertionException(String err) {
		super(err);
	}
}
