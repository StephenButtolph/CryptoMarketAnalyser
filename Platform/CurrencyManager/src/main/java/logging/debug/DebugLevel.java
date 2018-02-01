package logging.debug;

public enum DebugLevel {
	ERROR("Error"), INFO("Info");

	private final String NAME;

	private DebugLevel(String name) {
		NAME = name;
	}

	@Override
	public String toString() {
		return NAME;
	}
}