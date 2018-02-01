package applications.components.stages.debug;

import applications.components.stages.ApplicationStage;
import constants.Timing;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import logging.debug.DebugLevel;
import logging.debug.DebugLog;
import logging.debug.DebugLogger;
import utils.guis.ThreadingUtils;
import utils.timing.TimingUtils;

public class Debugger extends ApplicationStage {
	@FXML
	private TextArea terminal;

	@Override
	public void init() throws Exception {
		super.init();

		Parent root = FXMLLoader.load(getClass().getResource(Constants.XML_PATH));
		terminal = (TextArea) root.lookup("#terminal");

		this.setTitle(Constants.TITLE);
		this.getIcons().add(Constants.icon);
		this.setScene(new Scene(root));

		ThreadingUtils.runForever(DebugLogger::getUnhandledLog, this::addLog, Timing.ZERO);
	}

	@Override
	public void resume() {
		this.show();
	}

	@Override
	public void stop() {
		this.hide();
	}

	private void addLog(DebugLog log) {
		String format = "%s: Recorded by %s at %s\n%s\n";

		DebugLevel debugLevel = log.getDebugLevel();
		String threadName = log.getThreadName();
		String timeStamp = TimingUtils.getCurrentTimestampMedium();
		String message = log.getMessage();

		String toAppend = String.format(format, debugLevel, threadName, timeStamp, message);
		terminal.appendText(toAppend);
	}
}
