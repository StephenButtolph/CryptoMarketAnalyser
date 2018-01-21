package guis.marketLogger;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import constants.Timing;
import guis.components.tables.currencyTables.trackingTables.TrackingData;
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
	private CoinMarketCap coinMarketCap;
	private MarketLogger marketLogger;
	private Set<Currency> trackingCurrencies;

	private Stage stage;
	private FileChooser fileChooser;
	private TrackingTable table;

	public Gui() {
		fileChooser = new FileChooser();

		coinMarketCap = new CoinMarketCap(Timing.SECOND);
		marketLogger = new MarketLogger(coinMarketCap);

		TypeProducer typeProducer = new TypeToken<List<String>>();
		List<String> tracking = FileUtils.load(Constants.TRACKING_PATH, typeProducer);
		Collection<Currency> currencies = CollectionUtils.convert(tracking, CurrencyFactory::parseCurrency);
		trackingCurrencies = new HashSet<>(currencies);

		marketLogger.setCurrencies(trackingCurrencies);
		marketLogger.start();

		table = new TrackingTable(coinMarketCap, trackingCurrencies, Timing.MINUTE);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;

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
		trackingCurrencies.clear();
		for (TrackingData row : table.getItems()) {
			if (row.isTrackingProperty().get()) {
				trackingCurrencies.add(row.getCurrency());
			}
		}

		TypeProducer typeProducer = new TypeToken<List<String>>();
		Collection<String> toSave = CollectionUtils.convert(trackingCurrencies, Object::toString);
		FileUtils.save(Constants.TRACKING_PATH, toSave, typeProducer);

		marketLogger.setCurrencies(trackingCurrencies);
	}

	public static void main(String[] args) {
		launch(args);
	}
}