package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import currencies.Currency;
import currencies.CurrencyFactory;

class CurrencyTester {
	@Test
	void testParseSymbol() {
		String usdt = "USDT";
		String btc = "btc";
		String eth = "eTh";

		Currency tether = CurrencyFactory.parseSymbol(usdt);
		Currency bitcoin = CurrencyFactory.parseSymbol(btc);
		Currency ethereum = CurrencyFactory.parseSymbol(eth);

		assertEquals("Tether", tether.getName());
		assertEquals("USDT", tether.getSymbol());
		
		assertEquals("Bitcoin", bitcoin.getName());
		assertEquals("BTC", bitcoin.getSymbol());
		
		assertEquals("Ethereum", ethereum.getName());
		assertEquals("ETH", ethereum.getSymbol());
	}
	
	@Test
	void testParseName() {
		String usdt = "Tether";
		String btc = "bitcoin";
		String eth = "EtHeReuM";

		Currency tether = CurrencyFactory.parseName(usdt);
		Currency bitcoin = CurrencyFactory.parseName(btc);
		Currency ethereum = CurrencyFactory.parseName(eth);

		assertEquals("Tether", tether.getName());
		assertEquals("USDT", tether.getSymbol());
		
		assertEquals("Bitcoin", bitcoin.getName());
		assertEquals("BTC", bitcoin.getSymbol());
		
		assertEquals("Ethereum", ethereum.getName());
		assertEquals("ETH", ethereum.getSymbol());
	}
}
