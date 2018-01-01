package exceptions;

public class ConnectionException extends RuntimeException {
	/**
	 * Generated version ID for serialization.
	 */
	private static final long serialVersionUID = -4557489127828571771L;

	public ConnectionException() {
		super();
	}

	public ConnectionException(String err) {
		super(err);
	}
}
