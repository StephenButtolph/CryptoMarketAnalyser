package applications.components.stages.loggers.marketLogger;

import javafx.scene.image.Image;

class Constants {
	static final String TITLE;
	static final String GUI_XML_PATH;

	static final Image icon;

	static {
		TITLE = "Currency Logger";
		GUI_XML_PATH = "/Windows/Loggers/MarketLogger/MarketLogger.fxml";
		icon = new Image(Constants.class.getResourceAsStream("/Windows/Loggers/MarketLogger/icon.png"));
	}
}
