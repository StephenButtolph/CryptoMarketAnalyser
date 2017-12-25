package constants;

public class Timing {
	public static final long TransactionHoldTimeMillis;
	
	static {
		TransactionHoldTimeMillis = 5000; // 5 seconds, should be made parsed 
	}
}
