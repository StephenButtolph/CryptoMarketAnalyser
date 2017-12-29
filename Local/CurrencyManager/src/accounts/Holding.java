package accounts;

import arithmetic.Pfloat;
import currencies.Currency;

public class Holding {
	private Currency currency;
	private Pfloat amount;
	
	public Holding(Currency currency, Pfloat amount) {
		this.currency = currency;
		this.amount = amount;
	}
	
	public Currency getCurrency() {
		return this.currency;
	}
	
	public Pfloat getAmount() {
		return this.amount;
	}
}
