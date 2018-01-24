package platforms.holdings;

import arithmetic.Pfloat;
import platforms.currencies.Currency;

public class LocalHolding implements Holding {
	private Currency currency;
	private Pfloat amount;

	/**
	 * Create a new holding with the given magnitude [amount] and the unit of
	 * currency [currency].
	 * 
	 * @param currency
	 *            The unit of currency that this holding will have.
	 * @param amount
	 *            The magnitude this holding will have.
	 */
	public LocalHolding(Currency currency, Pfloat amount) {
		this.currency = currency;
		this.amount = amount;
	}

	@Override
	public Currency getCurrency() {
		return this.currency;
	}

	@Override
	public Pfloat getAmount() {
		return this.amount;
	}
}
