package applications.components.stages.loggers.marketLogger;

import java.io.File;

import applications.components.stages.DebuggableStage;
import applications.components.tables.currencyTables.trackingTables.TrackingTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import logging.debug.DebugLevel;
import logging.debug.DebugLogger;
import logging.loggers.currencyLoggers.marketLogger.MarketLogger;
import platforms.tickers.coinMarketCap.CoinMarketCap;
import utils.guis.ThreadingUtils;
import utils.timing.TimingUtils;

public class MarketLoggerStage extends DebuggableStage {
	@FXML
	private static GridPane layoutGrid;

	@FXML
	private static Button saveButton;

	@FXML
	private static Button setLogButton;

	private static FileChooser fileChooser;

	private static MarketLogger marketLogger;
	private static TrackingTable table;

	@Override
	public void init() throws Exception {
		super.init();

		Parent root = FXMLLoader.load(getClass().getResource(Constants.XML_PATH));
		layoutGrid = (GridPane) root.lookup("#layoutGrid");
		saveButton = (Button) root.lookup("#saveButton");
		setLogButton = (Button) root.lookup("#setLogButton");

		fileChooser = new FileChooser();

		CoinMarketCap coinMarketCap = new CoinMarketCap(TimingUtils.SECOND);
		table = new TrackingTable(coinMarketCap, TimingUtils.MINUTE);
		layoutGrid.add(table, 0, 0, 2, 1);

		this.setTitle(Constants.TITLE);
		this.getIcons().add(Constants.icon);
		this.loadScene(new Scene(root));

		ThreadingUtils.run(() -> new MarketLogger(coinMarketCap), this::setMarketLogger);
	}

	@Override
	public void resume() {
		if (marketLogger != null) {
			marketLogger.start();
		}
		this.show();
	}

	@Override
	public void stop() {
		if (marketLogger != null) {
			marketLogger.stop();
		}
		this.hide();
	}

	@FXML
	private void setNewLogFile(ActionEvent event) {
		File chosen = fileChooser.showOpenDialog(this);
		if (chosen != null) {
			marketLogger.setLogFile(chosen.getAbsolutePath());
		}
	}

	@FXML
	private void handleSave(ActionEvent event) {
		marketLogger.setCurrencies(table.getTrackingCurrencies());
	}

	private void setMarketLogger(MarketLogger marketLogger) {
		MarketLoggerStage.marketLogger = marketLogger;
		table.setTrackingCurrencies(marketLogger.getCurrencies());
		marketLogger.start();

		saveButton.setDisable(false);
		setLogButton.setDisable(false);

		DebugLogger.addLog("MarketLogger started.", DebugLevel.INFO);
	}
}
