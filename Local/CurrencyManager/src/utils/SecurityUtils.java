package utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import exceptions.AssertionException;

public class SecurityUtils {
	public static final Algorithm HMAC_SHA512;
	private static long lastNonce;

	static {
		HMAC_SHA512 = Algorithm.SHA512;
		lastNonce = -1;
	}

	public static String hash(String data, String key, Algorithm type) {
		Mac mac = getMac(key, type);
		byte[] results = mac.doFinal(data.getBytes());
		return Hex.encodeHexString(results);
	}

	public static String getNonce() {
		long nonce = System.currentTimeMillis();
		if (nonce <= lastNonce) {
			nonce = lastNonce + 1;
		}
		lastNonce = nonce;

		return String.valueOf(nonce);
	}

	private static Mac getMac(String key, Algorithm type) {
		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), type.toString());
		try {
			Mac mac = Mac.getInstance(type.toString());
			mac.init(secretKeySpec);
			return mac;
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new AssertionException();
		}
	}

	public static enum Algorithm {
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
}
