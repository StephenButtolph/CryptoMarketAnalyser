package utils.timing;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class TimingUtils {
	public static final DateTimeFormatter FULL;
	public static final DateTimeFormatter LONG;
	public static final DateTimeFormatter MEDIUM;
	public static final DateTimeFormatter SHORT;

	static {
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
}
