package DATA;

import java.util.ArrayList;

public class Word {

	public String getWordName() {
		return wordName;
	}
	public ArrayList<Dependent> getDepentWord() {
		return depentWord;
	}
	public void setDepentWord(ArrayList<Dependent> depentWord) {
		this.depentWord = depentWord;
	}
	public void setWordName(String wordName) {
		this.wordName = wordName;
	}
	public ArrayList<Morph> getMorphList() {
		return morphList;
	}
	public void setMorphList(ArrayList<Morph> morphList) {
		this.morphList = morphList;
	}
	
	private String wordName;
	private ArrayList<Morph> morphList;
	private ArrayList<Dependent> depentWord;

}
