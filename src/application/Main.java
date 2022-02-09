package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
				
		Parent root = FXMLLoader.load(getClass().getResource("startPage.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add("application/startPage.css");
		
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Hangman");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
