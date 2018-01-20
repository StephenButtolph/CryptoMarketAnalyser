package guis.marketLogger;

import exceptions.StaticLoadException;
import utils.files.FileUtils;
import utils.resources.ResourceUtils;

class Constants {
	static final String GUI_XML_PATH;
	static final String LOG_UPDATES_PATH;
	static final String LOG_PATH;

	static {
		GUI_XML_PATH = "MarketLogger.fxml";

		final String LOG_UPDATES_LOCAL_PATH = "/Loggers/MarketLogger/LastUpdates.json";
		LOG_UPDATES_PATH = ResourceUtils.getResourcePath(LOG_UPDATES_LOCAL_PATH);
		if (LOG_UPDATES_PATH == null) {
			throw new StaticLoadException("Market Logger updates path failed to load.");
		}

		final String LOG_PATH_LOCAL_PATH = "/Loggers/MarketLogger/LogPath.path";
		final String LOG_PATH_PATH = ResourceUtils.getResourcePath(LOG_PATH_LOCAL_PATH);
		LOG_PATH = FileUtils.read(LOG_PATH_PATH);
		if (LOG_PATH == null) {
			throw new StaticLoadException("Market Logger log path failed to load.");
		}
	}
}
