package utils.timing;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class TimingUtils {
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

	public static final DateTimeFormatter FULL;
	public static final DateTimeFormatter LONG;
	public static final DateTimeFormatter MEDIUM;
	public static final DateTimeFormatter SHORT;

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

		FULL = makeFormatter(FormatStyle.FULL);
		LONG = makeFormatter(FormatStyle.LONG);
		MEDIUM = makeFormatter(FormatStyle.MEDIUM);
		SHORT = makeFormatter(FormatStyle.SHORT);
	}

	private static DateTimeFormatter makeFormatter(FormatStyle format) {
		return DateTimeFormatter.ofLocalizedDateTime(format).withZone(ZoneId.systemDefault());
	}

	public static String getCurrentUnixTimestamp() {
		long unixTimestamp = Instant.now().getEpochSecond();
		return String.valueOf(unixTimestamp);
	}

	public static String getCurrentTimestampFull() {
		return FULL.format(Instant.now());
	}

	public static String getCurrentTimestampLong() {
		return LONG.format(Instant.now());
	}

	public static String getCurrentTimestampMedium() {
		return MEDIUM.format(Instant.now());
	}

	public static String getCurrentTimestampShort() {
		return SHORT.format(Instant.now());
	}

	public static Instant getNextMultiple(Duration duration) {
		long multiplicand = getNumPeriods(Instant.EPOCH, duration);
		Duration untilNextMultiple = duration.multipliedBy(multiplicand + 1);
		return Instant.EPOCH.plus(untilNextMultiple);
	}

	public static long getNumPeriods(Instant base, Duration duration) {
		Duration difference = Duration.between(base, Instant.now());
		return difference.toMillis() / duration.toMillis();
	}
}
