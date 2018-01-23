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

//		super(Instant.now(), Duration.ofSeconds(10));

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
		// Instant previousTime = lastUpdates.get(toLog);
		// Instant currentTime = this.getLastCollectionTime();

		// long timesToRecord = 1;
		// if (currentTime != null) {
		// timesToRecord = Duration.between(previousTime, currentTime).get(UNIT) /
		// getCollectionSeparation().get(UNIT);
		// }

		// TODO put in fake average data for missed times ( hard because we need to load
		// the previously stored values)
		// for (long missedTime = 1; missedTime <= timesToRecord; missedTime++) {

		// filler value = (missedTime / timesToRecord) * (new - old)

		MarketLogRow newLog = getCurrentLogRow(toLog);
		FileUtils.appendln(logPath, newLog.toCSVRow());

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
