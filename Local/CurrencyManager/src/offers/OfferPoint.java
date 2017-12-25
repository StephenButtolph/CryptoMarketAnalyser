package offers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;

import utils.AssertUtils;
import utils.IterableUtils;

public class OfferPoint implements Iterable<Offer> {
	private Collection<BigDecimal> coll;
	private BigDecimal price;

	public OfferPoint(BigDecimal price, BigDecimal... amounts) {
		this.coll = new ArrayList<>();
		this.price = price;
		
		add(amounts);
	}

	public OfferPoint(Offer firstOffer, Offer... offers) {
		this.coll = new ArrayList<>();
		this.price = firstOffer.getPrice();
		this.coll.add(firstOffer.getAmount());
		
		for (Offer offer : offers) {
			AssertUtils.assertEquals(price, offer.getPrice());
			coll.add(offer.getAmount());
		}
	}
	
	public BigDecimal getPrice() {
		return price;
	}

	public void add(Offer... offers) {
		for (Offer offer : offers) {
			AssertUtils.assertEquals(price, offer.getPrice());
			coll.add(offer.getAmount());
		}
	}

	public void add(BigDecimal... amounts) {
		for (BigDecimal amount : amounts) {
			coll.add(amount);
		}
	}
	
	public Iterator<BigDecimal> amountIterator(){
		return coll.iterator();
	}

	@Override
	public Iterator<Offer> iterator() {
		Function<BigDecimal, Offer> mapper = amount -> new Offer(getPrice(), amount);
		return IterableUtils.map(coll, mapper).iterator();
	}
}
