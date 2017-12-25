package accounts;

import java.math.BigDecimal;

import currencies.Currency;

public class Holding {
	private Currency currency;
	private BigDecimal amount;
	
	public Holding(Currency currency, BigDecimal amount) {
		this.currency = currency;
		this.amount = amount;
	}
	
	public Currency getCurrency() {
		return this.currency;
	}
	
	public BigDecimal getAmount() {
		return this.amount;
	}
}
