package constants;

public class AccountConstants {
	public final String KEY, SECRET;
	
	public AccountConstants(String key, String secret){
		KEY = key;
		SECRET = secret;
	}
	
	@Override
	public String toString(){
		String output = "Key: " + KEY;
		output += "\n";
		output += "Secret: " + SECRET;
		return output;
	}
}
