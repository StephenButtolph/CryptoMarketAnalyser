package main;

import applications.marketLogger.MarketLoggerApp;

/**
 * Test entry point for functions not clearly suited for JUnit testing.
 * 
 * @author Stephen Buttolph
 */
public class Main {
	public static void main(String[] args) {
		MarketLoggerApp.launch(MarketLoggerApp.class, args);
	}
}
