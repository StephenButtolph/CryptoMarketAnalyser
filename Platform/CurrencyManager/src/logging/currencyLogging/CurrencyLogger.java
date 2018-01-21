package logging.currencyLogging;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import logging.loggers.FunctionalLogger;
import platforms.currencies.Currency;

public abstract class CurrencyLogger extends FunctionalLogger {
	private Collection<Currency> currencies;
	private Lock lock;

	protected CurrencyLogger(Instant firstTime, Duration separation) {
		this(null, firstTime, separation);
	}

	protected CurrencyLogger(Collection<Currency> currencies, Instant firstTime, Duration separation) {
		super(null, firstTime, separation);
		super.setFunction(this::logCurrencies);

		lock = new ReentrantLock();
		setCurrencies(currencies);
	}

	public void setCurrencies(Collection<Currency> newCurrencies) {
		lock.lock();
		if (newCurrencies == null) {
			currencies = new ArrayList<>();
		} else {
			currencies = new ArrayList<>(newCurrencies);
		}
		lock.unlock();
	}

	protected void logCurrencies() {
		lock.lock();
		Collection<Currency> localCurrencies = new ArrayList<>(currencies);
		lock.unlock();

		localCurrencies.forEach(this::logCurrency);
	}

	protected abstract void logCurrency(Currency toLog);
}
