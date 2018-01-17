package guis.marketLogger;

import java.math.BigDecimal;
import java.text.NumberFormat;

import arithmetic.Pfloat;

public class PfloatCurrency extends Pfloat {
	protected PfloatCurrency(Pfloat val) {
		super(val);
	}
	
	public String getUnformattedString() {
		return super.toString();
	}

	@Override
	public String toString() {
		if (!isDefined()) {
			return super.toString();
		}

		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		formatter.setMaximumFractionDigits(Integer.MAX_VALUE);
		return formatter.format(new BigDecimal(super.toString()));
	}
}
