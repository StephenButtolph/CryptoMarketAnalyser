package offers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;

import utils.AssertUtils;
import utils.IterableUtils;

public class OfferPoint implements Iterable<Offer> {
	private Collection<BigDecimal> coll;
	private final BigDecimal price;
	
	private final Function<BigDecimal, Offer> wrapAmount = amount -> {
		return new Offer(getPrice(), amount);
	};
	private final Function<Offer, BigDecimal> unwrapAmount = offer -> {
		AssertUtils.assertEquals(this.getPrice(), offer.getPrice());
		return offer.getAmount();
	};
	private final BiFunction<BigDecimal, BigDecimal, BigDecimal> plus = (x, y) -> {
		return x.add(y);
	};

	public OfferPoint(Offer offer, Offer... offers) {
		this(offer.getPrice());

		add(offer);
		add(offers);
	}

	public OfferPoint(BigDecimal price, BigDecimal... amounts) {
		this.coll = new ArrayList<>();
		this.price = price;

		add(amounts);
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getAmount() {
		return IterableUtils.fold(this.amountIterable(), plus, BigDecimal.ZERO);
	}

	public void add(Offer... offers) {
		Iterable<Offer> iter = IterableUtils.toIterable(offers);
		Iterable<BigDecimal> amounts = IterableUtils.map(iter, unwrapAmount);
		add(amounts);
	}

	public void add(BigDecimal... amounts) {
		add(IterableUtils.toIterable(amounts));
	}

	private void add(Iterable<BigDecimal> amounts) {
		amounts.forEach(coll::add);
	}

	public Iterable<BigDecimal> amountIterable() {
		return coll;
	}

	@Override
	public Iterator<Offer> iterator() {
		return IterableUtils.map(coll, wrapAmount).iterator();
	}
}
