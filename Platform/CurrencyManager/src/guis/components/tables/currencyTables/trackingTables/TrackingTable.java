package guis.components.tables.currencyTables.trackingTables;

import java.util.Collection;

import arithmetic.Pfloat;
import guis.components.tables.currencyTables.CurrencyTable;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import platforms.currencies.Currency;
import platforms.tickers.coinMarketCap.CoinMarketCap;

public class TrackingTable extends CurrencyTable<TrackingData> {
	private CoinMarketCap coinMarketCap;
	private Collection<Currency> trackingCurrencies;

	public TrackingTable(CoinMarketCap coinMarketCap, Collection<Currency> trackingCurrencies) {
		this.coinMarketCap = coinMarketCap;
		this.trackingCurrencies = trackingCurrencies;
	}

	@Override
	protected void initializeColumns() {
		ObservableList<TableColumn<TrackingData, ?>> columns = this.getColumns();

		final TableColumn<TrackingData, Boolean> trackingColumn = new TableColumn<>("Tracking");
		trackingColumn.setCellValueFactory(new PropertyValueFactory<>("isTracking"));
		trackingColumn.setCellFactory(tc -> new CheckBoxTableCell<>());
		trackingColumn.setStyle("-fx-alignment: CENTER;");
		trackingColumn.setMinWidth(100);
		trackingColumn.setMaxWidth(125);
		columns.add(trackingColumn);

		super.initializeColumns();
	}

	@Override
	protected TrackingData loadRow(Currency currency) {
		boolean isTracking = trackingCurrencies.contains(currency);
		Pfloat floatRank = coinMarketCap.getRank(currency);

		int rank;
		if (floatRank.isDefined()) {
			rank = Integer.parseInt(floatRank.toString());
		} else {
			return null;
		}

		Pfloat price = coinMarketCap.getUsdPrice(currency);
		Pfloat marketCap = coinMarketCap.getMarketCap(currency);
		Pfloat volume = coinMarketCap.get24HVolume(currency);

		return new TrackingData(currency, isTracking, rank, currency.toString(), price, marketCap, volume);
	}

}
