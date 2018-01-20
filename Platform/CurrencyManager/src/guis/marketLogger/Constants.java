package guis.marketLogger;

import exceptions.StaticLoadException;
import utils.resources.ResourceUtils;

class Constants {
	static final String GUI_XML_PATH;
	static final String TRACKING_PATH;
	static final String LOG_UPDATES_PATH;
	static final String LOG_PATH_PATH;

	static {
		GUI_XML_PATH = "MarketLogger.fxml";

		final String TRACKING_LOCAL_PATH = "/Loggers/MarketLogger/Tracking.json";
		TRACKING_PATH = ResourceUtils.getResourcePath(TRACKING_LOCAL_PATH);
		if (TRACKING_PATH == null) {
			throw new StaticLoadException("Market Logger tracking path failed to load.");
		}

		final String LOG_UPDATES_LOCAL_PATH = "/Loggers/MarketLogger/LastUpdates.json";
		LOG_UPDATES_PATH = ResourceUtils.getResourcePath(LOG_UPDATES_LOCAL_PATH);
		if (LOG_UPDATES_PATH == null) {
			throw new StaticLoadException("Market Logger updates path failed to load.");
		}

		final String LOG_PATH_LOCAL_PATH = "/Loggers/MarketLogger/LogPath.path";
		LOG_PATH_PATH = ResourceUtils.getResourcePath(LOG_PATH_LOCAL_PATH);
		if (LOG_PATH_PATH == null) {
			throw new StaticLoadException("Market Logger log path failed to load.");
		}
	}
}
