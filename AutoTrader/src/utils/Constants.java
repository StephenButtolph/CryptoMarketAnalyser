package utils;

import java.io.IOException;

import exceptions.InitializationException;

public class Constants {
	public static final Account POLONIEX;

	private static String poloniexKeyFile = "Poloniex/key.txt";
	private static String poloniexSecretFile = "Poloniex/secret.txt";
	
	static{
		POLONIEX = initializePoloniex();
	}
	
	private static Account initializePoloniex(){
		try {
			String key = Utils.readFile(poloniexKeyFile);
			String secret = Utils.readFile(poloniexSecretFile);
			
			return new Account(key, secret);
		} catch (IOException exception) {
			throw new InitializationException(exception);
		}
	}
}
