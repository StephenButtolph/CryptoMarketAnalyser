package guis.marketLogger;

import java.io.File;
import java.io.IOException;

import constants.Timing;
import guis.components.tables.currencyTables.trackingTables.TrackingTable;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import platforms.tickers.coinMarketCap.CoinMarketCap;

/**
 * Application for tracking selected currencies.
 * 
 * @author Stephen Buttolph
 */
public class Gui extends Application {
	// When left as instance variables, the values seem to be overwritten with null
	// values. This seems to be related to the Gui constructor being called twice,
	// however it is unclear why that is happening.
	private static Stage stage;
	private static FileChooser fileChooser;
	
	private static MarketLogger marketLogger;
	private static TrackingTable table;

	@Override
	public void init() {
		fileChooser = new FileChooser();

		CoinMarketCap coinMarketCap = new CoinMarketCap(Timing.SECOND);

		marketLogger = new MarketLogger(coinMarketCap);
		table = new TrackingTable(coinMarketCap, marketLogger.getCurrencies(), Timing.MINUTE);
	}

	@Override
	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource(Constants.GUI_XML_PATH));
		stage.setTitle("Currency Logger");

		GridPane layoutGrid = (GridPane) root.lookup("#layoutGrid");
		layoutGrid.add(table, 0, 0, 2, 1);

		Gui.stage = stage;
		stage.setScene(new Scene(root));
		stage.getIcons().add(Constants.icon);
		
		marketLogger.start();
		stage.show();
	}

	@Override
	public void stop() {
		marketLogger.stop();
	}

	@FXML
	private void handleRefresh(ActionEvent event) {
		table.refresh();
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

	public static void main(String[] args) {
		launch(args);
	}
}