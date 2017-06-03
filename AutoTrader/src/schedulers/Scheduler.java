package schedulers;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import constants.Constants;
import tasks.Task;

public class Scheduler {
	public static ScheduledFuture<?> Schedule(Task toRun) {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(Constants.SCHEDULER.THREAD_POOL_SIZE);

		ScheduledFuture<?> scheduleHandle = scheduler.scheduleAtFixedRate(toRun, toRun.getInitialDelay(),
				toRun.getRepeatDelay(), toRun.getTimeUnit());

		return scheduleHandle;
	}
}
