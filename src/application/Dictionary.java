package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.Vector;

public class Dictionary {
	
	private String wordMeaningURL = "https://api.dictionaryapi.dev/api/v1/entries/en/";
	private Vector<String> wordList = new Vector<String>();
	
	public Random rand = new Random();
	
	public void bringWords() {
		try {
			URL link = new URL("https://random-word-api.herokuapp.com/word?number=100&swear=0&lang=en");
			BufferedReader in = new BufferedReader(new InputStreamReader(link.openStream()));
			
			String inputLine;

			while ((inputLine = in.readLine()) != null) { // 한 행씩 읽기
				inputLine = inputLine.substring(1, inputLine.length() - 1);
				String[] array = inputLine.split(",");

				for (int i = 0; i < array.length; i++) {
					if (array[i].length() >= 6 && array[i].length() <= 8 && array[i].charAt(array[i].length()-2)!='s') {
						wordList.add(array[i].substring(1, array[i].length() - 1));
						System.out.println(array[i].substring(1, array[i].length() - 1));
					}
				}
			}
			in.close();

		} catch (IOException e) {
			System.out.println("URL에서 데이터를 읽는 중 오류가 발생 했습니다.");
		}

	}
	
	public String pickRandomWord() {
		if(wordList.isEmpty())
			bringWords();
		
		rand.setSeed(System.currentTimeMillis());
		String randomWord = wordList.elementAt(rand.nextInt(wordList.size()));
		wordList.remove(randomWord);
		return randomWord;
	}
	
	public String getWordMeaning(String word) throws MalformedURLException {
		String wordMeaning = "";
		try {
			URL link = new URL(wordMeaningURL+word);
			System.out.println(link);
			BufferedReader in = new BufferedReader(new InputStreamReader(link.openStream()));
			
			String inputLine;

			if ((inputLine = in.readLine()) != null) { // 한 행씩 읽기
				int temp1 = inputLine.indexOf("\"definition\"");
				int temp2 = inputLine.indexOf("\"", temp1+16);
				wordMeaning = inputLine.substring(temp1+14,temp2);
			}
			in.close();

		} catch (IOException e) {
			System.out.println("URL에서 데이터를 읽는 중 오류가 발생 했습니다.");
		}
		
		return wordMeaning;
	}
}
