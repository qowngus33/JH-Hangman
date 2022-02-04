package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.Vector;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class Controller {

	private int phase = 0;
	private Vector<Character> v = new Vector<Character>();
	private Vector<String> ansList = new Vector<String>();
	public Random rand = new Random();
	private String currentWord;
	private int correctWord = 0;

	
	@FXML
	private TextField AnswerField;
	@FXML
	private Text AlreadyAnswered;
	@FXML
	private Text word1;
	@FXML
	private Text word2;
	@FXML
	private Text word3;
	@FXML
	private Text word4;
	@FXML
	private Text word5;
	@FXML
	private Text word6;

	@FXML
	private Circle man_head;
	@FXML
	private Line man_body;
	@FXML
	private Line man_left_arm;
	@FXML
	private Line man_right_arm;
	@FXML
	private Line man_left_leg;
	@FXML
	private Line man_right_leg;
	@FXML
	private Line slingX;
	@FXML
	private Line slingY;
	@FXML
	private Line slingRope;
	@FXML
	private Button startBtn;

	public void getWordMeaning() {
		// API로 원하는 단어의 뜻을 가져올 함
	}

	@FXML
	private void Restart(ActionEvent event) throws MalformedURLException {
		if (startBtn.getText() == "STARTGAME") {
			SetGame();
			startBtn.setText("RESTART");
		} else if (startBtn.getText() != "RESTART") {
			SetGame();
			startBtn.setText("RESTART");
		} // 수정할 부분 

		rand.setSeed(System.currentTimeMillis());
		currentWord = ansList.elementAt(rand.nextInt(ansList.size() - 1));
		System.out.println(currentWord);

		resetValues();
	}

	@FXML
	private void Submit(ActionEvent event) {
		if (startBtn.getText() != "RESTART") {
			//수정할 부분 
		}
		
		String ans = AnswerField.getText();
		AnswerField.setText(""); //Initialize answer field.
		
		boolean flag = false;

		if (ans.length() == 1) 
			if (checkText(ans.charAt(0))) 
				if (checkAns(ans.charAt(0)) && v.contains(ans.charAt(0))==false) 
					flag = true;

		if (flag) {
			setTextVisible(ans.charAt(0));
			v.add(ans.charAt(0));
		} else if (ans.length() != 1 || !checkText(ans.charAt(0)) || v.contains(ans.charAt(0))) {
			System.out.println("Wrong Character.");
		} else {
			phase += 1;
			v.add(ans.charAt(0));
			if (AlreadyAnswered.getText().length() == 0) {
				AlreadyAnswered.setText(ans.charAt(0) + "");
			} else {
				AlreadyAnswered.setText(AlreadyAnswered.getText() + ", " + ans.charAt(0));
			}
			AlreadyAnswered.setX(20 - phase * 5);
			
			drawHangMan(phase);
		}

		if (correctWord == 6) {
			AlreadyAnswered.setX(-5);
			AlreadyAnswered.setText("You Win !!");
			
		}
	}

	public void SetGame() {
		try {
			URL link = new URL("https://random-word-api.herokuapp.com/word?number=1000");
			BufferedReader in = new BufferedReader(new InputStreamReader(link.openStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) { // 한 행씩 읽기
				inputLine = inputLine.substring(1, inputLine.length() - 1);
				String[] salesTeamArray = inputLine.split(",");

				for (int i = 0; i < salesTeamArray.length; i++) {
					if (salesTeamArray[i].length() == 8) {
						ansList.add(salesTeamArray[i].substring(1, salesTeamArray[i].length() - 1));
						System.out.println(salesTeamArray[i].substring(1, salesTeamArray[i].length() - 1));
					}
				}
			}
			in.close();

		} catch (IOException e) {
			System.out.println("URL에서 데이터를 읽는 중 오류가 발생 했습니다.");
		}

	}

	public boolean checkText(char ch) {
		int temp = (int) (ch);
		boolean flag = false;
		if (temp >= 65 && temp <= 90)
			flag = true;
		else if (temp >= 97 && temp <= 122)
			flag = true;
		;

		return flag;
	}

	public boolean checkAns(char ch) {
		if (currentWord.indexOf(ch, 0) != -1 && v.indexOf(ch) == -1)
			return true;
		else
			return false;
	}

	public void setTextVisible(char ch) {
		if (currentWord.charAt(0) == ch) {
			word1.setText(ch + "");
			correctWord++;
		}
		if (currentWord.charAt(1) == ch) {
			word2.setText(ch + "");
			correctWord++;
		}
		if (currentWord.charAt(2) == ch) {
			word3.setText(ch + "");
			correctWord++;
		}
		if (currentWord.charAt(3) == ch) {
			word4.setText(ch + "");
			correctWord++;
		}
		if (currentWord.charAt(4) == ch) {
			word5.setText(ch + "");
			correctWord++;
		}
		if (currentWord.charAt(5) == ch) {
			word6.setText(ch + "");
			correctWord++;
		}
	}

	public void resetValues() {
		man_head.setVisible(false);
		man_body.setVisible(false);
		man_left_arm.setVisible(false);
		man_right_arm.setVisible(false);
		man_left_leg.setVisible(false);
		man_right_leg.setVisible(false);
		slingX.setVisible(false);
		slingY.setVisible(false);
		slingRope.setVisible(false);

		word1.setText("");
		word2.setText("");
		word3.setText("");
		word4.setText("");
		word5.setText("");
		word6.setText("");

		correctWord = 0;
		phase = 0;
		AlreadyAnswered.setText("");
		AnswerField.setText("");

		v.clear();
	}
	
	public void drawHangMan(int phase) {
		switch (phase) {
		case 1:
			slingX.setVisible(true);
			break;
		case 2:
			slingY.setVisible(true);
			break;
		case 3:
			slingRope.setVisible(true);
			break;
		case 4:
			man_head.setVisible(true);
			break;
		case 5:
			man_body.setVisible(true);
			break;
		case 6:
			man_left_arm.setVisible(true);
			break;
		case 7:
			man_right_arm.setVisible(true);
			break;
		case 8:
			man_left_leg.setVisible(true);
			break;
		case 9:
			man_right_leg.setVisible(true);
			AlreadyAnswered.setX(-5);
			AlreadyAnswered.setText("You Lost : (");
			break;
		default:
			System.out.print("Wrong input!");
			break;
		}
	}

}
