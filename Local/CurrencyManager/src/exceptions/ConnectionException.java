package exceptions;

/**
 * This exception should be thrown when a internet connection has failed.
 * 
 * @author Stephen Buttolph
 */
public class ConnectionException extends RuntimeException {
	/**
	 * Generated version ID for serialization.
	 */
	private static final long serialVersionUID = -4557489127828571771L;

	/**
	 * Create a new connection exception with no message.
	 */
	public ConnectionException() {
		super();
	}

	/**
	 * Create a new connection exception with the error message [err].
	 * 
	 * @param err
	 *            The error message.
	 */
	public ConnectionException(String err) {
		super(err);
	}
}
