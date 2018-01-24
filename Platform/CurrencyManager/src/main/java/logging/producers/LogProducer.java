package logging.producers;

public interface LogProducer<T> {
	public String getLog(T val);
}
