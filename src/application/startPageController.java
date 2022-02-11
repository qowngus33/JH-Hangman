package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class startPageController implements Initializable {

	@FXML
	private Button startBtn;
	@FXML
	private Text mainText;
	@FXML
	private ProgressBar progressBar;
	@FXML
	private ProgressIndicator p;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@FXML
	public void gameStart(ActionEvent event) {

		System.out.println("Start button clicked");

		Thread thread2 = new Thread() {
			@Override
			public void run() {
				double i = 0.01;
				while (true) {
					i = i + 0.01;

					if (i > 1) {
						break;
					}
				
					printP(i);
					System.out.println("i : " + i);
				}

				closeStage();
			};
		};

		thread2.setDaemon(true);
		thread2.start();

		Stage primaryStage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("hangman.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add("application/application.css");
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setResizable(false);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void closeStage() {
		Stage thisStage = (Stage) startBtn.getScene().getWindow();
		Platform.runLater(() -> {
			thisStage.close();
		});
	}

	public void getMainPane() throws IOException {
		Parent start = FXMLLoader.load(getClass().getResource("hangman.fxml"));

		StackPane root = (StackPane) startBtn.getScene().getRoot();
		root.getChildren().add(start);
		root.getStylesheets().add("application/application.css");
		// animation
		start.setTranslateX(400);
		Timeline timeline = new Timeline();
		KeyValue keyvalue = new KeyValue(start.translateXProperty(), 0);
		KeyFrame keyfame = new KeyFrame(Duration.millis(750), keyvalue);
		timeline.getKeyFrames().add(keyfame);
		timeline.play();
	}

	public void printP(double i) {
		Platform.runLater(() -> {
			progressBar.setProgress(i);
		});
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			
		}
	}
}
