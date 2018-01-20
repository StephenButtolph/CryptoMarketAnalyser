package offerGroups;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

import arithmetic.Pfloat;
import offers.Offer;
import utils.collections.iterables.IterableUtils;

/**
 * A group of offers that all have the same price.
 * 
 * @author Stephen Buttolph
 */
public class OfferPoint implements Iterable<Offer> {
	private Collection<Offer> coll;
	private final Pfloat price;

	/**
	 * Create a new group of offers with the given offers.
	 * 
	 * @param offer
	 *            The first required offer.
	 * @param offers
	 *            An array of other initial offers.
	 * @throws IllegalArgumentException
	 *             The offers must all have the same price, otherwise an illegal
	 *             argument exception will be raised.
	 */
	public OfferPoint(Offer offer, Offer... offers) throws IllegalArgumentException {
		this.coll = new ArrayList<>();
		this.price = offer.getPrice();

		add(offer);
		add(offers);
	}

	/**
	 * @return The price of the offers contained in this offer group.
	 */
	public Pfloat getPrice() {
		return price;
	}

	/**
	 * @return The total amount of all the offers contained within this offer group.
	 */
	public Pfloat getAmount() {
		return IterableUtils.fold(amountIterable(), Pfloat::add, Pfloat.ZERO);
	}

	/**
	 * Add an array of offers to this offer group.
	 * 
	 * @param offers
	 *            The array of offers to add to the group.
	 * @throws IllegalArgumentException
	 *             The offers must all have the same price as this groups price,
	 *             otherwise an illegal argument exception will be raised.
	 */
	public void add(Offer... offers) throws IllegalArgumentException {
		IterableUtils.toIterable(offers).forEach(this::internalAdd);
	}

	private void internalAdd(Offer offer) throws IllegalArgumentException {
		if (!Objects.equals(this.getPrice(), offer.getPrice())) {
			throw new IllegalArgumentException("Offers must have the same price as the OfferPoint");
		}
		coll.add(offer);
	}

	/**
	 * @return An iterable containing the amount of each offer in this offer group.
	 */
	public Iterable<Pfloat> amountIterable() {
		return IterableUtils.map(coll, Offer::getAmount);
	}

	@Override
	public Iterator<Offer> iterator() {
		return coll.iterator();
	}
}
