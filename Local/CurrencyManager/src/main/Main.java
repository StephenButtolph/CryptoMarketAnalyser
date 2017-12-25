package main;

import java.math.BigDecimal;
import java.util.SortedMap;
import java.util.TreeMap;

import accounts.Transaction;

public class Main {
	public static void main(String[] args) {
		SortedMap<BigDecimal, Transaction> transactions = new TreeMap<>();

		Transaction t1 = new Transaction(null, null, new BigDecimal("5"));
		Transaction t2 = new Transaction(null, null, new BigDecimal("6"));

		transactions.put(t1.getPrice(), t1);
		transactions.put(t2.getPrice(), t2);
		
		for(Transaction t : transactions.values()) {
			System.out.println(t.getPrice());
		}
	}
}
