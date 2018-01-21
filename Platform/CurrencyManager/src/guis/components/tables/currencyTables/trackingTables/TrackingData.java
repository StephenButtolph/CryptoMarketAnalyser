package guis.components.tables.currencyTables.trackingTables;

import arithmetic.Pfloat;
import guis.components.tables.currencyTables.CurrencyData;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import platforms.currencies.Currency;

public class TrackingData extends CurrencyData {
	protected final Currency currency;
	protected final BooleanProperty isTracking;

	TrackingData(Currency currency, boolean isTracking, int rank, String name, Pfloat price, Pfloat marketCap,
			Pfloat volume) {
		super(rank, name, price, marketCap, volume);

		this.currency = currency;
		this.isTracking = new SimpleBooleanProperty(isTracking);
	}

	public Currency getCurrency() {
		return currency;
	}

	public BooleanProperty isTrackingProperty() {
		return isTracking;
	}
}
