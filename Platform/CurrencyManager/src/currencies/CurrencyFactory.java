package currencies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.apache.commons.text.WordUtils;

import com.google.common.collect.BiMap;

import tickers.coinMarketCap.CoinMarketCap;
import wrappers.Wrapper;

/**
 * This is the proper way to obtain new Currency objects. By giving a
 * sudo-unique identifier, a currency will be created.
 * 
 * @author Stephen Buttolph
 */
public class CurrencyFactory {
	private static final Wrapper<BiMap<String, String>> NAME_TO_SYMBOL;

	static {
		NAME_TO_SYMBOL = CoinMarketCap.getNameToSymbolMappings(Constants.REFRESH_FREQUENCY);
	}

	/**
	 * Given a currency name [name], return the internal representation of that
	 * currency.
	 * 
	 * @param name
	 *            The name of the requested currency.
	 * @return The currency representation of the currency with the name [name].
	 */
	public static Currency parseName(String name) {
		name = WordUtils.capitalizeFully(name);
		String symbol = NAME_TO_SYMBOL.getValue().getOrDefault(name, null);
		if (symbol == null) {
			return null;
		}
		return new StandardCurrency(name, symbol);
	}

	/**
	 * Given a currency symbol [symbol], return the internal representation of that
	 * currency.
	 * 
	 * @param symbol
	 *            The symbol of the requested currency.
	 * @return The currency representation of the currency with the symbol [symbol].
	 */
	public static Currency parseSymbol(String symbol) {
		symbol = symbol.toUpperCase();
		String name = NAME_TO_SYMBOL.getValue().inverse().getOrDefault(symbol, null);
		if (name == null) {
			return null;
		}
		return new StandardCurrency(name, symbol);
	}

	/**
	 * @return the currency represented by this string.
	 */
	public static Currency parseCurrency(String currencyString) {
		if (currencyString == null
				|| currencyString.length() <= Constants.FORMAT_HEADER.length() + Constants.FORMAT_FOOTER.length()
						+ Constants.FORMAT_DELIMITER.length()
				|| !currencyString.contains(Constants.FORMAT_HEADER)
				|| !currencyString.contains(Constants.FORMAT_FOOTER)
				|| !currencyString.contains(Constants.FORMAT_DELIMITER)) {
			return null;
		}

		currencyString = currencyString.substring(Constants.FORMAT_HEADER.length(), currencyString.length() - 1);
		currencyString = currencyString.substring(0, currencyString.length() - (Constants.FORMAT_FOOTER.length() + 1));
		String[] parts = currencyString.split(Pattern.quote(Constants.FORMAT_DELIMITER));

		if (parts.length != 2) {
			return null;
		}

		return parseName(parts[0]);
	}

	public static Collection<Currency> getAllCurrencies() {
		Collection<Currency> currencies = new ArrayList<>();
		NAME_TO_SYMBOL.getValue().forEach((name, symbol) -> currencies.add(new StandardCurrency(name, symbol)));
		return currencies;
	}
}
