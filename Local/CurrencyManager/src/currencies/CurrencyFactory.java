package currencies;

import java.io.IOException;

import org.apache.commons.text.WordUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import caches.TemporaryValue;
import constants.Timing;
import constants.Web;
import javafx.util.Pair;

public class CurrencyFactory {
	private static final TemporaryValue<BiMap<String, String>> NAME_TO_SYMBOL;

	static {
		NAME_TO_SYMBOL = new TemporaryValue<>(CurrencyFactory::refreshMappings, Timing.CurrencyNameMappingHoldDuration);
	}

	private static BiMap<String, String> refreshMappings() {
		BiMap<String, String> nameToSymbol = HashBiMap.create();

		Document doc;
		try {
			doc = Jsoup.connect(Web.COIN_MARKET_CAP_ALL_COINS_URL).maxBodySize(0).get();
		} catch (IOException e) {
			return nameToSymbol;
		}

		Element table = doc.select("table").first(); // select the table
		Elements rows = table.select("tr"); // select the rows

		for (int i = 1; i < rows.size(); i++) { // first row is the col names so skip it
			Element elem = rows.get(i);
			try {
				Pair<String, String> entry = parseRow(elem);

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

	private static Pair<String, String> parseRow(Element row) throws IOException {
		Elements cols = row.children();

		String[] args = cols.get(1).text().split(" ", 2);
		String symbol = args[0];
		String name = args[1];

		if (name.endsWith("...")) {
			String url = Web.COIN_MARKET_CAP_URL + cols.get(1).select("a").attr("href");
			name = searchName(url);
		}

		return new Pair<>(WordUtils.capitalizeFully(name), symbol.toUpperCase());
	}

	private static String searchName(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements elems = doc.select(".text-large").first().children();

		return elems.attr("alt");
	}

	public static Currency parseName(String name) {
		name = WordUtils.capitalizeFully(name);
		String symbol = NAME_TO_SYMBOL.getValue().getOrDefault(name, null);
		if (symbol == null) {
			return null;
		}
		return new Currency(name, symbol);
	}

	public static Currency parseSymbol(String symbol) {
		symbol = symbol.toUpperCase();
		String name = NAME_TO_SYMBOL.getValue().inverse().getOrDefault(symbol, null);
		if (name == null) {
			return null;
		}
		return new Currency(name, symbol);
	}
}
