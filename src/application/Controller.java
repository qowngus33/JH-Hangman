package application;

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
	private String currentWord;
	private int correctWord = 0;
	private final int word_num = 14;

	private String ansList[] = { "beetle", "cactus", "donate", "dragon", "egoist", "finish", "floats", "forest",
			"groove", "handle", "header", "health", "insure", "island", "ironic" };

	public Random rand = new Random();
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
	private void Restart(ActionEvent event) {
		rand.setSeed(System.currentTimeMillis());
		currentWord = ansList[rand.nextInt(word_num)];

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
		
		v.clear();
	}

	@FXML
	private void Submit(ActionEvent event) {
		String ans = AnswerField.getText();
		boolean flag = false;

		if (ans.length() == 1) {
			if (checkText(ans.charAt(0))) {
				if (checkAns(ans.charAt(0))) {
					flag = true;
				}
			}
		}

		if (flag) {
			setTextVisible(ans.charAt(0));
			v.add(ans.charAt(0));

		} else {
			phase += 1;
			
			if(AlreadyAnswered.getText().length()==0) {
				AlreadyAnswered.setText(ans.charAt(0)+"");
			} else {
				AlreadyAnswered.setText(AlreadyAnswered.getText()+ ", "+ ans.charAt(0));
			}

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
				AlreadyAnswered.setText("You Lost : (");
				break;
			default:
				System.out.print("Wrong input!");
				break;
			}
		}
		
		if(correctWord==6) {
			AlreadyAnswered.setText("You Win !!");
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

}
