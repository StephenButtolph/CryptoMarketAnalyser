package accounts;

import java.math.BigDecimal;

import currencies.Currency;

public class Transaction {
	private Holding offer;
	
	private Currency commodity;
	// amount of currency that it would take to get 1 unit of the commodity
	private BigDecimal price;
	
	public Transaction(Holding offer, Currency commodity, BigDecimal price) {
		this.offer = offer;
		this.commodity = commodity;
		this.price = price;
	}
	
	public Holding getOffer() {
		return this.offer;
	}
	
	public Currency getCommodity() {
		return this.commodity;
	}
	
	public BigDecimal getPrice() {
		return this.price;
	}
}
