package exchanges;

import java.util.Collection;

import arithmetic.Pfloat;
import currencies.Currency;
import currencyExchanges.CurrencyMarket;
import holdings.Holding;
import offerGroups.Offers;
import orders.ClosedOrder;
import orders.Order;
import tickers.Ticker;

/**
 * This represents an exchange that allows the analysis and exchange of
 * currencies.
 * 
 * @author Stephen Buttolph
 */
public interface Exchange extends Ticker {
	/**
	 * Get the current order book of the [market] exchange. Will take into account
	 * all applicable taxes. These orders will only include offers that apply to the
	 * internal currency exchange and will be different than the inverted markets
	 * values in normal use cases.
	 * 
	 * @param market
	 *            The exchange pair to process.
	 * @return the current order book of the [market] exchange. Will take into
	 *         account all applicable taxes.
	 */
	Offers getOffers(CurrencyMarket market);

	/**
	 * Get all the trade requests that you have placed on this exchange, that
	 * haven't been fully filled yet.
	 * 
	 * @param market
	 *            The exchange pair to analyze.
	 * @return The trade requests that you have placed on this exchange, that
	 *         haven't been fully filled yet.
	 */
	Collection<? extends Order> getOpenOrders(CurrencyMarket market);

	/**
	 * Get all the trade requests that you have placed on this exchange, that have
	 * been filled.
	 * 
	 * @param market
	 *            The exchange pair to analyze.
	 * @return The trade requests that you have placed on this exchange, that have
	 *         been fully filled.
	 */
	Collection<? extends ClosedOrder> getTradeHistory(CurrencyMarket market);

	/**
	 * Get the current available amount of [currency].
	 * 
	 * @param currency
	 *            The currency you wish to know the balance of.
	 * @return The current available amount of [currency].
	 */
	Pfloat getBalance(Currency currency);

	/**
	 * Send the [holding] in this exchange to the requested [exchange].
	 * 
	 * @param exchange
	 *            The exchange to send the [holding] to.
	 * @param holding
	 *            The holding to send to the [exchange].
	 * @return True if the request was transfered successfully, false otherwise.
	 */
	boolean sendFundsTo(Exchange exchange, Holding holding);

	/**
	 * Get the address to be used to deposit [currency] into this exchange.
	 * 
	 * @param currency
	 *            The currency that can be deposited into this address.
	 * @return The address to be used to deposit [currency] into this exchange, null
	 *         if this exchange doesn't support that currency.
	 */
	String getWalletAddress(Currency currency);

	/**
	 * @return The markets that this currency supports.
	 */
	Collection<? extends CurrencyMarket> getCurrencyMarkets();

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
	 * @return The order placed by this call.
	 */
	Order buy(Pfloat toSpend, CurrencyMarket market);

	/**
	 * Buy [amount] units of [commodity] currency with the [currency] currency.
	 * 
	 * @param currency
	 *            The currency to spend.
	 * @param commodity
	 *            The currency to buy.
	 * @param amount
	 *            The amount of the currency to buy.
	 * @return The order placed by this call.
	 */
	Order buy(CurrencyMarket market, Pfloat toBuy);

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
	 * @return The order placed by this call.
	 */
	Order buy(Pfloat toSpend, CurrencyMarket market, Pfloat price);
}
