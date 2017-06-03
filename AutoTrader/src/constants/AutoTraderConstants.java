package constants;

import java.util.concurrent.TimeUnit;

public class AutoTraderConstants {
	public final long INITIAL_DELAY;
	public final long REPEAT_DELAY;
	public final TimeUnit TIME_UNIT;
	
	public AutoTraderConstants(long initialDelay, long repeatDelay, TimeUnit timeUnit){
		INITIAL_DELAY = initialDelay;
		REPEAT_DELAY = repeatDelay;
		TIME_UNIT = timeUnit;
	}
	
	@Override
	public String toString(){
		String output = "Initial Delay: " + INITIAL_DELAY + " " + TIME_UNIT;
		output += "\n";
		output += "Repeat Delay: " + REPEAT_DELAY + " " + TIME_UNIT;
		return output;
	}
}
