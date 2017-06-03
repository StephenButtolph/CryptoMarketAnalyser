package constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import exceptions.InitializationException;
import utils.Utils;

public class Constants {
	public static final AccountConstants POLONIEX;

	private static String poloniexFolder = "Poloniex/";
	private static String poloniexKeyFile = poloniexFolder + "key.txt";
	private static String poloniexSecretFile = poloniexFolder + "secret.txt";
	
	
	public static final AutoTraderConstants AUTO_TRADER;
	
	private static String autoTraderFolder = "AutoTrader/";
	private static String autoTraderInitialDelayFile = autoTraderFolder + "initialDelay.txt";
	private static String autoTraderRepeatDelayFile = autoTraderFolder + "repeatDelay.txt";
	private static String autoTraderTimeUnitFile = autoTraderFolder + "timeUnit.txt";
	
	
	
	static{
		POLONIEX = initializePoloniexConstants();
		AUTO_TRADER = initializeAutoTraderConstants();
	}
	
	
	private static AccountConstants initializePoloniexConstants(){
		try {
			String key = Utils.readFile(poloniexKeyFile);
			String secret = Utils.readFile(poloniexSecretFile);
			
			return new AccountConstants(key, secret);
		} catch (Exception exception) {
			throw new InitializationException(exception);
		}
	}
	
	private static AutoTraderConstants initializeAutoTraderConstants(){
		try {
			String initialDelayString = Utils.readFile(autoTraderInitialDelayFile);
			String repeatDelayString = Utils.readFile(autoTraderRepeatDelayFile);
			String timeUnitString = Utils.readFile(autoTraderTimeUnitFile);
			
			long initialDelay = Long.parseLong(initialDelayString);
			long repeatDelay = Long.parseLong(repeatDelayString);
			TimeUnit timeUnit = TimeUnit.valueOf(timeUnitString);
			
			return new AutoTraderConstants(initialDelay, repeatDelay, timeUnit);
		} catch (Exception exception) {
			throw new InitializationException(exception);
		}
	}
}
