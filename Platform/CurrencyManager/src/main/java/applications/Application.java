package applications;

import javafx.stage.Stage;

public abstract class Application extends javafx.application.Application {
	public abstract void start() throws Exception;

	@Override
	public void start(Stage primaryStage) throws Exception {
		start();
	}

}
