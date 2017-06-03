package exchanges;

public class ExchangePair {
	private String monetaryCurrency, commodityCurrency;
	
	public ExchangePair(String monetaryCurrency, String commodityCurrency){
		this.monetaryCurrency = monetaryCurrency;
		this.commodityCurrency = commodityCurrency;
	}

	/**
	 * @return the monetary currency.
	 */
	public String getMonetaryCurrency() {
		return monetaryCurrency;
	}

	/**
	 * @return the commodity currency.
	 */
	public String getCommodityCurrency() {
		return commodityCurrency;
	}
}
