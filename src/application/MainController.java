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

public class MainController {

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
	private Line word1Bar;
	@FXML
	private Line word6Bar;
	@FXML
	private Button nextBtn;
	@FXML
	private Button submitBtn;
	@FXML
	private Button hintBtn;
	
	@FXML
	private void Next(ActionEvent event) throws MalformedURLException {

		if (nextBtn.getText() == "START") {
			dictionary.bringWords();
			submitBtn.setDisable(false);
			nextBtn.setText("NEXT");
		} 
		if (nextBtn.getText() != "NEXT") {
			nextBtn.setText("NEXT");
		}

		getWord();
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

		if (correctWord == currentWord.length()) {
			gameEnded(true);
		}
	}

	public void getWord() throws MalformedURLException {
		currentWord = dictionary.pickRandomWord();
		System.out.println(currentWord);
		currentWordMeaning = dictionary.getWordMeaning(currentWord);
		
		if(currentWordMeaning.length()>0) {
			if(isAlphabet(currentWordMeaning.charAt(0))) {
				String temp = (currentWordMeaning.charAt(0)+"").toUpperCase();
				currentWordMeaning = temp + currentWordMeaning.substring(1,currentWordMeaning.length()-1);
			}
		}
	}
	
	@FXML
	private void Hint(ActionEvent event) {
		if (!hintBtn.isDisabled()) {
			for(int i=0;i<currentWord.length();i++) {
				if(!v.contains(currentWord.charAt(i))) {
					setTextVisible(currentWord.charAt(i));
					hintNum++;
					v.add(currentWord.charAt(i));
					i = currentWord.length();
					break;
				}
			}
			
		}

		if (hintNum == 3) {
			hintBtn.setDisable(true);
		}

		if (correctWord == currentWord.length()) {
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
		int temp = currentWord.length();
		if (currentWord.length()==6 && word1.getText() == "") {
			if(currentWord.charAt(temp-6)==ch) {
				word1.setText(ch + "");
				correctWord++;
			}
		}
		if (currentWord.length()>=5 && word2.getText() == "") {
			if(currentWord.charAt(temp-5)==ch) {
				word2.setText(ch + "");
				correctWord++;
			}
		}
		if (currentWord.charAt(temp-4) == ch && word3.getText() == "") {
			word3.setText(ch + "");
			correctWord++;
		}
		if (currentWord.charAt(temp-3) == ch && word4.getText() == "") {
			word4.setText(ch + "");
			correctWord++;
		}
		if (currentWord.charAt(temp-2) == ch && word5.getText() == "") {
			word5.setText(ch + "");
			correctWord++;
		}
		if (currentWord.charAt(temp-1) == ch && word6.getText() == "") {
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

		if (currentWord.length() == 5) {
			word6Bar.setVisible(true);
		} else if (currentWord.length() == 6) {
			word1Bar.setVisible(true);
			word6Bar.setVisible(true);
		}

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
		for(int i=0;i<currentWord.length();i++) {
			correctWord = (currentWord.length()+1)*(-1);
			setTextVisible(currentWord.charAt(i));
		}
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
		word1Bar.setVisible(false);
		word6Bar.setVisible(false);
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



