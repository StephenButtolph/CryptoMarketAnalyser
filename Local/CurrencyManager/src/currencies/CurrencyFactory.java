package currencies;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import constants.Web;
import javafx.util.Pair;

public class CurrencyFactory {
	private static Map<String, String> nameToSymbolConverter;

	private static Map<String, Currency> symbolToCurrencyConverter;

	static {
		nameToSymbolConverter = new HashMap<>();
		refreshMappings();
	}

	private static Map<String, String> refreshMappings() {
		Map<String, String> nameToSymbol = new HashMap<>();

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
				nameToSymbol.put(entry.getKey(), entry.getValue());
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

		return new Pair<>(name, symbol);
	}

	private static String searchName(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements elems = doc.select(".text-large").first().children();

		return elems.attr("alt");
	}

	public static Currency parseName(String name) {
		return null; // TODO
	}

	public static Currency parseSymbol(String string) {
		return null; // TODO
	}
}
