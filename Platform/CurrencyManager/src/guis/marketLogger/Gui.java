package guis.marketLogger;

import arithmetic.Pfloat;
import constants.Timing;
import currencies.Currency;
import currencies.CurrencyFactory;
import javafx.application.Application;
import javafx.application.Platform;
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

/**
 * Application for tracking selected currencies.
 * 
 * @author Stephen Buttolph
 */
public class Gui extends Application {
	private static ObservableList<CurrencyData> rows;
	private static Button refreshButton;

	private static CoinMarketCap coinMarketCap = new CoinMarketCap(Timing.MINUTE);

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource(Constants.GUI_XML_PATH));
		stage.setTitle("Currency Logger");

		GridPane layoutGrid = (GridPane) root.lookup("#layoutGrid");
		refreshButton = (Button) root.lookup("#refreshButton");

		TableView<CurrencyData> table = initTable();
		rows = table.getItems();

		layoutGrid.add(table, 0, 0);

		stage.setScene(new Scene(root));
		handleRefresh(null);
		stage.show();
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
		columns.add(trackingColumn);

		final TableColumn<CurrencyData, Integer> rankColumn = new TableColumn<>("Rank");
		rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
		rankColumn.setStyle("-fx-alignment: CENTER;");
		columns.add(rankColumn);

		final TableColumn<CurrencyData, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameColumn.setStyle("-fx-alignment: CENTER;");
		columns.add(nameColumn);

		final TableColumn<CurrencyData, Pfloat> priceColumn = new TableColumn<>("Price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		priceColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		columns.add(priceColumn);

		final TableColumn<CurrencyData, Pfloat> marketCapColumn = new TableColumn<>("Market Cap");
		marketCapColumn.setCellValueFactory(new PropertyValueFactory<>("marketCap"));
		marketCapColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		columns.add(marketCapColumn);

		final TableColumn<CurrencyData, Pfloat> volumeColumn = new TableColumn<>("Volume");
		volumeColumn.setCellValueFactory(new PropertyValueFactory<>("volume"));
		volumeColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		columns.add(volumeColumn);

		return table;
	}

	@FXML
	private void handleRefresh(ActionEvent event) {
		refreshButton.setDisable(true);

		new Thread(() -> {
			ObservableList<CurrencyData> newRows = FXCollections.observableArrayList();
			for (Currency currency : CurrencyFactory.getAllCurrencies()) {
				boolean isTracking = false;
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

				newRows.add(new CurrencyData(isTracking, rank, currency.toString(), price, marketCap, volume));
			}

			Platform.runLater(() -> {
				rows.clear();
				rows.addAll(newRows);
				refreshButton.setDisable(false);
			});
		}).start();
	}

	@FXML
	private void handleSave(ActionEvent event) {
		for (CurrencyData row : rows) {
			System.out.println(row.isTrackingProperty().get());
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}