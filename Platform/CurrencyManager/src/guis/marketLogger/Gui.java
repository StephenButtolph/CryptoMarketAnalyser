package guis.marketLogger;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import constants.Timing;
import currencies.Currency;
import currencies.CurrencyFactory;
import guis.components.tables.currencyTables.trackingTables.TrackingData;
import guis.components.tables.currencyTables.trackingTables.TrackingTable;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tickers.coinMarketCap.CoinMarketCap;
import types.TypeProducer;
import types.TypeToken;
import utils.collections.collections.CollectionUtils;
import utils.files.FileUtils;

/**
 * Application for tracking selected currencies.
 * 
 * @author Stephen Buttolph
 */
public class Gui extends Application {
	private static CoinMarketCap coinMarketCap;
	private static MarketLogger marketLogger;
	private static Set<Currency> trackingCurrencies;

	private static Stage stage;
	private static FileChooser fileChooser;
	private static TrackingTable table;

	static {
		fileChooser = new FileChooser();

		coinMarketCap = new CoinMarketCap(Timing.SECOND);
		marketLogger = new MarketLogger(coinMarketCap);

		TypeProducer typeProducer = new TypeToken<List<String>>();
		List<String> tracking = FileUtils.load(Constants.TRACKING_PATH, typeProducer);
		Collection<Currency> currencies = CollectionUtils.convert(tracking, CurrencyFactory::parseCurrency);
		trackingCurrencies = new HashSet<>(currencies);

		marketLogger.setCurrencies(trackingCurrencies);

		table = new TrackingTable(coinMarketCap, trackingCurrencies);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setEditable(true);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Gui.stage = stage;

		Parent root = FXMLLoader.load(getClass().getResource(Constants.GUI_XML_PATH));
		stage.setTitle("Currency Logger");

		GridPane layoutGrid = (GridPane) root.lookup("#layoutGrid");
		layoutGrid.add(table, 0, 0, 2, 1);

		handleRefresh(null);
		marketLogger.start();

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