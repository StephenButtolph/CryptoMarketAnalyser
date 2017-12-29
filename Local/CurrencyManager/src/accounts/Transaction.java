package accounts;

import arithmetic.Pfloat;
import currencies.Currency;

public class Transaction {
	private Holding offer;
	
	private Currency commodity;
	// amount of currency that it would take to get 1 unit of the commodity
	private Pfloat price;
	
	public Transaction(Holding offer, Currency commodity, Pfloat price) {
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
	
	public Pfloat getPrice() {
		return this.price;
	}
}
