package applications.marketLogger;

import java.io.File;
import java.io.IOException;

import applications.components.tables.currencyTables.trackingTables.TrackingTable;
import constants.Timing;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logging.loggers.currencyLoggers.marketLogger.MarketLogger;
import platforms.tickers.coinMarketCap.CoinMarketCap;
import utils.guis.ThreadingUtils;

/**
 * Application for tracking selected currencies.
 * 
 * @author Stephen Buttolph
 */
public class MarketLoggerApp extends Application {
	// When left as instance variables, the values seem to be overwritten with null
	// values. This seems to be related to the Gui constructor being called twice,
	// however it is unclear why that is happening.
	private static Stage stage;
	private static FileChooser fileChooser;

	private static MarketLogger marketLogger;
	private static TrackingTable table;

	@Override
	public void start(Stage stage) throws IOException {
		fileChooser = new FileChooser();
		CoinMarketCap coinMarketCap = new CoinMarketCap(Timing.SECOND);
		table = new TrackingTable(coinMarketCap, Timing.MINUTE);

		Parent root = FXMLLoader.load(getClass().getResource(Constants.GUI_XML_PATH));
		stage.setTitle("Currency Logger");

		GridPane layoutGrid = (GridPane) root.lookup("#layoutGrid");
		layoutGrid.add(table, 0, 0, 2, 1);

		MarketLoggerApp.stage = stage;
		stage.setScene(new Scene(root));
		stage.getIcons().add(Constants.icon);

		stage.show();

		ThreadingUtils.run(() -> new MarketLogger(coinMarketCap), MarketLoggerApp::setMarketLogger);
	}

	@Override
	public void stop() {
		if (marketLogger != null) {
			marketLogger.stop();
		}
	}

	@FXML
	private void setNewLogFile(ActionEvent event) {
		File chosen = fileChooser.showOpenDialog(stage);
		if (chosen != null) {
			marketLogger.setLogFile(chosen.getAbsolutePath());
		}
	}

	@FXML
	private void handleSave(ActionEvent event) {
		marketLogger.setCurrencies(table.getTrackingCurrencies());
	}

	private static void setMarketLogger(MarketLogger marketLogger) {
		MarketLoggerApp.marketLogger = marketLogger;
		table.setTrackingCurrencies(marketLogger.getCurrencies());
		marketLogger.start();
	}

	public static void main(String[] args) {
		launch(args);
	}
}