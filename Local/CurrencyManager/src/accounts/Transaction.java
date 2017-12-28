package accounts;

import org.apfloat.Apfloat;

import currencies.Currency;

public class Transaction {
	private Holding offer;
	
	private Currency commodity;
	// amount of currency that it would take to get 1 unit of the commodity
	private Apfloat price;
	
	public Transaction(Holding offer, Currency commodity, Apfloat price) {
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
	
	public Apfloat getPrice() {
		return this.price;
	}
}
