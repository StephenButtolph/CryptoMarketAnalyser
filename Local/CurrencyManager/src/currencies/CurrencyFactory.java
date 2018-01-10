package currencies;

import org.apache.commons.text.WordUtils;

import com.google.common.collect.BiMap;

import constants.Timing;
import tickers.coinMarketCap.CoinMarketCap;
import wrappers.Wrapper;

/**
 * @author Stephen Buttolph
 * 
 *         This is the proper way to obtain new Currency objects. By giving a
 *         sudo-unique identifier, a currency will be created.
 */
public class CurrencyFactory {
	private static final Wrapper<BiMap<String, String>> NAME_TO_SYMBOL;

	static {
		NAME_TO_SYMBOL = CoinMarketCap.getNameToSymbolMappings(Timing.DEFAULT_CURRENCY_MAPPING_REFRESH_FREQUENCY);
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
}
