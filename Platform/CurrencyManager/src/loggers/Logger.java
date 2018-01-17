package loggers;

import java.time.Instant;
import java.time.temporal.TemporalAmount;

public interface Logger {
	public boolean start();

	public boolean stop();

	public Instant getNextCollectionTime();

	public void setNextCollectionTime(Instant nextCollection);

	public TemporalAmount getCollectionSeparation();

	public void setCollectionSeparation(TemporalAmount separation);
}
