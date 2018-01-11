package offerGroups;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

import arithmetic.Pfloat;
import offers.Offer;
import utils.IterableUtils;

public class OfferPoint implements Iterable<Offer> {
	private Collection<Offer> coll;
	private final Pfloat price;

	public OfferPoint(Offer offer, Offer... offers) {
		this.coll = new ArrayList<>();
		this.price = offer.getPrice();

		add(offer);
		add(offers);
	}

	public Pfloat getPrice() {
		return price;
	}

	public Pfloat getAmount() {
		return IterableUtils.fold(amountIterable(), Pfloat::add, Pfloat.ZERO);
	}

	public void add(Offer... offers) {
		IterableUtils.toIterable(offers).forEach(this::internalAdd);
	}

	private void internalAdd(Offer offer) {
		if (!Objects.equals(this.getPrice(), offer.getPrice())) {
			throw new IllegalArgumentException("Offers must have the same price as the OfferPoint");
		}
		coll.add(offer);
	}

	public Iterable<Pfloat> amountIterable() {
		return IterableUtils.map(coll, Offer::getAmount);
	}

	@Override
	public Iterator<Offer> iterator() {
		return coll.iterator();
	}
}
