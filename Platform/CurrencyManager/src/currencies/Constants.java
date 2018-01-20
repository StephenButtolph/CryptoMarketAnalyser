package currencies;

import java.time.temporal.TemporalAmount;

import constants.Timing;

class Constants {
	static final String FORMAT_HEADER;
	static final String FORMAT_DELIMITER;
	static final String FORMAT_FOOTER;
	static final String STRING_FORMAT;
	

	/**
	 * The default amount of time to refresh the currency factory.
	 */
	public static final TemporalAmount REFRESH_FREQUENCY;

	static {
		FORMAT_HEADER = "";
		FORMAT_DELIMITER = " (";
		FORMAT_FOOTER = ")";
		STRING_FORMAT = FORMAT_HEADER + "%s" + FORMAT_DELIMITER + "%s" + FORMAT_FOOTER;
	
		REFRESH_FREQUENCY = Timing.WEEK;
	}
}
