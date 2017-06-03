package tasks;

import java.util.concurrent.TimeUnit;

public interface Task extends Runnable{
	long getInitialDelay();
	TimeUnit getTimeUnit();
	
	boolean shouldRepeat();
	long getRepeatDelay();
}
