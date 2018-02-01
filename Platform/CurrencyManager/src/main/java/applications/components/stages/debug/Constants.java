package applications.components.stages.debug;

import javafx.scene.image.Image;

class Constants {
	static final String TITLE;
	static final String XML_PATH;

	static final Image icon;

	static {
		TITLE = "Debugger";
		XML_PATH = "/Windows/Debugger/Debugger.fxml";
		icon = new Image(Constants.class.getResourceAsStream("/Windows/Debugger/icon.png"));
	}
}
