package accounts;

import org.apfloat.Apfloat;

import currencies.Currency;

public class Holding {
	private Currency currency;
	private Apfloat amount;
	
	public Holding(Currency currency, Apfloat amount) {
		this.currency = currency;
		this.amount = amount;
	}
	
	public Currency getCurrency() {
		return this.currency;
	}
	
	public Apfloat getAmount() {
		return this.amount;
	}
}
