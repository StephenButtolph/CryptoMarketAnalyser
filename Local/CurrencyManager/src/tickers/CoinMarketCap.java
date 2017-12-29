package tickers;

import java.io.IOException;
import java.time.temporal.TemporalAmount;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import arithmetic.Pfloat;
import constants.Web;
import currencies.Currency;
import currencies.CurrencyFactory;
import wrappers.TemporaryValue;

public class CoinMarketCap implements Ticker {
	private static final String CLEAR_REGEX = "[$,%* ]";
	private static final String EMPTY = "";
	private static final String LOW_VOLUME_REGEX = "LowVol";
	private static final String ZERO = "0";
	private static final int NUM_ARGS = 9;

	private TemporaryValue<Map<Currency, CurrencyData>> cachedData;

	public CoinMarketCap(TemporalAmount refreshRate) {
		cachedData = new TemporaryValue<>(this::getData, refreshRate);
	}

	private Map<Currency, CurrencyData> getData() {
		Map<Currency, CurrencyData> mapping = new HashMap<>();
		Document doc;
		try {
			doc = Jsoup.connect(Web.COIN_MARKET_CAP_ALL_COINS_URL).maxBodySize(0).get();
		} catch (IOException e) {
			return mapping;
		}

		Element table = doc.select("table").first(); // select the table
		Elements rows = table.select("tr"); // select the rows

		for (int i = 1; i < rows.size(); i++) { // first row is the col names so skip it
			Element elem = rows.get(i);
			CurrencyData row = parseRow(elem);

			if (row != null) {
				mapping.putIfAbsent(row.getCurrency(), row);
			}
		}

		return mapping;
	}

	private CurrencyData parseRow(Element row) {
		try {
			Elements cols = row.children();

			String[] args = new String[NUM_ARGS];

			args[CurrencyData.RANK] = cols.get(0).text();

			for (int arg = CurrencyData.SYMBOL; arg < NUM_ARGS; arg++) {
				String text = cols.get(arg + 1).text();
				String cleaned = text.replaceAll(CLEAR_REGEX, EMPTY);
				String fixed = cleaned.replaceAll(LOW_VOLUME_REGEX, ZERO);
				args[arg] = fixed.trim();
			}

			return new CurrencyData(args);
		} catch (IndexOutOfBoundsException | NumberFormatException e) {
			return null;
		}
	}

	@Override
	public Pfloat getPrice(Currency currency, Currency comodity) {
		Map<Currency, CurrencyData> dataMapping = cachedData.getValue();

		CurrencyData currencyData = dataMapping.get(currency);
		CurrencyData comodityData = dataMapping.get(comodity);

		if (currencyData == null || comodityData == null) {
			return null;
		}

		Pfloat currencyPrice = currencyData.getUsdPrice();
		Pfloat comodityPrice = comodityData.getUsdPrice();

		return comodityPrice.divide(currencyPrice);
	}

	@Override
	public Pfloat get24HVolume(Currency currency) {
		return getValue(CurrencyData::getDayVolume, currency);
	}

	public Pfloat getRank(Currency currency) {
		return getValue(CurrencyData::getRank, currency);
	}

	public Pfloat getMarketCap(Currency currency) {
		return getValue(CurrencyData::getMarketCap, currency);
	}

	public Pfloat getUsdPrice(Currency currency) {
		return getValue(CurrencyData::getUsdPrice, currency);
	}

	public Pfloat getCirculatingSupply(Currency currency) {
		return getValue(CurrencyData::getCirculatingSupply, currency);
	}

	public Pfloat getDayVolume(Currency currency) {
		return getValue(CurrencyData::getDayVolume, currency);
	}

	public Pfloat getHourPercentChange(Currency currency) {
		return getValue(CurrencyData::getHourPercentChange, currency);
	}

	public Pfloat getDayPercentChange(Currency currency) {
		return getValue(CurrencyData::getDayPercentChange, currency);
	}

	public Pfloat getWeekPercentChange(Currency currency) {
		return getValue(CurrencyData::getWeekPercentChange, currency);
	}

	private Pfloat getValue(Function<CurrencyData, Pfloat> f, Currency currency) {
		Map<Currency, CurrencyData> dataMapping = cachedData.getValue();
		CurrencyData data = dataMapping.get(currency);

		if (data == null) {
			return Pfloat.UNDEFINED;
		}
		return f.apply(data);
	}

	private static class CurrencyData {
		public static final int RANK = 0;
		public static final int SYMBOL = 1;
		public static final int MARKET_CAP = 2;
		public static final int USD_PRICE = 3;
		public static final int CIRCULATING_SUPPLY = 4;
		public static final int DAY_VOLUME = 5;
		public static final int HOUR_PERCENT_CHANGE = 6;
		public static final int DAY_PERCENT_CHANGE = 7;
		public static final int WEEK_PERCENT_CHANGE = 8;

		private final Currency currency;
		private final Pfloat rank, marketCap, usdPrice, circulatingSupply, dayVolume, hourPercentChange,
				dayPercentChange, weekPercentChange;

		public CurrencyData(String[] args) {
			if (args.length != NUM_ARGS) {
				throw new IllegalArgumentException("Incorrect number of arguments.");
			}

			rank = new Pfloat(args[RANK]);

			currency = CurrencyFactory.parseSymbol(args[SYMBOL]);

			marketCap = new Pfloat(args[MARKET_CAP]);
			usdPrice = new Pfloat(args[USD_PRICE]);
			circulatingSupply = new Pfloat(args[CIRCULATING_SUPPLY]);
			dayVolume = new Pfloat(args[DAY_VOLUME]);
			hourPercentChange = new Pfloat(args[HOUR_PERCENT_CHANGE]);
			dayPercentChange = new Pfloat(args[DAY_PERCENT_CHANGE]);
			weekPercentChange = new Pfloat(args[WEEK_PERCENT_CHANGE]);
		}

		public Currency getCurrency() {
			return currency;
		}

		public Pfloat getRank() {
			return rank;
		}

		public Pfloat getMarketCap() {
			return marketCap;
		}

		public Pfloat getUsdPrice() {
			return usdPrice;
		}

		public Pfloat getCirculatingSupply() {
			return circulatingSupply;
		}

		public Pfloat getDayVolume() {
			return dayVolume;
		}

		public Pfloat getHourPercentChange() {
			return hourPercentChange;
		}

		public Pfloat getDayPercentChange() {
			return dayPercentChange;
		}

		public Pfloat getWeekPercentChange() {
			return weekPercentChange;
		}

		@Override
		public String toString() {
			String format = "#%s: %s; Cap = $%s, Price = $%s, Supply = %s, 24hVolume = %s, Hour Change = %s%%, Day Change = %s%%, Week Change = %s%%";
			return String.format(format, getRank(), getCurrency(), getMarketCap(), getUsdPrice(),
					getCirculatingSupply(), getDayVolume(), getHourPercentChange(), getDayPercentChange(),
					getWeekPercentChange());
		}
	}
}
