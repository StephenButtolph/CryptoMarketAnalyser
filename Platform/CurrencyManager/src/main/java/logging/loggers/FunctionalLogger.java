package logging.loggers;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import utils.wrappers.Ref;
import utils.wrappers.Wrapper;

/**
 * Records values for analysis using lambda functions.
 * 
 * @author Stephen Buttolph
 */
public class FunctionalLogger implements Logger {
	private Runnable function;

	private Duration separation;

	private Instant previousCollection;
	private Instant nextCollection;
	private boolean shouldUseGivenTime;

	private Ref<Boolean> shouldExecute;
	private boolean startedExecution;

	private Lock lock;

	private ScheduledThreadPoolExecutor scheduler;
	private ThreadFactory threadFactory;

	public FunctionalLogger(Runnable function, Instant firstCollection, Duration separation) {
		setFunction(function);

		this.separation = separation;

		previousCollection = null;
		nextCollection = firstCollection;
		shouldUseGivenTime = true;

		shouldExecute = new Ref<>(false);
		startedExecution = false;

		lock = new ReentrantLock();

		scheduler = null;
		threadFactory = new LogThreadFactory();
	}

	protected void setFunction(Runnable function) {
		this.function = function;
	}

	@Override
	public boolean start() {
		lock.lock();

		try {
			boolean scheduled = shouldExecute.getValue();
			if (!scheduled) {
				scheduler = new ScheduledThreadPoolExecutor(5, threadFactory);
				registerFunction();
			}
			return !scheduled;
		} finally {
			lock.unlock();
		}

	}

	@Override
	public boolean stop() {
		lock.lock();

		try {
			boolean scheduled = shouldExecute.getValue();
			if (scheduled) {
				shouldExecute.assign(false);
			}

			Iterable<Runnable> runnables = scheduler.getQueue();
			runnables.forEach(scheduler::remove);
			scheduler.shutdown();
			return scheduled;
		} finally {
			lock.unlock();
		}

	}

	@Override
	public Instant getLastCollectionTime() {
		return nextCollection;
	}

	@Override
	public Instant getNextCollectionTime() {
		return nextCollection;
	}

	@Override
	public void setNextCollectionTime(Instant nextCollectionTime) {
		lock.lock();

		try {
			nextCollection = nextCollectionTime;
			shouldUseGivenTime = true;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public Duration getCollectionSeparation() {
		return separation;
	}

	@Override
	public void setCollectionSeparation(Duration separation) {
		lock.lock();

		try {
			this.separation = separation;
		} finally {
			lock.unlock();
		}
	}

	private void registerFunction() {
		lock.lock();

		try {
			if (shouldUseGivenTime) {
				registerFunction(nextCollection);
			} else {
				registerFunction(previousCollection.plus(separation));
			}
		} finally {
			lock.unlock();
		}
	}

	private void registerFunction(Instant nextCollectionTime) {
		lock.lock();

		try {
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
		} finally {
			lock.unlock();
		}
	}
}
