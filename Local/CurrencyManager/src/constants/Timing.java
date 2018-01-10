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
	 * The default amount of time to refresh the currency factory.
	 */
	public static final TemporalAmount DEFAULT_CURRENCY_MAPPING_REFRESH_FREQUENCY;

	static {
		ZERO = Duration.ZERO;
		SECOND = Duration.ofSeconds(1);
		MINUTE = Duration.ofMinutes(1);
		HOUR = Duration.ofHours(1);
		DAY = Duration.ofDays(1);

		DEFAULT_CURRENCY_MAPPING_REFRESH_FREQUENCY = DAY;
	}
}
