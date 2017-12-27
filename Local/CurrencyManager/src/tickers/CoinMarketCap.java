package tickers;

import java.io.IOException;
import java.math.BigDecimal;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import currencies.Currency;

public class CoinMarketCap implements Ticker {
	private static final String URL = "https://coinmarketcap.com/all/views/all/";
	private static final String CLEAR_REGEX = "[$,%* ]";
	private static final String EMPTY = "";
	private static final String LOW_VOLUME_REGEX = "LowVol";
	private static final String ZERO = "0";
	private static final int NUM_ARGS = 10;

	private void getRows() throws IOException {
		Document doc = Jsoup.connect(URL).maxBodySize(0).get();

		Element table = doc.select("table").first(); // select the table
		Elements rows = table.select("tr"); // select the rows

		for (int i = 1; i < rows.size(); i++) { // first row is the col names so skip it
			Element elem = rows.get(i);
			Row row = parseRow(elem);

			if (row != null) {
				System.out.println(row);
			}
		}
	}

	private Row parseRow(Element row) {
		try {
			Elements cols = row.children();

			String[] args = new String[NUM_ARGS];

			args[Row.RANK] = cols.get(0).text();

			String[] symbolAndName = cols.get(1).text().split(" ");
			args[Row.NAME] = symbolAndName[1];
			args[Row.SYMBOL] = symbolAndName[0];

			for (int arg = Row.MARKET_CAP; arg < NUM_ARGS; arg++) {
				String text = cols.get(arg).text();
				String cleaned = text.replaceAll(CLEAR_REGEX, EMPTY);
				String fixed = cleaned.replaceAll(LOW_VOLUME_REGEX, ZERO);
				args[arg] = fixed.trim();
			}

			return new Row(args);
		} catch (IndexOutOfBoundsException | NumberFormatException e) {
			return null;
		}
	}

	@Override
	public BigDecimal getPrice(Currency currency, Currency comodity) {

		throw new RuntimeException("Unimplemented.");
	}

	@Override
	public BigDecimal get24HVolume(Currency currency) {
		throw new RuntimeException("Unimplemented.");
	}

	private static class Row {
		public static final int RANK = 0;
		public static final int NAME = 1;
		public static final int SYMBOL = 2;
		public static final int MARKET_CAP = 3;
		public static final int USD_PRICE = 4;
		public static final int CIRCULATING_SUPPLY = 5;
		public static final int DAY_VOLUME = 6;
		public static final int HOUR_PERCENT_CHANGE = 7;
		public static final int DAY_PERCENT_CHANGE = 8;
		public static final int WEEK_PERCENT_CHANGE = 9;

		private final int rank;
		private final String name, symbol;
		private final BigDecimal marketCap, usdPrice, circulatingSupply, dayVolume, hourPercentChange, dayPercentChange,
				weekPercentChange;

		public Row(String[] args) {
			if (args.length != NUM_ARGS) {
				throw new IllegalArgumentException("Incorrect number of arguments.");
			}

			rank = Integer.parseInt(args[RANK]);

			name = args[NAME];
			symbol = args[SYMBOL];

			marketCap = new BigDecimal(args[MARKET_CAP]);
			usdPrice = new BigDecimal(args[USD_PRICE]);
			circulatingSupply = new BigDecimal(args[CIRCULATING_SUPPLY]);
			dayVolume = new BigDecimal(args[DAY_VOLUME]);
			hourPercentChange = new BigDecimal(args[HOUR_PERCENT_CHANGE]);
			dayPercentChange = new BigDecimal(args[DAY_PERCENT_CHANGE]);
			weekPercentChange = new BigDecimal(args[WEEK_PERCENT_CHANGE]);
		}

		public int getRank() {
			return rank;
		}

		public String getName() {
			return name;
		}

		public String getSymbol() {
			return symbol;
		}

		public BigDecimal getMarketCap() {
			return marketCap;
		}

		public BigDecimal getUsdPrice() {
			return usdPrice;
		}

		public BigDecimal getCirculatingSupply() {
			return circulatingSupply;
		}

		public BigDecimal getDayVolume() {
			return dayVolume;
		}

		public BigDecimal getHourPercentChange() {
			return hourPercentChange;
		}

		public BigDecimal getDayPercentChange() {
			return dayPercentChange;
		}

		public BigDecimal getWeekPercentChange() {
			return weekPercentChange;
		}

		@Override
		public String toString() {
			String format = "#%s: %s(%s); Cap = $%s, Price = $%s, Supply = %s, 24hVolume = %s, Hour Change = %s%%, Day Change = %s%%, Week Change = %s%%";
			return String.format(format, getRank(), getName(), getSymbol(), getMarketCap(), getUsdPrice(),
					getCirculatingSupply(), getDayVolume(), getHourPercentChange(), getDayPercentChange(),
					getWeekPercentChange());
		}
	}

	public static void main(String[] args) throws IOException {
		new CoinMarketCap().getRows();
	}
}
