package exchanges;

import java.util.Collection;

import accounts.Holding;
import arithmetic.Pfloat;
import currencies.Currency;
import currencies.CurrencyMarket;
import offers.Offers;
import tickers.Ticker;

public interface Exchange extends Ticker {
	Offers getOffers(CurrencyMarket exchangePair);

	void getOpenTransactions();

	void getOpenTransactions(CurrencyMarket market);

	void getTradeHistory(CurrencyMarket market);

	void getBalance(Currency currency);

	void sendFundsTo(Exchange exchange, Holding holding);

	void getWalletAddress(Currency currency);

	Collection<CurrencyMarket> getCurrencyMarkets();

	/*
	 * Pfloat get24HVolume(Currency currency);
	 */

	/**
	 * Get the cost of purchasing [amount] units of [commodity] currency in
	 * [currency] currency units.
	 * 
	 * @param currency
	 *            The currency to spend.
	 * @param commodity
	 *            The currency to buy.
	 * @param amount
	 *            The amount of the currency we want to know the cost of buying.
	 * @return The cost of purchasing [amount] units of [commodity] currency in
	 *         [currency] currency units.
	 */
	Pfloat getPrice(CurrencyMarket market, Pfloat amount);

	/**
	 * Get the largest quantity of [commodity] currency that could be bought with
	 * the [toSpend] holding.
	 * 
	 * @param toSpend
	 *            The currency and amount to spend.
	 * @param commodity
	 *            The currency to buy.
	 * @return The amount of [commodity] currency that could be bought.
	 */
	Pfloat getReturn(CurrencyMarket market, Pfloat amount);

	/*
	 * No need for sell methods as selling is the same as buying with the currencies
	 * inverted.
	 */

	/**
	 * Buy the largest possible quantity of [commodity] currency with the [toSpend]
	 * holding.
	 * 
	 * @param toSpend
	 *            The currency and amount to spend.
	 * @param commodity
	 *            The currency to buy.
	 * @return TODO
	 */
	void buy(Pfloat toSpend, CurrencyMarket market);

	/**
	 * Buy [amount] units of [commodity] currency with the [currency] currency.
	 * 
	 * @param currency
	 *            The currency to spend.
	 * @param commodity
	 *            The currency to buy.
	 * @param amount
	 *            The amount of the currency to buy.
	 * @return TODO
	 */
	void buy(CurrencyMarket market, Pfloat toBuy);

	/**
	 * Place an order for the [commodity] currency at a rate of [price] with the
	 * [toSpend] holding.
	 * 
	 * @param toSpend
	 *            The currency and amount to spend.
	 * @param price
	 *            The price, in terms of commodity currency per holding currency, to
	 *            place the order for the [commodity] currency at.
	 * @param commodity
	 *            The currency to buy.
	 * @return TODO
	 */
	void buy(Pfloat toSpend, CurrencyMarket market, Pfloat price);
}
