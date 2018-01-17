package loggers;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import wrappers.Ref;
import wrappers.Wrapper;

/**
 * Records values for analysis using java 8 lambda functions.
 * 
 * @author Stephen Buttolph
 */
public class FunctionalLogger implements Logger {
	private Runnable function;

	private TemporalAmount separation;

	private Instant previousCollection;
	private Instant nextCollection;
	private boolean shouldUseGivenTime;

	private Ref<Boolean> shouldExecute;
	private boolean startedExecution;

	private Lock lock;

	private ScheduledThreadPoolExecutor scheduler;

	public FunctionalLogger(Runnable function, Instant firstCollection, TemporalAmount separation) {
		this.function = function;

		this.separation = separation;

		previousCollection = null;
		nextCollection = firstCollection;
		shouldUseGivenTime = true;

		shouldExecute = new Ref<>(false);
		startedExecution = false;

		lock = new ReentrantLock();

		scheduler = null;

	}

	@Override
	public boolean start() {
		lock.lock();
		boolean scheduled = shouldExecute.getValue();
		if (!scheduled) {
			scheduler = new ScheduledThreadPoolExecutor(5);
			registerFunction();
		}
		lock.unlock();
		return !scheduled;
	}

	@Override
	public boolean stop() {
		lock.lock();
		boolean scheduled = shouldExecute.getValue();
		if (scheduled) {
			shouldExecute.assign(false);
		}

		Iterable<Runnable> runnables = scheduler.getQueue();
		runnables.forEach(scheduler::remove);
		scheduler.shutdown();
		lock.unlock();
		return scheduled;
	}

	@Override
	public Instant getNextCollectionTime() {
		return nextCollection;
	}

	@Override
	public void setNextCollectionTime(Instant nextCollectionTime) {
		lock.lock();
		nextCollection = nextCollectionTime;
		shouldUseGivenTime = true;
		lock.unlock();
	}

	@Override
	public TemporalAmount getCollectionSeparation() {
		return separation;
	}

	@Override
	public void setCollectionSeparation(TemporalAmount separation) {
		lock.lock();
		this.separation = separation;
		lock.unlock();
	}

	private void registerFunction() {
		lock.lock();
		if (shouldUseGivenTime) {
			registerFunction(nextCollection);
		} else {
			registerFunction(previousCollection.plus(separation));
		}
		lock.unlock();
	}

	private void registerFunction(Instant nextCollectionTime) {
		lock.lock();
		nextCollection = nextCollectionTime;

		if (!startedExecution) {
			shouldExecute.assign(false);
			shouldExecute = new Ref<>(true);
		}
		shouldExecute.assign(true);

		final Wrapper<Boolean> ref = shouldExecute;
		Runnable internalFunction = () -> {
			lock.lock();
			boolean willExecute = ref.getValue();
			if (willExecute) {
				startedExecution = true;
				previousCollection = nextCollection;
				shouldUseGivenTime = false;
			}
			lock.unlock();

			if (willExecute) {
				function.run();
				registerFunction();
				startedExecution = false;
			}
		};

		long waitTime = nextCollection.toEpochMilli() - Instant.now().toEpochMilli();
		if (waitTime < 0) {
			waitTime = 0;
		}

		scheduler.schedule(internalFunction, waitTime, TimeUnit.MILLISECONDS);
		lock.unlock();
	}
}
