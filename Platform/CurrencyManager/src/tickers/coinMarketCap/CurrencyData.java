package tickers.coinMarketCap;

import arithmetic.Pfloat;
import currencies.Currency;
import currencies.CurrencyFactory;

class CurrencyData {
	private final Currency currency;
	private final Pfloat rank, marketCap, usdPrice, circulatingSupply, dayVolume, hourPercentChange, dayPercentChange,
			weekPercentChange;

	CurrencyData(String[] args) {
		if (args.length != Constants.NUM_ARGS) {
			throw new IllegalArgumentException("Incorrect number of arguments.");
		}

		rank = new Pfloat(args[Constants.RANK]);

		currency = CurrencyFactory.parseSymbol(args[Constants.SYMBOL]);

		marketCap = new Pfloat(args[Constants.MARKET_CAP]);
		usdPrice = new Pfloat(args[Constants.USD_PRICE]);
		circulatingSupply = new Pfloat(args[Constants.CIRCULATING_SUPPLY]);
		dayVolume = new Pfloat(args[Constants.DAY_VOLUME]);
		hourPercentChange = new Pfloat(args[Constants.HOUR_PERCENT_CHANGE]);
		dayPercentChange = new Pfloat(args[Constants.DAY_PERCENT_CHANGE]);
		weekPercentChange = new Pfloat(args[Constants.WEEK_PERCENT_CHANGE]);
	}

	Currency getCurrency() {
		return currency;
	}

	Pfloat getRank() {
		return rank;
	}

	Pfloat getMarketCap() {
		return marketCap;
	}

	Pfloat getUsdPrice() {
		return usdPrice;
	}

	Pfloat getCirculatingSupply() {
		return circulatingSupply;
	}

	Pfloat getDayVolume() {
		return dayVolume;
	}

	Pfloat getHourPercentChange() {
		return hourPercentChange;
	}

	Pfloat getDayPercentChange() {
		return dayPercentChange;
	}

	Pfloat getWeekPercentChange() {
		return weekPercentChange;
	}

	@Override
	public String toString() {
		String format = "#%s: %s; Cap = $%s, Price = $%s, Supply = %s, 24hVolume = %s, Hour Change = %s%%, Day Change = %s%%, Week Change = %s%%";
		return String.format(format, getRank(), getCurrency(), getMarketCap(), getUsdPrice(), getCirculatingSupply(),
				getDayVolume(), getHourPercentChange(), getDayPercentChange(), getWeekPercentChange());
	}
}
