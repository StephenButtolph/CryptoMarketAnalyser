package applications.components.tables.currencyTables;

import java.time.Duration;

import arithmetic.Pfloat;
import platforms.currencies.Currency;
import platforms.tickers.coinMarketCap.CoinMarketCap;

public class StandardCurrencyTable extends CurrencyTable<CurrencyData> {
	private final CoinMarketCap coinMarketCap;

	public StandardCurrencyTable(CoinMarketCap coinMarketCap) {
		this(coinMarketCap, null);
	}

	public StandardCurrencyTable(CoinMarketCap coinMarketCap, Duration autoRefreshRate) {
		super(autoRefreshRate);

		this.coinMarketCap = coinMarketCap;
	}

	@Override
	protected CurrencyData loadRow(Currency currency) {
		Pfloat floatRank = coinMarketCap.getRank(currency);

		int rank;
		if (floatRank.isDefined()) {
			rank = Integer.parseInt(floatRank.toString());
		} else {
			return null;
		}

		Pfloat price = coinMarketCap.getUsdPrice(currency);
		Pfloat marketCap = coinMarketCap.getMarketCap(currency);
		Pfloat volume = coinMarketCap.get24HVolume(currency);

		CurrencyData newData = new CurrencyData(currency, rank, currency.toString(), price, marketCap, volume);
		return newData;
	}
}
