package utils.timing;

import java.time.Instant;

public class TimingUtils {
	public static String getCurrentUnixTimestamp() {
		long unixTimestamp = Instant.now().getEpochSecond();
		return String.valueOf(unixTimestamp);
	}
}
