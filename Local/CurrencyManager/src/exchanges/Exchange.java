package exchanges;

import accounts.Holding;
import arithmetic.Pfloat;
import currencies.Currency;
import offers.Offers;
import tickers.Ticker;

public interface Exchange extends Ticker {
	Offers getOffers(Currency currency, Currency commodity);

	void getOpenTransactions();

	void getOpenTransactions(Currency currency, Currency commodity);

	void getTradeHistory(Currency currency, Currency commodity);

	void getBalance(Currency currency);

	void sendFundsTo(Exchange exchange, Holding holding);

	void getWalletAddress(Currency currency);

	void getTradableCurrencies();

	/*
	 * Pfloat get24HVolume(Currency currency);
	 */

	/*
	 * Variations on price, as price is dependent on amount.
	 */

	/*
	 * Pfloat getPrice(Currency currency, Currency commodity);
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
	Pfloat getPrice(Currency currency, Currency commodity, Pfloat amount);

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
	Pfloat getReturn(Holding toSpend, Currency commodity);

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
	void buy(Holding toSpend, Currency commodity);

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
	void buy(Currency currency, Currency commodity, Pfloat amount);

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
	void buy(Holding toSpend, Pfloat price, Currency commodity);
}
