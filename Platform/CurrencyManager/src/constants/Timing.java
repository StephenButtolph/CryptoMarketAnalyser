package constants;

import java.time.Duration;
import java.time.temporal.TemporalAmount;

/**
 * This is a time manager to control constant time periods, such as seconds or
 * hours, and to control default time periods.
 * 
 * @author Stephen Buttolph
 */
public class Timing {
	/**
	 * No time amount.
	 */
	public static final TemporalAmount ZERO;

	/**
	 * A one nanosecond time amount.
	 */
	public static final TemporalAmount NANOSECOND;

	/**
	 * A one millisecond time amount.
	 */
	public static final TemporalAmount MILLISECOND;

	/**
	 * A one second time amount.
	 */
	public static final TemporalAmount SECOND;

	/**
	 * A one minute time amount.
	 */
	public static final TemporalAmount MINUTE;

	/**
	 * A one hour time amount.
	 */
	public static final TemporalAmount HOUR;

	/**
	 * A one day time amount.
	 */
	public static final TemporalAmount DAY;

	/**
	 * A one week time amount.
	 */
	public static final TemporalAmount WEEK;

	static {
		ZERO = Duration.ZERO;
		NANOSECOND = Duration.ofNanos(1);
		MILLISECOND = Duration.ofMillis(1);
		SECOND = Duration.ofSeconds(1);
		MINUTE = Duration.ofMinutes(1);
		HOUR = Duration.ofHours(1);
		DAY = Duration.ofDays(1);
		WEEK = Duration.ofDays(7);
	}
}
