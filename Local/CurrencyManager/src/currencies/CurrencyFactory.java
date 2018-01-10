package currencies;

import org.apache.commons.text.WordUtils;

import com.google.common.collect.BiMap;

import constants.Timing;
import tickers.coinMarketCap.CoinMarketCap;
import wrappers.Wrapper;

public class CurrencyFactory {
	private static final Wrapper<BiMap<String, String>> NAME_TO_SYMBOL;

	static {
		NAME_TO_SYMBOL = CoinMarketCap.getNameToSymbolMappings(Timing.CurrencyNameMappingHoldDuration);
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
