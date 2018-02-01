package exceptions;

import logging.debug.DebugLogger;

public class LoggedException extends RuntimeException {
	/**
	 * Generated version ID for serialization.
	 */
	private static final long serialVersionUID = 8435894603779710433L;

	/**
	 * Create and log a new runtime exception.
	 */
	public LoggedException() {
		super();
		DebugLogger.addError(this);
	}

	/**
	 * Create and log a new runtime exception with the error message [err].
	 * 
	 * @param err
	 *            The error message.
	 */
	public LoggedException(String err) {
		super(err);
		DebugLogger.addError(this);
	}
}
