package guis.components.tables.currencyTables.trackingTables;

import java.util.Collection;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import platforms.currencies.Currency;

public class IsTrackingListener implements ChangeListener<Boolean> {
	private final Collection<Currency> currencies;
	private final Currency currency;

	public IsTrackingListener(Collection<Currency> currencies, Currency currency) {
		this.currencies = currencies;
		this.currency = currency;
	}

	@Override
	public void changed(ObservableValue<? extends Boolean> observable, Boolean oldVal, Boolean newVal) {
		if (newVal) {
			currencies.add(currency);
		} else {
			currencies.remove(currency);
		}
	}
}
