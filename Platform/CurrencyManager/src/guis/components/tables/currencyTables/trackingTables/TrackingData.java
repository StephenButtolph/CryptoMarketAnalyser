package guis.components.tables.currencyTables.trackingTables;

import arithmetic.Pfloat;
import currencies.Currency;
import guis.components.tables.currencyTables.CurrencyData;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class TrackingData extends CurrencyData {
	protected final BooleanProperty isTracking;

	TrackingData(Currency currency, boolean isTracking, int rank, String name, Pfloat price, Pfloat marketCap,
			Pfloat volume) {
		super(currency, rank, name, price, marketCap, volume);

		this.isTracking = new SimpleBooleanProperty(isTracking);
	}

	public BooleanProperty isTrackingProperty() {
		return isTracking;
	}
}
