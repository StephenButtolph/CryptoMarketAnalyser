package analyzers;

public interface Analyzer {
	/**
	 * @param amountToSpend
	 *            This is that amount that the user is willing to spend in the
	 *            monetary currency for the commodity currency.
	 * @return If the purchase should be made or not.
	 */
	boolean shouldBuy(double amountToSpend);

	/**
	 * @param amountToSell
	 *            This is that amount that the user is willing to sell in the
	 *            commodity currency for the monetary currency.
	 * @return If the sale should be made or not.
	 */
	boolean shouldSell(double amountToSell);
}
