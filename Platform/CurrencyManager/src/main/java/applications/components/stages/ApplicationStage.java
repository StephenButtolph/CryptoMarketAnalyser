package applications.components.stages;

import javafx.stage.Stage;

public abstract class ApplicationStage extends Stage {
	private boolean initilized = false;

	public void init() throws Exception {
		initilized = true;
	}

	public void start() throws Exception {
		if (!initilized) {
			init();
			this.show();
		} else {
			resume();
		}
	}

	public abstract void resume();

	public abstract void stop();
}
