package logging.loggers.currencyLoggers.marketLogger;

class Constants {
	static final String TRACKING_PATH;
	static final String LOG_UPDATES_PATH;
	static final String LOG_PATH_PATH;

	static {
		TRACKING_PATH = "/Loggers/MarketLogger/Tracking.json";
		LOG_UPDATES_PATH = "/Loggers/MarketLogger/LastUpdates.json";
		LOG_PATH_PATH = "/Loggers/MarketLogger/LogPath.path";
	}
}
