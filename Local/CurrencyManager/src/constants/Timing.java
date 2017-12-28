package constants;

import java.time.Duration;
import java.time.temporal.TemporalAmount;

public class Timing {
	public static final TemporalAmount SECOND;
	public static final TemporalAmount MINUTE;
	public static final TemporalAmount HOUR;
	public static final TemporalAmount DAY;
	
	public static final TemporalAmount TransactionHoldDuration;
	public static final TemporalAmount CurrencyNameMappingHoldDuration;
	
	static {
		SECOND = Duration.ofSeconds(1);
		MINUTE = Duration.ofMinutes(1);
		HOUR = Duration.ofHours(1);
		DAY = Duration.ofDays(1);
		
		TransactionHoldDuration = Duration.ofSeconds(5); // should be made parsed
		CurrencyNameMappingHoldDuration = DAY; // should be made parsed
	}
}
