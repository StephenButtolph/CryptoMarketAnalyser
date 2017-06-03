package exchanges;

import platforms.Platform;

public class Exchange {
	private Platform platform;
	private ExchangePair exchangeIdentifier;
	public Exchange(Platform platform, ExchangePair exchangeIdentifier){
		this.platform = platform;
		this.exchangeIdentifier = exchangeIdentifier;
	}
	
	
}
