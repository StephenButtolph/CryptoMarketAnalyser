package main;

import arithmetic.Pfloat;
import constants.Auths;
import currencies.Currency;
import currencies.CurrencyFactory;
import exchanges.poloniex.Poloniex;
import holdings.LocalHolding;

/**
 * Test entry point for functions not clearly suited for JUnit testing.
 * 
 * @author Stephen Buttolph
 */
public class Main {
	public static void main(String[] args) {
		poloniexTest();
	}

	public static void poloniexTest() {
		System.out.println(Auths.POLONIEX_AUTH);
		Poloniex p = new Poloniex(Auths.POLONIEX_AUTH);

		Currency usdt = CurrencyFactory.parseSymbol("USDT");

		System.out.println(usdt);

		Object ret = p.sendFundsTo(p, new LocalHolding(usdt, Pfloat.ONE));
		System.out.println(ret);
	}
}
