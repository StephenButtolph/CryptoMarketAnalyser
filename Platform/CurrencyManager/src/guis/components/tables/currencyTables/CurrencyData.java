package guis.components.tables.currencyTables;

import arithmetic.Pfloat;
import arithmetic.PfloatCurrency;
import currencies.Currency;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CurrencyData {
	private final Currency currency;

	private final IntegerProperty rank;
	private final StringProperty name;
	private final ObjectProperty<Pfloat> price;
	private final ObjectProperty<Pfloat> marketCap;
	private final ObjectProperty<Pfloat> volume;

	public CurrencyData(Currency currency, int rank, String name, Pfloat price, Pfloat marketCap,
			Pfloat volume) {
		this.currency = currency;
		this.rank = new SimpleIntegerProperty(rank);
		this.name = new SimpleStringProperty(name);
		this.price = new SimpleObjectProperty<>(new PfloatCurrency(price));
		this.marketCap = new SimpleObjectProperty<>(new PfloatCurrency(marketCap));
		this.volume = new SimpleObjectProperty<>(new PfloatCurrency(volume));
	}

	public Currency getCurrency() {
		return currency;
	}

	public IntegerProperty rankProperty() {
		return rank;
	}

	public StringProperty nameProperty() {
		return name;
	}

	public ObjectProperty<Pfloat> priceProperty() {
		return price;
	}

	public ObjectProperty<Pfloat> marketCapProperty() {
		return marketCap;
	}

	public ObjectProperty<Pfloat> volumeProperty() {
		return volume;
	}
}
