package applications.components.stages;

import applications.components.stages.ApplicationStage;
import applications.components.stages.debug.Debugger;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

public abstract class DebuggableStage extends ApplicationStage {
	public void loadScene(Scene scene) {
		scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			final KeyCombination keyComb = new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN);

			public void handle(KeyEvent ke) {
				if (keyComb.match(ke)) {
					Debugger debugger = StageFactory.getDebugger();
					try {
						debugger.start();
					} catch (Exception e) {
					}
					ke.consume();
				}
			}
		});

		this.setScene(scene);
	}
}
