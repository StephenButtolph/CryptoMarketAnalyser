package logging.loggers.currencyLoggers.marketLogger;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

import org.apache.commons.lang3.time.DurationFormatUtils;

import arithmetic.Pfloat;
import constants.Json;
import logging.debug.DebugLevel;
import logging.debug.DebugLogger;
import logging.loggers.currencyLoggers.CurrencyLogger;
import platforms.currencies.Currency;
import platforms.currencies.CurrencyFactory;
import platforms.tickers.coinMarketCap.CoinMarketCap;
import utils.collections.collections.CollectionUtils;
import utils.collections.maps.MapUtils;
import utils.files.FileUtils;
import utils.timing.TimingUtils;
import utils.types.TypeProducer;
import utils.types.TypeToken;

public class MarketLogger extends CurrencyLogger {
	private static final TemporalUnit UNIT;
	private static final long AMOUNT;

	private static final DateTimeFormatter formatter;

	static {
		UNIT = ChronoUnit.SECONDS;
		AMOUNT = 12 * 60 * 60;

		formatter = TimingUtils.SHORT;
	}

	private final Preferences prefs;
	private final CoinMarketCap coinMarketCap;

	private Map<Currency, MarketLogRow> lastUpdates;
	private String logPath;

	public MarketLogger(CoinMarketCap coinMarketCap) {
		super(Instant.now().truncatedTo(ChronoUnit.HALF_DAYS).plus(Duration.of(AMOUNT, UNIT)),
				Duration.of(AMOUNT, UNIT));

		prefs = Preferences.userNodeForPackage(MarketLogger.class);
		this.coinMarketCap = coinMarketCap;

		lastUpdates = loadLastUpdates();

		TypeProducer typeProducer = new TypeToken<List<String>>();
		String json = prefs.get(Constants.TRACKING_PATH, Json.EMPTY_ARRAY);
		List<String> tracking = Json.GSON.fromJson(json, typeProducer.getType());
		Collection<Currency> currencies = CollectionUtils.convert(tracking, CurrencyFactory::parseCurrency);
		super.setCurrencies(currencies);

		String timeFormat = "MarketLogger created:\n\tNext Collection = %s\n\tCollection Separation = %s (%s)";
		String sepFormat = "HH:mm:ss.S";

		String firstTime = TimingUtils.MEDIUM.format(getNextCollectionTime());
		String separation = DurationFormatUtils.formatDuration(getCollectionSeparation().toMillis(), sepFormat);
		DebugLogger.addLog(String.format(timeFormat, firstTime, separation, sepFormat), DebugLevel.INFO);
	}

	@Override
	public void setCurrencies(Collection<Currency> newCurrencies) {
		super.setCurrencies(newCurrencies);

		TypeProducer typeProducer = new TypeToken<List<String>>();
		Collection<String> toSave = CollectionUtils.convert(newCurrencies, Object::toString);
		String json = Json.GSON.toJson(toSave, typeProducer.getType());
		prefs.put(Constants.TRACKING_PATH, json);
	}

	public void setLogFile(String path) {
		prefs.put(Constants.LOG_PATH_PATH, path);
	}

	@Override
	protected void logCurrencies() {
		logPath = prefs.get(Constants.LOG_PATH_PATH, null);
		if (logPath != null) {
			logPath = logPath.trim();
		}
		DebugLogger.addLog("MarketLogger logging to: " + logPath, DebugLevel.INFO);

		super.logCurrencies();

		saveLastUpdates();
	}

	@Override
	protected void logCurrency(Currency toLog) {
		DebugLogger.addLog("MarketLogger logging: " + toLog, DebugLevel.INFO);

		Instant currentTime = this.getLastCollectionTime();
		MarketLogRow newLog = getCurrentLogRow(toLog);
		MarketLogRow previousLog = lastUpdates.get(toLog);

		Instant time;
		long timesToRecord;
		if (previousLog != null) {
			time = previousLog.getTimeStamp();

			Duration sinceLast = Duration.between(time, currentTime);
			long sinceLastUnit = sinceLast.get(UNIT);
			long unit = getCollectionSeparation().get(UNIT);
			timesToRecord = sinceLastUnit / unit;
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
		String json = prefs.get(Constants.LOG_UPDATES_PATH, Json.EMPTY_DICTIONARY);
		Map<String, String> lastUpdatesLoaded = Json.GSON.fromJson(json, typeProducer.getType());
		Map<Currency, MarketLogRow> lastUpdatesParsed = MapUtils.convertEntries(lastUpdatesLoaded,
				CurrencyFactory::parseCurrency, MarketLogRow::parse);
		if (lastUpdatesParsed == null) {
			lastUpdatesParsed = new HashMap<>();
		}
		return lastUpdatesParsed;
	}

	private void saveLastUpdates() {
		TypeProducer typeProducer = new TypeToken<Map<String, String>>();
		Map<String, String> lastUpdatesFormatted = MapUtils.convertEntries(lastUpdates, Object::toString,
				Object::toString);
		String json = Json.GSON.toJson(lastUpdatesFormatted, typeProducer.getType());
		prefs.put(Constants.LOG_UPDATES_PATH, json);
	}
}
