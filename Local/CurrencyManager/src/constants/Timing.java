package constants;

import java.time.Duration;
import java.time.temporal.TemporalAmount;

public class Timing {
	public static final TemporalAmount TransactionHoldDuration;
	
	static {
		TransactionHoldDuration = Duration.ofSeconds(5); // should be made parsed 
	}
}
