package main;

import java.math.BigDecimal;

public class Main {
	public static void main(String[] args) {
		BigDecimal dnum1 = new BigDecimal("0.25");
		BigDecimal dnum2 = new BigDecimal("0.3");
		BigDecimal dnum3 = dnum1.subtract(dnum2);

		System.out.println(dnum1);
		System.out.println(dnum2);
		System.out.println(dnum3);
	}
}
