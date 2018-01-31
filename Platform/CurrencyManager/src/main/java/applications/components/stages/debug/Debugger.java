package applications.components.stages.debug;

import applications.components.stages.ApplicationStage;

public class Debugger extends ApplicationStage {
	@Override
	public void resume() {
		this.show();
	}

	@Override
	public void stop() {
		this.hide();
	}
}
