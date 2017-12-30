package tickers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BiFunction;

import arithmetic.Pfloat;
import currencies.Currency;
import utils.IterableUtils;

public class MultiTicker implements Ticker {
	private Collection<Ticker> tickers;

	public MultiTicker(Ticker... tickers) {
		this();
		add(tickers);
	}

	public MultiTicker(Iterable<Ticker> iter) {
		this();
		add(iter);
	}

	public MultiTicker() {
		tickers = new ArrayList<>();
	}

	public void add(Ticker... tickers) {
		add(IterableUtils.toIterable(tickers));
	}

	public void add(Iterable<Ticker> iter) {
		iter.forEach(tickers::add);
	}

	@Override
	public Pfloat getPrice(Currency currency, Currency commodity) {
		BiFunction<Pfloat, Ticker, Pfloat> f = (acc, ticker) -> {
			Pfloat sndPrice = ticker.getPrice(currency, commodity);
			Pfloat sndVolume = ticker.get24HVolume(commodity);

			// when one is defined, the other should be as well.
			if (!sndPrice.isDefined() || !sndVolume.isDefined()) {
				return acc;
			}

			Pfloat snd = sndPrice.multiply(sndVolume);
			if (!acc.isDefined()) {
				return snd;
			}

			return acc.add(snd);
		};
		Pfloat num = IterableUtils.fold(tickers, f, Pfloat.UNDEFINED);
		Pfloat den = get24HVolume(currency);
		return num.divide(den);
	}

	@Override
	public Pfloat get24HVolume(Currency currency) {
		BiFunction<Pfloat, Ticker, Pfloat> f = (acc, ticker) -> {
			Pfloat snd = ticker.get24HVolume(currency);

			if (!snd.isDefined()) {
				return acc;
			}
			if (!acc.isDefined()) {
				return snd;
			}

			return acc.add(snd);
		};
		return IterableUtils.fold(tickers, f, Pfloat.UNDEFINED);
	}
}
