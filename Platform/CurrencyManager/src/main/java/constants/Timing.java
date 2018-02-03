package constants;

import java.time.Duration;

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
	public static final Duration ZERO;

	/**
	 * A one nanosecond of time.
	 */
	public static final Duration NANOSECOND;

	/**
	 * A one millisecond of time.
	 */
	public static final Duration MILLISECOND;

	/**
	 * A one second of time.
	 */
	public static final Duration SECOND;

	/**
	 * A one minute of time.
	 */
	public static final Duration MINUTE;

	/**
	 * A one hour of time.
	 */
	public static final Duration HOUR;

	/**
	 * A half of one day of time.
	 */
	public static final Duration HALF_DAY;

	/**
	 * A one day of time.
	 */
	public static final Duration DAY;

	/**
	 * A one week of time.
	 */
	public static final Duration WEEK;

	static {
		ZERO = Duration.ZERO;
		NANOSECOND = Duration.ofNanos(1);
		MILLISECOND = Duration.ofMillis(1);
		SECOND = Duration.ofSeconds(1);
		MINUTE = Duration.ofMinutes(1);
		HOUR = Duration.ofHours(1);
		HALF_DAY = Duration.ofHours(12);
		DAY = Duration.ofDays(1);
		WEEK = Duration.ofDays(7);
	}
}
