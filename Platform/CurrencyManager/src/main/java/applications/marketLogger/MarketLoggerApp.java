package applications.marketLogger;

import applications.Application;
import applications.components.stages.ApplicationStage;
import applications.components.stages.StageFactory;
import javafx.application.Platform;

/**
 * Application for tracking selected currencies.
 * 
 * @author Stephen Buttolph
 */
public class MarketLoggerApp extends Application {
	private ApplicationStage stage;

	@Override
	public void start() throws Exception {
		stage = StageFactory.getMarketLogger();
		stage.setOnHidden(e -> Platform.exit());
		stage.start();
	}

	@Override
	public void stop() {
		stage.stop();
	}

	public static void main(String[] args) {
		launch(args);
	}
}