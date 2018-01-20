package guis.marketLogger;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import arithmetic.Pfloat;
import constants.Timing;
import currencies.Currency;
import currencies.CurrencyFactory;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tickers.coinMarketCap.CoinMarketCap;
import types.TypeProducer;
import types.TypeToken;
import utils.collections.collections.CollectionUtils;
import utils.files.FileUtils;
import utils.guis.ThreadingUtils;

/**
 * Application for tracking selected currencies.
 * 
 * @author Stephen Buttolph
 */
public class Gui extends Application {
	private static ObservableList<CurrencyData> rows;
	private static Button refreshButton;

	private static CoinMarketCap coinMarketCap;
	private static MarketLogger marketLogger;
	private static Set<Currency> trackingCurrencies;

	static {
		coinMarketCap = new CoinMarketCap(Timing.SECOND);
		marketLogger = new MarketLogger(coinMarketCap);

		TypeProducer typeProducer = new TypeToken<List<String>>();
		List<String> tracking = FileUtils.load(Constants.TRACKING_PATH, typeProducer);
		Collection<Currency> currencies = CollectionUtils.convert(tracking, CurrencyFactory::parseCurrency);
		trackingCurrencies = new HashSet<>(currencies);

		marketLogger.setCurrencies(trackingCurrencies);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource(Constants.GUI_XML_PATH));
		stage.setTitle("Currency Logger");

		GridPane layoutGrid = (GridPane) root.lookup("#layoutGrid");
		refreshButton = (Button) root.lookup("#refreshButton");

		TableView<CurrencyData> table = initTable();
		rows = table.getItems();

		layoutGrid.add(table, 0, 0);

		handleRefresh(null);
		marketLogger.start();

		stage.setScene(new Scene(root));
		stage.show();
	}
	
	@Override
	public void stop(){
		marketLogger.stop();
	}

	private TableView<CurrencyData> initTable() {
		TableView<CurrencyData> table = new TableView<>();
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setEditable(true);

		ObservableList<TableColumn<CurrencyData, ?>> columns = table.getColumns();

		final TableColumn<CurrencyData, Boolean> trackingColumn = new TableColumn<>("Tracking");
		trackingColumn.setCellValueFactory(new PropertyValueFactory<>("isTracking"));
		trackingColumn.setCellFactory(tc -> new CheckBoxTableCell<>());
		trackingColumn.setStyle("-fx-alignment: CENTER;");
		trackingColumn.setMinWidth(100);
		trackingColumn.setMaxWidth(125);
		columns.add(trackingColumn);

		final TableColumn<CurrencyData, Integer> rankColumn = new TableColumn<>("Rank");
		rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
		rankColumn.setStyle("-fx-alignment: CENTER;");
		rankColumn.setMinWidth(65);
		rankColumn.setMaxWidth(75);
		columns.add(rankColumn);

		final TableColumn<CurrencyData, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameColumn.setStyle("-fx-alignment: CENTER;");
		nameColumn.setMinWidth(75);
		columns.add(nameColumn);

		final TableColumn<CurrencyData, Pfloat> priceColumn = new TableColumn<>("Price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		priceColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		priceColumn.setMinWidth(75);
		columns.add(priceColumn);

		final TableColumn<CurrencyData, Pfloat> marketCapColumn = new TableColumn<>("Market Cap");
		marketCapColumn.setCellValueFactory(new PropertyValueFactory<>("marketCap"));
		marketCapColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		marketCapColumn.setMinWidth(115);
		columns.add(marketCapColumn);

		final TableColumn<CurrencyData, Pfloat> volumeColumn = new TableColumn<>("Volume");
		volumeColumn.setCellValueFactory(new PropertyValueFactory<>("volume"));
		volumeColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		volumeColumn.setMinWidth(85);
		columns.add(volumeColumn);

		return table;
	}

	@FXML
	private void handleRefresh(ActionEvent event) {
		refreshButton.setDisable(true);
		ThreadingUtils.run(this::loadRows, this::refreshRows);
	}

	private ObservableList<CurrencyData> loadRows() {
		ObservableList<CurrencyData> newRows = FXCollections.observableArrayList();
		for (Currency currency : CurrencyFactory.getAllCurrencies()) {
			boolean isTracking = trackingCurrencies.contains(currency);
			Pfloat floatRank = coinMarketCap.getRank(currency);
			int rank;
			if (floatRank.isDefined()) {
				rank = Integer.parseInt(floatRank.toString());
			} else {
				continue;
			}
			Pfloat price = coinMarketCap.getUsdPrice(currency);
			Pfloat marketCap = coinMarketCap.getMarketCap(currency);
			Pfloat volume = coinMarketCap.get24HVolume(currency);

			newRows.add(new CurrencyData(currency, isTracking, rank, currency.toString(), price, marketCap, volume));
		}
		return newRows;
	}

	private void refreshRows(ObservableList<CurrencyData> newRows) {
		rows.clear();
		rows.addAll(newRows);
		refreshButton.setDisable(false);
	}

	@FXML
	private void handleSave(ActionEvent event) {
		trackingCurrencies.clear();
		for (CurrencyData row : rows) {
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