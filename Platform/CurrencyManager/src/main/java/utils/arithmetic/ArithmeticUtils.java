package utils.arithmetic;

import arithmetic.Pfloat;

public class ArithmeticUtils {
	public static Pfloat getPrice(Pfloat amountCurrency, Pfloat amountCommodity) {
		return amountCurrency.divide(amountCommodity);
	}

	public static Pfloat getReturn(Pfloat toSpend, Pfloat price) {
		return toSpend.divide(price);
	}
}
