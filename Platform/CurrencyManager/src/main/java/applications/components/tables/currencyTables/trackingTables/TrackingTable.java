package applications.components.tables.currencyTables.trackingTables;

import java.time.Duration;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import applications.components.tables.currencyTables.CurrencyTable;
import arithmetic.Pfloat;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import platforms.currencies.Currency;
import platforms.tickers.coinMarketCap.CoinMarketCap;

public class TrackingTable extends CurrencyTable<TrackingData> {
	private final CoinMarketCap coinMarketCap;
	private Set<Currency> trackingCurrencies;

	public TrackingTable(CoinMarketCap coinMarketCap) {
		this(coinMarketCap, null, null);
	}

	public TrackingTable(CoinMarketCap coinMarketCap, Duration autoRefreshRate) {
		this(coinMarketCap, null, autoRefreshRate);
	}

	public TrackingTable(CoinMarketCap coinMarketCap, Collection<Currency> currencies) {
		this(coinMarketCap, currencies, null);
	}

	public TrackingTable(CoinMarketCap coinMarketCap, Collection<Currency> currencies, Duration autoRefreshRate) {
		super(autoRefreshRate);

		this.coinMarketCap = coinMarketCap;
		setTrackingCurrencies(currencies);

		this.setEditable(true);
	}

	public Set<Currency> getTrackingCurrencies() {
		return trackingCurrencies;
	}

	public void setTrackingCurrencies(Collection<Currency> currencies) {
		if (currencies == null) {
			this.trackingCurrencies = new HashSet<>();
		} else {
			this.trackingCurrencies = new HashSet<>(currencies);
			refreshRows();
		}
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
