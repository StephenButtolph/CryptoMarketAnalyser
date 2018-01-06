package wrappers;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.function.Supplier;

public class RefreshingValue<V> implements Wrapper<V>{
	private Instant prevRefresh;
	private TemporalAmount refreshPeriod;

	private Supplier<V> supplier;
	private V value;

	public RefreshingValue(Supplier<V> supplier, TemporalAmount refreshPeriod) {
		this.supplier = supplier;
		setRefreshPeriod(refreshPeriod);
	}

	public V getValue() {
		if (shouldRefresh()) {
			value = supplier.get();
			prevRefresh = Instant.now();
		}
		return value;
	}

	public void setRefreshPeriod(TemporalAmount refreshPeriod) {
		this.refreshPeriod = refreshPeriod;
	}

	private boolean shouldRefresh() {
		return prevRefresh == null || prevRefresh.plus(refreshPeriod).isBefore(Instant.now());
	}
}
