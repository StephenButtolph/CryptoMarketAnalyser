package applications.components.tables.currencyTables.trackingTables;

import applications.components.tables.currencyTables.CurrencyData;
import arithmetic.Pfloat;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import platforms.currencies.Currency;

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
