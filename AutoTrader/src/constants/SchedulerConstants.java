package constants;

public class SchedulerConstants {
	public final int THREAD_POOL_SIZE;
	
	public SchedulerConstants(int threadPoolSize){
		THREAD_POOL_SIZE = threadPoolSize;
	}
	
	@Override
	public String toString(){
		String output = "Thread Pool Size: " + THREAD_POOL_SIZE;
		return output;
	}
}
