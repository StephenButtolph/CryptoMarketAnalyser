package offerGroups;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

import arithmetic.Pfloat;
import offers.Offer;
import utils.iterables.IterableUtils;

public class Offers implements Iterable<OfferPoint> {
	private SortedMap<Pfloat, OfferPoint> offers;

	public Offers(Offers... offers) {
		this();
		add(offers);
	}

	public Offers() {
		offers = new TreeMap<>();
	}

	public void add(Offers... offerGroups) {
		IterableUtils.flatten(IterableUtils.toIterable(offerGroups)).forEach(this::add);
	}

	public void add(OfferPoint... offerPoints) {
		for (OfferPoint newOfferPoint : offerPoints) {
			OfferPoint offerPoint = offers.get(newOfferPoint.getPrice());
			if (offerPoint == null) {
				offers.put(newOfferPoint.getPrice(), newOfferPoint);
			} else {
				newOfferPoint.forEach(offerPoint::add);
			}
		}
	}

	public void add(Offer... otherOffers) {
		for (Offer offer : otherOffers) {
			OfferPoint offerPoint = offers.get(offer.getPrice());
			if (offerPoint == null) {
				offerPoint = new OfferPoint(offer);
				offers.put(offerPoint.getPrice(), offerPoint);
			} else {
				offerPoint.add(offer);
			}
		}
	}

	@Override
	public Iterator<OfferPoint> iterator() {
		return offers.values().iterator();
	}
}
