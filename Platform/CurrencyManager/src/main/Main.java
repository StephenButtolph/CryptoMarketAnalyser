package main;

import java.io.File;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import arithmetic.Pfloat;
import guis.marketLogger.MarketLogRow;
import platforms.currencies.Currency;
import platforms.currencies.CurrencyFactory;
import utils.collections.maps.MapUtils;
import utils.files.FileUtils;
import utils.types.TypeProducer;
import utils.types.TypeToken;

/**
 * Test entry point for functions not clearly suited for JUnit testing.
 * 
 * @author Stephen Buttolph
 */
public class Main {
	public static void main(String[] args) throws InterruptedException {
		Currency eth = CurrencyFactory.parseSymbol("eth");
		Instant now = Instant.now();
		
		TypeProducer typeProducer = new TypeToken<Map<String, String>>();

		MarketLogRow ethRow = new MarketLogRow(eth, Pfloat.ONE, Pfloat.ZERO, Pfloat.ONE, Pfloat.ZERO, now);
		System.out.println(ethRow);

		Map<Currency, MarketLogRow> map = new HashMap<>();
		map.put(eth, ethRow);

		Map<String, String> serializedMap = MapUtils.convertEntries(map, Object::toString, Object::toString);
		FileUtils.save("myFile", serializedMap, typeProducer);
		
		Map<String, String> loadedMap = FileUtils.load("myFile", typeProducer);
		
		Map<Currency, MarketLogRow> deserializedMap = MapUtils.convertEntries(loadedMap, CurrencyFactory::parseCurrency, MarketLogRow::parse);

		System.out.println(ethRow.getTimeStamp().equals(deserializedMap.get(eth).getTimeStamp()));
		
		new File("myFile").delete();
	}
}
