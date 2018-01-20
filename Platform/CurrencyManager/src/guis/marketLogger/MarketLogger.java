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
import currencies.Currency;
import currencies.CurrencyFactory;
import logging.currencyLogging.CurrencyLogger;
import tickers.coinMarketCap.CoinMarketCap;
import types.TypeProducer;
import types.TypeToken;
import utils.files.CSVFileUtils;
import utils.files.FileUtils;
import utils.maps.MapUtils;

public class MarketLogger extends CurrencyLogger {
	private static final TemporalUnit UNIT = ChronoUnit.HOURS;
	private Map<Currency, Instant> lastUpdates;
	private CoinMarketCap coinMarketCap;

	private DateTimeFormatter formatter;

	public MarketLogger(CoinMarketCap coinMarketCap) {
		super(Instant.now().truncatedTo(ChronoUnit.HALF_DAYS).plus(Duration.of(12, UNIT)), Duration.of(12, UNIT));

		this.coinMarketCap = coinMarketCap;
		formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withZone(ZoneId.systemDefault());
		lastUpdates = loadLastUpdates();
	}

	@Override
	protected void logCurrencies() {
		super.logCurrencies();

		saveLastUpdates();
	}

	@Override
	protected void logCurrency(Currency toLog) {
		Instant previousTime = lastUpdates.get(toLog);
		Instant currentTime = this.getLastCollectionTime();

		long timesToRecord = 1;
		if (currentTime != null) {
			timesToRecord = Duration.between(previousTime, currentTime).get(UNIT) / getCollectionSeparation().get(UNIT);
		}
		System.out.println(timesToRecord);

		// TODO put in fake average data for missed times ( hard because we need to load
		// the previously stored values)
		// for (long missedTime = 1; missedTime <= timesToRecord; missedTime++) {
		Pfloat rank = coinMarketCap.getRank(toLog);
		Pfloat price = coinMarketCap.getUsdPrice(toLog);
		Pfloat marketCap = coinMarketCap.getMarketCap(toLog);
		Pfloat volume = coinMarketCap.get24HVolume(toLog);
		String timeStamp = formatter.format(currentTime);

		String newLog = CSVFileUtils.toLine(toLog, rank, price, marketCap, volume, timeStamp);
		FileUtils.appendln(Constants.LOG_PATH, newLog);
		// }

		lastUpdates.put(toLog, currentTime);
	}

	private Map<Currency, Instant> loadLastUpdates() {
		TypeProducer typeProducer = new TypeToken<Map<String, String>>();
		Map<String, String> lastUpdatesLoaded = FileUtils.load(Constants.LOG_UPDATES_PATH, typeProducer);

		Map<Currency, Instant> lastUpdatesParsed = MapUtils.convertEntries(lastUpdatesLoaded,
				CurrencyFactory::parseCurrency, Instant::parse);
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
