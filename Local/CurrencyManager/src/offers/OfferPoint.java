package offers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;

import arithmetic.Pfloat;
import constants.Numeric;
import utils.AssertUtils;
import utils.IterableUtils;

public class OfferPoint implements Iterable<Offer> {
	private Collection<Pfloat> coll;
	private final Pfloat price;
	private Pfloat amount;

	private final Function<Pfloat, Offer> wrapAmount = amount -> {
		return new Offer(getPrice(), amount);
	};
	private final Function<Offer, Pfloat> unwrapAmount = offer -> {
		AssertUtils.assertEquals(this.getPrice(), offer.getPrice());
		return offer.getAmount();
	};

	public OfferPoint(Offer offer, Offer... offers) {
		this(offer.getPrice());

		add(offer);
		add(offers);
	}

	public OfferPoint(Pfloat price, Pfloat... amounts) {
		this.coll = new ArrayList<>();
		this.price = price;
		this.amount = new Pfloat(0);

		add(amounts);
	}

	public Pfloat getPrice() {
		return price;
	}

	public Pfloat getAmount() {
		return IterableUtils.fold(amountIterable(), Pfloat::add, new Pfloat(0));
	}

	public void add(Offer... offers) {
		Iterable<Offer> iter = IterableUtils.toIterable(offers);
		Iterable<Pfloat> amounts = IterableUtils.map(iter, unwrapAmount);
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
		return IterableUtils.map(coll, wrapAmount).iterator();
	}
}
