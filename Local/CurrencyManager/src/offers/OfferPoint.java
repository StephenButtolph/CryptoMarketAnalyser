package offers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

import arithmetic.Pfloat;
import utils.IterableUtils;

public class OfferPoint implements Iterable<Offer> {
	private Collection<Pfloat> coll;
	private final Pfloat price;
	private Pfloat amount;

	public OfferPoint(Offer offer, Offer... offers) {
		this(offer.getPrice());

		add(offer);
		add(offers);
	}

	public OfferPoint(Pfloat price, Pfloat... amounts) {
		this.coll = new ArrayList<>();
		this.price = price;
		this.amount = Pfloat.ZERO;

		add(amounts);
	}

	public Pfloat getPrice() {
		return price;
	}

	public Pfloat getAmount() {
		return IterableUtils.fold(amountIterable(), Pfloat::add, Pfloat.ZERO);
	}

	public void add(Offer... offers) {
		Iterable<Offer> iter = IterableUtils.toIterable(offers);
		Iterable<Pfloat> amounts = IterableUtils.map(iter, this::unwrapAmount);
		internalAdd(amounts);
	}

	public void add(Pfloat... amounts) {
		internalAdd(IterableUtils.toIterable(amounts));
	}

	private void internalAdd(Iterable<Pfloat> amounts) {
		amounts.forEach(this::internalAdd);
	}

	private void internalAdd(Pfloat amount) {
		coll.add(amount);
		this.amount = this.amount.add(amount);
	}

	public Iterable<Pfloat> amountIterable() {
		return coll;
	}

	@Override
	public Iterator<Offer> iterator() {
		return IterableUtils.map(coll, this::wrapAmount).iterator();
	}

	private Offer wrapAmount(Pfloat amount) {
		return new Offer(getPrice(), amount);
	}

	private Pfloat unwrapAmount(Offer offer) {
		if (!Objects.equals(this.getPrice(), offer.getPrice())) {
			throw new IllegalArgumentException("Offers must have the same price as the OfferPoint");
		}
		return offer.getAmount();
	}
}
