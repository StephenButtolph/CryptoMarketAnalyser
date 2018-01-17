package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.Instant;

import org.junit.jupiter.api.Test;

import loggers.FunctionalLogger;
import loggers.Logger;

class LoggerTester {
	private int functionalCount;

	@Test
	void testFunctionalLogger() throws InterruptedException {
		functionalCount = 0;

		Instant init = Instant.now();
		Logger logger = new FunctionalLogger(() -> functionalCount++, init, Duration.ofMillis(50));

		logger.start();
		Thread.sleep(215);
		logger.stop();
		Thread.sleep(60);
		assertEquals(5, functionalCount);

		assertEquals(init.plus(Duration.ofMillis(250)), logger.getNextCollectionTime());

		logger.setNextCollectionTime(Instant.now());
		logger.start();
		Thread.sleep(215);
		logger.stop();
		assertEquals(10, functionalCount);

		assertEquals(Duration.ofMillis(50), logger.getCollectionSeparation());

		logger.setCollectionSeparation(Duration.ofMillis(25));
		logger.setNextCollectionTime(Instant.now());
		logger.start();
		Thread.sleep(230);
		logger.stop();
		assertEquals(20, functionalCount);

	}
}
