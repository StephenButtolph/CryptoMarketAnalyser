package utils;

public class AssertUtils {
	public static void assertEquals(Object fst, Object snd) {
		if (!fst.equals(snd)) {
			String err = "Assertion failed, Objects: " + fst + ", and " + snd +  " failed equality check.";
			System.err.println(err);
		}
	}
}
