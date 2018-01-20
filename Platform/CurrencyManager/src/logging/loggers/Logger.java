package logging.loggers;

import java.time.Duration;
import java.time.Instant;

public interface Logger {
	boolean start();

	boolean stop();

	Instant getLastCollectionTime();

	Instant getNextCollectionTime();

	void setNextCollectionTime(Instant nextCollection);

	Duration getCollectionSeparation();

	void setCollectionSeparation(Duration separation);
}
