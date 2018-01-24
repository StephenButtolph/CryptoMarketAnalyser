package arithmetic;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class PfloatCurrency extends Pfloat {
	public PfloatCurrency(Pfloat val) {
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
