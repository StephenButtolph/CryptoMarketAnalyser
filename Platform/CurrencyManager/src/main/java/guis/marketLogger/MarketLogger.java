package guis.marketLogger;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.Map;

import arithmetic.Pfloat;
import logging.currencyLogging.CurrencyLogger;
import platforms.currencies.Currency;
import platforms.currencies.CurrencyFactory;
import platforms.tickers.coinMarketCap.CoinMarketCap;
import utils.collections.maps.MapUtils;
import utils.files.FileUtils;
import utils.types.TypeProducer;
import utils.types.TypeToken;

public class MarketLogger extends CurrencyLogger {
	private static final TemporalUnit UNIT = ChronoUnit.HOURS;
	private static final long AMOUNT = 12;

	private Map<Currency, MarketLogRow> lastUpdates;
	private CoinMarketCap coinMarketCap;

	private DateTimeFormatter formatter;

	private String logPath;

	public MarketLogger(CoinMarketCap coinMarketCap) {
		super(Instant.now().truncatedTo(ChronoUnit.HALF_DAYS).plus(Duration.of(AMOUNT, UNIT)),
				Duration.of(AMOUNT, UNIT));

		this.coinMarketCap = coinMarketCap;
		formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withZone(ZoneId.systemDefault());
		lastUpdates = loadLastUpdates();
	}

	@Override
	protected void logCurrencies() {
		logPath = FileUtils.read(Constants.LOG_PATH_PATH);
		if (logPath != null) {
			logPath = logPath.trim();
		}

		super.logCurrencies();

		saveLastUpdates();
	}

	@Override
	protected void logCurrency(Currency toLog) {
		Instant currentTime = this.getLastCollectionTime();
		MarketLogRow newLog = getCurrentLogRow(toLog);
		MarketLogRow previousLog = lastUpdates.get(toLog);

		Instant time;
		long timesToRecord;
		if (previousLog != null) {
			time = previousLog.getTimeStamp();
			timesToRecord = Duration.between(time, currentTime).get(UNIT) / getCollectionSeparation().get(UNIT);
		} else {
			time = currentTime.minus(AMOUNT, UNIT);
			timesToRecord = 1;
			previousLog = newLog;
		}

		Pfloat timeAmount = new Pfloat(timesToRecord);

		Pfloat rankRatio = newLog.getRank().subtract(previousLog.getRank()).divide(timeAmount);
		Pfloat priceRatio = newLog.getPrice().subtract(previousLog.getPrice()).divide(timeAmount);
		Pfloat marketCapRatio = newLog.getMarketCap().subtract(previousLog.getMarketCap()).divide(timeAmount);
		Pfloat volumeRatio = newLog.getVolume().subtract(previousLog.getVolume()).divide(timeAmount);

		for (long missedTime = 1; missedTime <= timesToRecord; missedTime++) {
			Pfloat thisTime = new Pfloat(missedTime);

			Pfloat rank = rankRatio.multiply(thisTime).add(previousLog.getRank());
			Pfloat price = priceRatio.multiply(thisTime).add(previousLog.getPrice());
			Pfloat marketCap = marketCapRatio.multiply(thisTime).add(previousLog.getMarketCap());
			Pfloat volume = volumeRatio.multiply(thisTime).add(previousLog.getVolume());
			time = time.plus(AMOUNT, UNIT);

			MarketLogRow fakeLog = new MarketLogRow(toLog, rank, price, marketCap, volume, time, formatter);
			FileUtils.appendln(logPath, fakeLog.toCSVRow());
		}

		lastUpdates.put(toLog, newLog);
	}

	private MarketLogRow getCurrentLogRow(Currency toLog) {
		Pfloat rank = coinMarketCap.getRank(toLog);
		Pfloat price = coinMarketCap.getUsdPrice(toLog);
		Pfloat marketCap = coinMarketCap.getMarketCap(toLog);
		Pfloat volume = coinMarketCap.get24HVolume(toLog);
		Instant currentTime = this.getLastCollectionTime();

		return new MarketLogRow(toLog, rank, price, marketCap, volume, currentTime, formatter);
	}

	private Map<Currency, MarketLogRow> loadLastUpdates() {
		TypeProducer typeProducer = new TypeToken<Map<String, String>>();
		Map<String, String> lastUpdatesLoaded = FileUtils.load(Constants.LOG_UPDATES_PATH, typeProducer);

		Map<Currency, MarketLogRow> lastUpdatesParsed = MapUtils.convertEntries(lastUpdatesLoaded,
				CurrencyFactory::parseCurrency, MarketLogRow::parse);
		if (lastUpdatesParsed == null) {
			lastUpdatesParsed = new HashMap<>();
		}
		return lastUpdatesParsed;
	}

	private void saveLastUpdates() {
		Map<String, String> lastUpdatesFormatted = MapUtils.convertEntries(lastUpdates, Object::toString,
				Object::toString);

		TypeProducer typeProducer = new TypeToken<Map<String, String>>();
		FileUtils.save(Constants.LOG_UPDATES_PATH, lastUpdatesFormatted, typeProducer);
	}
}
