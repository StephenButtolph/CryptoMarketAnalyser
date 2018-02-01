package exceptions;

/**
 * This exception should be thrown when a statically initialized constant failed
 * to be initiallized correctly.
 * 
 * @author Stephen Buttolph
 */
public class StaticLoadException extends LoggedException {
	/**
	 * Generated version ID for serialization.
	 */
	private static final long serialVersionUID = -3581807123684564260L;

	/**
	 * Create a new static load exception with no message.
	 */
	public StaticLoadException() {
		super();
	}

	/**
	 * Create a new static load exception with the error message [err].
	 * 
	 * @param err
	 *            The error message.
	 */
	public StaticLoadException(String err) {
		super(err);
	}
}
