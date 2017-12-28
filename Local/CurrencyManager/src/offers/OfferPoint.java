package offers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;

import org.apfloat.Apfloat;

import constants.Numeric;
import utils.AssertUtils;
import utils.IterableUtils;

public class OfferPoint implements Iterable<Offer> {
	private Collection<Apfloat> coll;
	private final Apfloat price;
	private Apfloat amount;
	
	private final Function<Apfloat, Offer> wrapAmount = amount -> {
		return new Offer(getPrice(), amount);
	};
	private final Function<Offer, Apfloat> unwrapAmount = offer -> {
		AssertUtils.assertEquals(this.getPrice(), offer.getPrice());
		return offer.getAmount();
	};

	public OfferPoint(Offer offer, Offer... offers) {
		this(offer.getPrice());

		add(offer);
		add(offers);
	}

	public OfferPoint(Apfloat price, Apfloat... amounts) {
		this.coll = new ArrayList<>();
		this.price = price;
		this.amount = new Apfloat(0, Numeric.APFLOAT_PRECISION);

		add(amounts);
	}

	public Apfloat getPrice() {
		return price;
	}

	public Apfloat getAmount() {
		return IterableUtils.fold(amountIterable(), Apfloat::add, new Apfloat(0, Numeric.APFLOAT_PRECISION));
	}

	public void add(Offer... offers) {
		Iterable<Offer> iter = IterableUtils.toIterable(offers);
		Iterable<Apfloat> amounts = IterableUtils.map(iter, unwrapAmount);
		internalAdd(amounts);
	}

	public void add(Apfloat... amounts) {
		internalAdd(IterableUtils.toIterable(amounts));
	}

	private void internalAdd(Iterable<Apfloat> amounts) {
		amounts.forEach(this::internalAdd);
	}
	
	private void internalAdd(Apfloat amount) {
		coll.add(amount);
		this.amount = this.amount.add(amount);
	}

	public Iterable<Apfloat> amountIterable() {
		return coll;
	}

	@Override
	public Iterator<Offer> iterator() {
		return IterableUtils.map(coll, wrapAmount).iterator();
	}
}
