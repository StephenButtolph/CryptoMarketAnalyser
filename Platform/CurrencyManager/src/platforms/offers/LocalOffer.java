package platforms.offers;

import arithmetic.Pfloat;

public class LocalOffer implements Offer {
	private final Pfloat price;
	private final Pfloat amount;

	public LocalOffer(Pfloat price, Pfloat amount) {
		this.price = price;
		this.amount = amount;
	}

	public Pfloat getPrice() {
		return price;
	}

	public Pfloat getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "Offer: Price = " + price + ", Amount = " + amount;
	}
}
