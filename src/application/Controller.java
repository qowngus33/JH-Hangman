package application;

import java.net.MalformedURLException;
import java.util.Random;
import java.util.Vector;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class Controller {

	public final double initialHeadLayoutY = 119.0;
	public Random rand = new Random();

	private Vector<Character> v = new Vector<Character>(); // 오답인 단어들 저장하는 벡터
	private Dictionary dictionary = new Dictionary();

	private String currentWord;
	private String currentWordMeaning;
	private int correctWord = 0;
	private int phase = 0;
	private int hintNum = 0;

	@FXML
	private TextField AnswerField;
	@FXML
	private TextArea wordMeaningArea;
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
	@FXML
	private Button hintBtn;

	@FXML
	private void Restart(ActionEvent event) throws MalformedURLException {
		if (startBtn.getText() == "START") {
			dictionary.bringWords();
			submitBtn.setDisable(false);
			startBtn.setText("NEXT");
		} else if (startBtn.getText() != "NEXT") {
			dictionary.bringWords();
			submitBtn.setDisable(false);
			startBtn.setText("NEXT");
		} // 수정할 부분

		currentWord = dictionary.pickRandomWord();
		System.out.println(currentWord);

		try {
			currentWordMeaning = dictionary.getWordMeaning(currentWord);
			System.out.println(currentWordMeaning);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		resetValues();
	}

	@FXML
	private void Submit(ActionEvent event) {
		if (correctWord < 6) {
			String ans = AnswerField.getText();
			AnswerField.setText(""); // Initialize answer field.

			if (ans.length() == 1 && isAlphabet(ans.charAt(0))) {
				char temp = ans.toLowerCase().charAt(0);

				if (currentWord.indexOf(temp, 0) != -1 && !v.contains(ans.charAt(0))) {
					setTextVisible(temp);
					v.add(temp);
				} else if (v.contains(temp)) {
					System.out.println("Wrong Character.");
				}

				else {
					phase += 1; // 정답이 틀린 경
					v.add(temp);

					String str = (AlreadyAnswered.getText().length() == 0) ? temp + ""
							: AlreadyAnswered.getText() + ", " + temp;

					AlreadyAnswered.setText(str);

					drawHangMan(phase);
				}

			} else {
				System.out.println("Wrong Character.");
			}
		}

		if (correctWord == 6) {
			gameEnded(true);
		}
	}

	@FXML
	private void Hint(ActionEvent event) {
		if (!hintBtn.isDisabled()) {
			if (word1.getText() == "") {
				word1.setText(currentWord.charAt(0) + "");
			} else if (word2.getText() == "") {
				word2.setText(currentWord.charAt(1) + "");
			} else if (word3.getText() == "") {
				word3.setText(currentWord.charAt(2) + "");
			} else if (word4.getText() == "") {
				word4.setText(currentWord.charAt(3) + "");
			} else if (word5.getText() == "") {
				word5.setText(currentWord.charAt(4) + "");
			} else if (word6.getText() == "") {
				word6.setText(currentWord.charAt(5) + "");
			} else {
				correctWord--;
			}

			hintNum++;
			correctWord++;
		}

		if (hintNum == 2) {
			hintBtn.setDisable(true);
		}

		if (correctWord == 6) {
			gameEnded(true);
		}
	}

	public boolean isAlphabet(char ch) {
		int temp = (int) (ch);
		boolean flag = false;
		if (temp >= 65 && temp <= 90)
			flag = true;
		else if (temp >= 97 && temp <= 122)
			flag = true;

		return flag;
	}

	public void setTextVisible(char ch) {
		if (currentWord.charAt(0) == ch && word1.getText() == "") {
			word1.setText(ch + "");
			correctWord++;
		}
		if (currentWord.charAt(1) == ch && word2.getText() == "") {
			word2.setText(ch + "");
			correctWord++;
		}
		if (currentWord.charAt(2) == ch && word3.getText() == "") {
			word3.setText(ch + "");
			correctWord++;
		}
		if (currentWord.charAt(3) == ch && word4.getText() == "") {
			word4.setText(ch + "");
			correctWord++;
		}
		if (currentWord.charAt(4) == ch && word5.getText() == "") {
			word5.setText(ch + "");
			correctWord++;
		}
		if (currentWord.charAt(5) == ch && word6.getText() == "") {
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
		hintNum = 0;

		AlreadyAnswered.setText("");
		FailsText.setText("Fails: ");
		Smile.setText("");
		currentWordMeaning = (currentWordMeaning.length() == 0) ? "No meaning provided." : currentWordMeaning;
		wordMeaningArea.setText(currentWordMeaning);

		submitBtn.setDisable(false);
		hintBtn.setDisable(false);
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

			setHangmanY(-10);
			showAns();
			submitBtn.setDisable(true);
		} else {
			Smile.setText(": )");
			FailsText.setText("You Win !!");

			showMan();
		}
		hintBtn.setDisable(true);
		submitBtn.setDisable(true);
		AlreadyAnswered.setText("");
	}
}
