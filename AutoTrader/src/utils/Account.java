package utils;

public class Account {
	public final String key, secret;
	
	public Account(String key, String secret){
		this.key = key;
		this.secret = secret;
	}
	
	@Override
	public String toString(){
		String output = "Key: " + key;
		output += "\n";
		output += "Secret: " + secret;
		return output;
	}
}
