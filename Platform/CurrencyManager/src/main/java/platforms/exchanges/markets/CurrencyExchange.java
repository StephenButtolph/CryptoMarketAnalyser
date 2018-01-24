package platforms.exchanges.markets;

import platforms.currencies.markets.CurrencyMarket;
import platforms.exchanges.Exchange;

/**
 * Allows the usage of focusing on individual markets and transacting with them,
 * rather than using then entire exchange platform.
 * 
 * @author Stephen Buttolph
 */
public interface CurrencyExchange extends CurrencyMarket {
	/**
	 * @return the exchange this currency exchange is using.
	 */
	Exchange getExchange();

	// TODO add standard exchange method stubs
}
