package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class startPageController implements Initializable {

	@FXML
	private Button startBtn;
	@FXML
	private Text mainText;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void gameStart(ActionEvent event) {
		
		startBtn.setDisable(true);
		startBtn.setVisible(false);
		mainText.setVisible(false);
		
		System.out.println("Start button clicked");

		try {
			
			Parent start = FXMLLoader.load(getClass().getResource("hangman.fxml"));

			StackPane root = (StackPane) startBtn.getScene().getRoot();
			root.getChildren().add(start);
			root.getStylesheets().add("application/application.css");
			
			//animation
			start.setTranslateX(400);
			Timeline timeline = new Timeline();
			KeyValue keyvalue = new KeyValue(start.translateXProperty(),0);
			KeyFrame keyfame = new KeyFrame(Duration.millis(500),keyvalue);
			timeline.getKeyFrames().add(keyfame);
			timeline.play();
			
		} catch (Exception e) {
			// TODO
		}

	}
}
