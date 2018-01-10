package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import exchangeAuths.PoloniexAuth;
import utils.FileUtils;
import utils.IterableUtils;
import utils.SecurityUtils;

class UtilsTester {
	private Integer[] makeArray(int length) {
		Integer[] arr = new Integer[length];

		for (int i = 0; i < length; i++) {
			arr[i] = (int) (1000 * Math.random()) - 500;
		}

		return arr;
	}

	@Test
	void testToIterable() {
		int[] lengths = { 0, 1, 2, 5, 10, 100 };

		for (int length : lengths) {
			Integer[] arr = makeArray(length);
			Iterator<Integer> iter = IterableUtils.toIterable(arr).iterator();
			for (Integer cur : arr) {
				if (!iter.hasNext()) {
					fail("Iterator ended too early");
				}

				assertEquals(cur, iter.next());
			}
			assertFalse(iter.hasNext());
		}
	}

	@Test
	void testFlatten() {
		ArrayList<ArrayList<Integer>> list = new ArrayList<>();

		ArrayList<Integer> l1 = new ArrayList<>();
		ArrayList<Integer> l2 = new ArrayList<>();
		ArrayList<Integer> l3 = new ArrayList<>();

		assertFalse(IterableUtils.flatten(list).iterator().hasNext());

		list.add(l1);

		assertFalse(IterableUtils.flatten(list).iterator().hasNext());

		l1.add(0);
		l1.add(1);
		l1.add(2);

		l3.add(3);

		list.add(l2);
		list.add(l3);

		Integer expected = 0;
		for (Integer val : IterableUtils.flatten(list)) {
			assertEquals(val, expected++);
		}

		assertEquals(4, (int) expected);
	}

	@Test
	void testMap() {
		int[] lengths = { 0, 1, 2, 5, 10, 100 };

		for (int length : lengths) {
			Integer[] arr = makeArray(length);
			Iterable<Integer> iter = IterableUtils.toIterable(arr);
			Iterator<Double> iterMapped = IterableUtils.map(iter, x -> 2.5 * x).iterator();
			for (Integer cur : arr) {
				if (!iterMapped.hasNext()) {
					fail("Iterator ended too early");
				}

				assertEquals(2.5 * cur, (double) iterMapped.next());
			}
			assertFalse(iterMapped.hasNext());
		}
	}

	@Test
	void testFold() {
		int[] lengths = { 0, 1, 2, 5, 10, 100 };

		for (int length : lengths) {
			Integer[] arr = makeArray(length);
			Iterable<Integer> iter = IterableUtils.toIterable(arr);
			int sum = IterableUtils.fold(iter, (x, y) -> x + y, 0);

			int checkSum = 0;
			for (Integer cur : arr) {
				checkSum += cur;
			}

			assertEquals(sum, checkSum);
		}
	}

	@Test
	void testSaveAndLoad() {
		String filePath = "someFile";
		String key = SecurityUtils.getNonce();
		String secret = SecurityUtils.getNonce();

		PoloniexAuth auth = new PoloniexAuth(key, secret);
		FileUtils.save(filePath, auth);
		auth = FileUtils.load(filePath, PoloniexAuth.class);

		assertEquals(key, auth.getApiKey());
		assertEquals(secret, auth.getApiSecret());

		File file = new File(filePath);
		assertTrue(file.delete());
	}
}
