package tickers.coinMarketCap;

import java.io.IOException;
import java.time.temporal.TemporalAmount;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.text.WordUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import arithmetic.Pfloat;
import currencies.Currency;
import currencies.CurrencyMarket;
import javafx.util.Pair;
import tickers.Ticker;
import wrappers.RefreshingValue;
import wrappers.Wrapper;

public class CoinMarketCap implements Ticker {
	private Wrapper<Map<Currency, CurrencyData>> cachedData;

	public CoinMarketCap(TemporalAmount refreshRate) {
		cachedData = new RefreshingValue<>(this::getData, refreshRate);
	}

	private Map<Currency, CurrencyData> getData() {
		Map<Currency, CurrencyData> mapping = new HashMap<>();
		Document doc;
		try {
			doc = Jsoup.connect(Constants.ALL_COINS_URL).maxBodySize(0).get();
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

			String[] args = new String[Constants.NUM_ARGS];

			args[Constants.RANK] = cols.get(0).text();

			for (int arg = Constants.SYMBOL; arg < Constants.NUM_ARGS; arg++) {
				String text = cols.get(arg + 1).text();
				String cleaned = text.replaceAll(Constants.CLEAR_REGEX, Constants.EMPTY);
				String fixed = cleaned.replaceAll(Constants.LOW_VOLUME_REGEX, Constants.ZERO);
				args[arg] = fixed.trim();
			}

			return new CurrencyData(args);
		} catch (IndexOutOfBoundsException | NumberFormatException e) {
			return null;
		}
	}

	@Override
	public Pfloat getPrice(CurrencyMarket market) {
		Map<Currency, CurrencyData> dataMapping = cachedData.getValue();

		CurrencyData currencyData = dataMapping.get(market.getCurrency());
		CurrencyData comodityData = dataMapping.get(market.getCommodity());

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

	public static RefreshingValue<BiMap<String, String>> getNameToSymbolMappings(TemporalAmount refreshRate) {
		return new RefreshingValue<>(CoinMarketCap::refreshMappings, refreshRate);

	}

	private static BiMap<String, String> refreshMappings() {
		BiMap<String, String> nameToSymbol = HashBiMap.create();

		Document doc;
		try {
			doc = Jsoup.connect(Constants.ALL_COINS_URL).maxBodySize(0).get();
		} catch (IOException e) {
			return nameToSymbol;
		}

		Element table = doc.select("table").first(); // select the table
		Elements rows = table.select("tr"); // select the rows

		for (int i = 1; i < rows.size(); i++) { // first row is the col names so skip it
			Element elem = rows.get(i);
			try {
				Pair<String, String> entry = parseRowToMapping(elem);

				// put if absent because some symbols are used multiple times.

				// TODO will this cause possible errors when attempting to send
				// money to incorrect wallets?
				if (!nameToSymbol.containsValue(entry.getValue())) {
					nameToSymbol.putIfAbsent(entry.getKey(), entry.getValue());
				}
			} catch (IOException e) {
			}
		}
		return nameToSymbol;
	}

	private static Pair<String, String> parseRowToMapping(Element row) throws IOException {
		Elements cols = row.children();

		String[] args = cols.get(1).text().split(" ", 2);
		String symbol = args[0];
		String name = args[1];

		if (name.endsWith("...")) {
			String url = Constants.BASE_URL + cols.get(1).select("a").attr("href");
			name = searchName(url);
		}

		return new Pair<>(WordUtils.capitalizeFully(name), symbol.toUpperCase());
	}

	private static String searchName(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements elems = doc.select(".text-large").first().children();

		return elems.attr("alt");
	}
}
