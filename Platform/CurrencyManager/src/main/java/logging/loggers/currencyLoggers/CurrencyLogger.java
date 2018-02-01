package logging.loggers.currencyLoggers;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
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
		this.currencies = copy(currencies);
	}

	public Collection<Currency> getCurrencies() {
		lock.lock();

		try {
			return copy(currencies);
		} finally {
			lock.unlock();
		}
	}

	public void setCurrencies(Collection<Currency> newCurrencies) {
		lock.lock();

		try {
			currencies = copy(newCurrencies);
		} finally {
			lock.unlock();
		}
	}

	protected void logCurrencies() {
		lock.lock();
		Collection<Currency> localCurrencies = new HashSet<>(currencies);
		lock.unlock();

		localCurrencies.forEach(this::logCurrency);
	}

	protected abstract void logCurrency(Currency toLog);

	private <T> Collection<T> copy(Collection<T> collection) {
		if (collection == null) {
			return new HashSet<>();
		} else {
			return new HashSet<>(collection);
		}
	}
}
