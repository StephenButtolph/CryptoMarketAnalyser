package tickers;

import java.io.IOException;
import java.time.temporal.TemporalAmount;
import java.util.HashMap;
import java.util.Map;

import org.apfloat.Apfloat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import caches.TemporaryValue;
import constants.Numeric;
import constants.Web;
import currencies.Currency;
import currencies.CurrencyFactory;

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
	public Apfloat getPrice(Currency currency, Currency comodity) {
		Map<Currency, CurrencyData> dataMapping = cachedData.getValue();

		CurrencyData currencyData = dataMapping.get(currency);
		CurrencyData comodityData = dataMapping.get(comodity);
		
		if (currencyData == null || comodityData == null) {
			return null;
		}

		Apfloat currencyPrice = currencyData.getUsdPrice();
		Apfloat comodityPrice = comodityData.getUsdPrice();
		
		
		return comodityPrice.divide(currencyPrice);
	}

	@Override
	public Apfloat get24HVolume(Currency currency) {
		Map<Currency, CurrencyData> dataMapping = cachedData.getValue();
		CurrencyData data = dataMapping.get(currency);
		
		if (data == null) {
			return null;
		}
		return data.getDayVolume();
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

		private final int rank;
		private final Currency currency;
		private final Apfloat marketCap, usdPrice, circulatingSupply, dayVolume, hourPercentChange, dayPercentChange,
				weekPercentChange;

		public CurrencyData(String[] args) {
			if (args.length != NUM_ARGS) {
				throw new IllegalArgumentException("Incorrect number of arguments.");
			}

			rank = Integer.parseInt(args[RANK]);
			
			currency = CurrencyFactory.parseSymbol(args[SYMBOL]);

			marketCap = new Apfloat(args[MARKET_CAP], Numeric.APFLOAT_PRECISION);
			usdPrice = new Apfloat(args[USD_PRICE], Numeric.APFLOAT_PRECISION);
			circulatingSupply = new Apfloat(args[CIRCULATING_SUPPLY], Numeric.APFLOAT_PRECISION);
			dayVolume = new Apfloat(args[DAY_VOLUME], Numeric.APFLOAT_PRECISION);
			hourPercentChange = new Apfloat(args[HOUR_PERCENT_CHANGE], Numeric.APFLOAT_PRECISION);
			dayPercentChange = new Apfloat(args[DAY_PERCENT_CHANGE], Numeric.APFLOAT_PRECISION);
			weekPercentChange = new Apfloat(args[WEEK_PERCENT_CHANGE], Numeric.APFLOAT_PRECISION);
		}

		public int getRank() {
			return rank;
		}

		public Currency getCurrency() {
			return currency;
		}

		public Apfloat getMarketCap() {
			return marketCap;
		}

		public Apfloat getUsdPrice() {
			return usdPrice;
		}

		public Apfloat getCirculatingSupply() {
			return circulatingSupply;
		}

		public Apfloat getDayVolume() {
			return dayVolume;
		}

		public Apfloat getHourPercentChange() {
			return hourPercentChange;
		}

		public Apfloat getDayPercentChange() {
			return dayPercentChange;
		}

		public Apfloat getWeekPercentChange() {
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
