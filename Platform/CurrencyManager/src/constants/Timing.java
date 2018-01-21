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
	 * A one nanosecond of time.
	 */
	public static final TemporalAmount NANOSECOND;

	/**
	 * A one millisecond of time.
	 */
	public static final TemporalAmount MILLISECOND;

	/**
	 * A one second of time.
	 */
	public static final TemporalAmount SECOND;

	/**
	 * A one minute of time.
	 */
	public static final TemporalAmount MINUTE;

	/**
	 * A one hour of time.
	 */
	public static final TemporalAmount HOUR;

	/**
	 * A one day of time.
	 */
	public static final TemporalAmount DAY;

	/**
	 * A one week of time.
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
