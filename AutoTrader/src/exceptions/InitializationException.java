package exceptions;

public class InitializationException extends RuntimeException {
	private static final long serialVersionUID = 6375286367621597311L;

	public InitializationException(Throwable thrown){
		super(thrown);
	}
}
