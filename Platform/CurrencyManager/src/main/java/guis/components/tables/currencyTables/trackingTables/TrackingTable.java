package guis.components.tables.currencyTables.trackingTables;

import java.time.Duration;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import arithmetic.Pfloat;
import guis.components.tables.currencyTables.CurrencyTable;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import platforms.currencies.Currency;
import platforms.tickers.coinMarketCap.CoinMarketCap;

public class TrackingTable extends CurrencyTable<TrackingData> {
	private final CoinMarketCap coinMarketCap;
	private final Set<Currency> trackingCurrencies;

	public TrackingTable(CoinMarketCap coinMarketCap, Collection<Currency> currencies) {
		this(coinMarketCap, currencies, null);
	}

	public TrackingTable(CoinMarketCap coinMarketCap, Collection<Currency> currencies, Duration autoRefreshRate) {
		super(autoRefreshRate);

		this.coinMarketCap = coinMarketCap;
		this.trackingCurrencies = new HashSet<>(currencies);

		this.setEditable(true);
	}

	public Set<Currency> getTrackingCurrencies() {
		return trackingCurrencies;
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

		TrackingData newData = new TrackingData(currency, isTracking, rank, currency.toString(), price, marketCap,
				volume);
		newData.isTracking.addListener(new IsTrackingListener(trackingCurrencies, currency));
		return newData;
	}

}
