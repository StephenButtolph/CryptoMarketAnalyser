package applications.marketLogger;

import javafx.scene.image.Image;

class Constants {
	static final String GUI_XML_PATH;

	static final Image icon;

	static {
		GUI_XML_PATH = "/Loggers/MarketLogger/Gui/MarketLogger.fxml";
		icon = new Image(Constants.class.getResourceAsStream("/Loggers/MarketLogger/Icons/icon.png"));
	}
}
