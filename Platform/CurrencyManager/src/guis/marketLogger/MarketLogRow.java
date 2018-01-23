package guis.marketLogger;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import arithmetic.Pfloat;
import constants.Json;
import platforms.currencies.Currency;
import platforms.currencies.CurrencyFactory;
import utils.files.CSVFileUtils;
import utils.types.TypeProducer;
import utils.types.TypeToken;

public class MarketLogRow {
	private Currency currency;
	private Pfloat rank;
	private Pfloat price;
	private Pfloat marketCap;
	private Pfloat volume;

	private Instant timeStamp;
	private transient DateTimeFormatter formatter;

	public MarketLogRow(Currency currency, Pfloat rank, Pfloat price, Pfloat marketCap, Pfloat volume,
			Instant timeStamp) {
		this(currency, rank, price, marketCap, volume, timeStamp, null);
	}

	public MarketLogRow(Currency currency, Pfloat rank, Pfloat price, Pfloat marketCap, Pfloat volume,
			Instant timeStamp, DateTimeFormatter formatter) {
		this.currency = currency;
		this.rank = rank;
		this.price = price;
		this.marketCap = marketCap;
		this.volume = volume;

		this.timeStamp = timeStamp;
		this.formatter = formatter;
	}

	public Instant getTimeStamp() {
		return timeStamp;
	}

	public String toCSVRow() {
		String timeStamp;
		if (formatter != null) {
			timeStamp = formatter.format(this.timeStamp);
		} else {
			timeStamp = this.timeStamp.toString();
		}

		return CSVFileUtils.toLine(currency, rank, price, marketCap, volume, timeStamp);
	}

	@Override
	public String toString() {
		Map<String, String> jsonRow = new HashMap<>();

		jsonRow.put("currency", currency.toString());
		jsonRow.put("rank", rank.toString());
		jsonRow.put("price", price.toString());
		jsonRow.put("marketCap", marketCap.toString());
		jsonRow.put("volume", volume.toString());
		jsonRow.put("timeStamp", timeStamp.toString());

		return Json.GSON.toJson(jsonRow);
	}

	public static MarketLogRow parse(String row) {
		TypeProducer typeProducer = new TypeToken<Map<String, String>>();
		Map<String, String> jsonRow = Json.GSON.fromJson(row, typeProducer.getType());

		Currency currency = CurrencyFactory.parseCurrency(jsonRow.get("currency"));
		Pfloat rank = new Pfloat(jsonRow.get("rank"));
		Pfloat price = new Pfloat(jsonRow.get("price"));
		Pfloat marketCap = new Pfloat(jsonRow.get("marketCap"));
		Pfloat volume = new Pfloat(jsonRow.get("volume"));
		Instant timeStamp = Instant.parse(jsonRow.get("timeStamp"));

		return new MarketLogRow(currency, rank, price, marketCap, volume, timeStamp);
	}
}
