package main;

import java.math.BigDecimal;
import utils.IterableUtils;

public class Main {
	public static void main(String[] args) {
		System.out.println(IterableUtils.fold(IterableUtils.toIterable(new BigDecimal[]{new BigDecimal("5"), new BigDecimal("6")}), BigDecimal::add, BigDecimal.ZERO));
	}
}
