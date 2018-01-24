package utils.security;

public enum Algorithm {
	SHA512("HmacSHA512");

	private final String type;

	private Algorithm(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}
}