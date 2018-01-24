package guis.marketVisualizer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Application for visualizing market depths.
 * 
 * @author Stephen Buttolph
 */
public class MarketVisualizer extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("MarketVisualizer.fxml"));
		primaryStage.setTitle("Registration Form FXML Application");
		primaryStage.setScene(new Scene(root, 800, 500));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}