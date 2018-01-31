package applications.components.stages.loggers.marketLogger;

import java.io.File;

import applications.components.stages.DebuggableStage;
import applications.components.tables.currencyTables.trackingTables.TrackingTable;
import constants.Timing;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import logging.loggers.currencyLoggers.marketLogger.MarketLogger;
import platforms.tickers.coinMarketCap.CoinMarketCap;
import utils.guis.ThreadingUtils;

public class MarketLoggerStage extends DebuggableStage {
	private FileChooser fileChooser;

	private MarketLogger marketLogger;
	private TrackingTable table;

	@Override
	public void init() throws Exception {
		super.init();

		Parent root = FXMLLoader.load(getClass().getResource(Constants.GUI_XML_PATH));
		GridPane layoutGrid = (GridPane) root.lookup("#layoutGrid");

		fileChooser = new FileChooser();

		CoinMarketCap coinMarketCap = new CoinMarketCap(Timing.SECOND);
		table = new TrackingTable(coinMarketCap, Timing.MINUTE);
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
		this.marketLogger = marketLogger;
		table.setTrackingCurrencies(marketLogger.getCurrencies());
		marketLogger.start();
	}
}
