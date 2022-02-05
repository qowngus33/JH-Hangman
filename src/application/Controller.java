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

	public final double initialHeadLayoutY = 119.0;
	public Random rand = new Random();

	private Vector<Character> v = new Vector<Character>(); // 오답인 단어들 저장하는 벡터
	private Vector<String> wordList = new Vector<String>(); // 단어들을 저장하는 벡터

	private String currentWord;
	private int correctWord = 0;
	private int phase = 0;

	@FXML
	private TextField AnswerField;
	@FXML
	private Text AlreadyAnswered;
	@FXML
	private Text FailsText;
	@FXML
	private Text Smile;
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
	@FXML
	private Button submitBtn;

	public void getWordMeaning() {
		// API로 원하는 단어의 뜻을 가져올 함
	}

	@FXML
	private void Restart(ActionEvent event) throws MalformedURLException {
		if (startBtn.getText() == "START") {
			bringWords();
			submitBtn.setDisable(false);
			startBtn.setText("NEXT");
		} else if (startBtn.getText() != "NEXT") {
			bringWords();
			submitBtn.setDisable(false);
			startBtn.setText("NEXT");
		} // 수정할 부분

		rand.setSeed(System.currentTimeMillis());
		currentWord = wordList.elementAt(rand.nextInt(wordList.size() - 1));
		System.out.println(currentWord);

		resetValues();
	}

	@FXML
	private void Submit(ActionEvent event) {
		if (correctWord < 6) {
			String ans = AnswerField.getText();
			AnswerField.setText(""); // Initialize answer field.

			boolean flag = false;

			if (ans.length() == 1)
				if (checkText(ans.charAt(0)))
					if (checkAns(ans.charAt(0)) && v.contains(ans.charAt(0)) == false)
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

				drawHangMan(phase);
			}
		}

		if (correctWord == 6) {
			gameEnded(true);
		}
	}

	public void bringWords() {
		try {
			URL link = new URL("https://random-word-api.herokuapp.com/word?number=1000");
			BufferedReader in = new BufferedReader(new InputStreamReader(link.openStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) { // 한 행씩 읽기
				inputLine = inputLine.substring(1, inputLine.length() - 1);
				String[] salesTeamArray = inputLine.split(",");

				for (int i = 0; i < salesTeamArray.length; i++) {
					if (salesTeamArray[i].length() == 8) {
						wordList.add(salesTeamArray[i].substring(1, salesTeamArray[i].length() - 1));
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
		return (currentWord.indexOf(ch, 0) != -1 && v.indexOf(ch) == -1);
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

		setVisibilityFalse();
		setWordsVoid();

		if (man_head.getLayoutY() == initialHeadLayoutY + 10)
			setHangmanY(-10);
		else if (man_head.getLayoutY() == initialHeadLayoutY - 10)
			setHangmanY(+10);

		correctWord = 0;
		phase = 0;

		AlreadyAnswered.setText("");
		FailsText.setText("Fails: ");
		Smile.setText("");

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
			man_head.setVisible(true);
			break;
		case 4:
			man_body.setVisible(true);
			break;
		case 5:
			man_left_arm.setVisible(true);
			break;
		case 6:
			man_right_arm.setVisible(true);
			break;
		case 7:
			man_left_leg.setVisible(true);
			break;
		case 8:
			man_right_leg.setVisible(true);
			break;
		case 9: // Game is Over.
			slingRope.setVisible(true);
			gameEnded(false);
			break;
		default:
			System.out.print("Wrong input!");
			break;
		}
	}

	public void setHangmanY(int value) {
		man_head.setLayoutY(man_head.getLayoutY() + value);
		man_body.setLayoutY(man_body.getLayoutY() + value);
		man_left_arm.setLayoutY(man_left_arm.getLayoutY() + value);
		man_right_arm.setLayoutY(man_right_arm.getLayoutY() + value);
		man_left_leg.setLayoutY(man_left_leg.getLayoutY() + value);
		man_right_leg.setLayoutY(man_right_leg.getLayoutY() + value);
	}

	public void showMan() {
		man_head.setVisible(true);
		man_body.setVisible(true);
		man_left_arm.setVisible(true);
		man_right_arm.setVisible(true);
		man_left_leg.setVisible(true);
		man_right_leg.setVisible(true);
	}

	public void showAns() {
		word1.setText(currentWord.charAt(0) + "");
		word2.setText(currentWord.charAt(1) + "");
		word3.setText(currentWord.charAt(2) + "");
		word4.setText(currentWord.charAt(3) + "");
		word5.setText(currentWord.charAt(4) + "");
		word6.setText(currentWord.charAt(5) + "");
	}

	public void setVisibilityFalse() {
		man_head.setVisible(false);
		man_body.setVisible(false);
		man_left_arm.setVisible(false);
		man_right_arm.setVisible(false);
		man_left_leg.setVisible(false);
		man_right_leg.setVisible(false);
		slingX.setVisible(false);
		slingY.setVisible(false);
		slingRope.setVisible(false);

	}

	public void setWordsVoid() {
		word1.setText("");
		word2.setText("");
		word3.setText("");
		word4.setText("");
		word5.setText("");
		word6.setText("");
	}

	public void gameEnded(boolean isWon) {
		// if won send value true else send false.
		if (!isWon) {
			Smile.setText("X");
			FailsText.setText("You Lost : (");
			AlreadyAnswered.setText("");

			setHangmanY(-10);
			showAns();
		} else {
			Smile.setText(": )");
			FailsText.setText("You Win !!");
			AlreadyAnswered.setText("");

			showMan();
		}
	}
}
