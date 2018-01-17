package main;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import arithmetic.Pfloat;
import constants.Auths;
import constants.Timing;
import currencies.Currency;
import currencies.CurrencyFactory;
import exchanges.poloniex.Poloniex;
import holdings.LocalHolding;
import loggers.FunctionalLogger;
import loggers.Logger;

/**
 * Test entry point for functions not clearly suited for JUnit testing.
 * 
 * @author Stephen Buttolph
 */
public class Main {
	public static void main(String[] args) throws InterruptedException {
		//poloniexTest();
		
		Logger logger = new FunctionalLogger(() -> System.out.println(Instant.now().toEpochMilli()), Instant.now().truncatedTo(ChronoUnit.SECONDS).plus(Timing.SECOND), Timing.SECOND);
		logger.start();
		
		Thread.sleep(4500);

		logger.stop();
		logger.start();
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
