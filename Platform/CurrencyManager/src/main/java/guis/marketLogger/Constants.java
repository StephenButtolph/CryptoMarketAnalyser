package guis.marketLogger;

import javafx.scene.image.Image;

class Constants {
	static final String GUI_XML_PATH;
	static final String TRACKING_PATH;
	static final String LOG_UPDATES_PATH;
	static final String LOG_PATH_PATH;

	static final Image icon;

	static {
		GUI_XML_PATH = "MarketLogger.fxml";

		TRACKING_PATH = "Tracking.json";
		LOG_UPDATES_PATH = "/Loggers/MarketLogger/LastUpdates.json";
		LOG_PATH_PATH = "/Loggers/MarketLogger/LogPath.path";
		icon = new Image(Constants.class.getResourceAsStream("/Loggers/Icons/blockchain.png"));
	}
}
