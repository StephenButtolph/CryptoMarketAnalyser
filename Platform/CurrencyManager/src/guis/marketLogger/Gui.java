package guis.marketLogger;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

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
import platforms.currencies.Currency;
import platforms.currencies.CurrencyFactory;
import platforms.tickers.coinMarketCap.CoinMarketCap;
import utils.collections.collections.CollectionUtils;
import utils.files.FileUtils;
import utils.types.TypeProducer;
import utils.types.TypeToken;

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
	private static TrackingTable table;
	
	private static MarketLogger marketLogger;

	@Override
	public void start(Stage stage) throws IOException {
		Gui.stage = stage;
		fileChooser = new FileChooser();

		CoinMarketCap coinMarketCap = new CoinMarketCap(Timing.SECOND);

		TypeProducer typeProducer = new TypeToken<List<String>>();
		List<String> tracking = FileUtils.load(Constants.TRACKING_PATH, typeProducer);
		Collection<Currency> currencies = CollectionUtils.convert(tracking, CurrencyFactory::parseCurrency);

		table = new TrackingTable(coinMarketCap, currencies, Timing.MINUTE);

		marketLogger = new MarketLogger(coinMarketCap);
		marketLogger.setCurrencies(table.getTrackingCurrencies());
		marketLogger.start();

		Parent root = FXMLLoader.load(getClass().getResource(Constants.GUI_XML_PATH));
		stage.setTitle("Currency Logger");

		GridPane layoutGrid = (GridPane) root.lookup("#layoutGrid");
		layoutGrid.add(table, 0, 0, 2, 1);

		stage.setScene(new Scene(root));
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
			String path = chosen.getAbsolutePath();
			FileUtils.write(Constants.LOG_PATH_PATH, path);
		}
	}

	@FXML
	private void handleSave(ActionEvent event) {
		TypeProducer typeProducer = new TypeToken<List<String>>();
		Collection<String> toSave = CollectionUtils.convert(table.getTrackingCurrencies(), Object::toString);
		FileUtils.save(Constants.TRACKING_PATH, toSave, typeProducer);

		Collection<Currency> currencies = table.getTrackingCurrencies();
		marketLogger.setCurrencies(currencies);
	}

	public static void main(String[] args) {
		launch(args);
	}
}