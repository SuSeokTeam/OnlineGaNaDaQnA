package DATA;

import java.util.ArrayList;

public class Dependent {

	public Word getSuperWord() {
		return superWord;
	}
	public void setSuperWord(Word superWord) {
		this.superWord = superWord;
	}
	public ArrayList<String> getDependentName() {
		return dependentName;
	}
	public void setDependentName(ArrayList<String> dependentName) {
		this.dependentName = dependentName;
	}
	public void addDependent(String dpName)
	{
		dependentName.add(dpName);
	}
	private Word superWord;
	
	private ArrayList<String> dependentName;
	private String superWordName;
	
	public String getSuperWordName() {
		return superWordName;
	}
	public void setSuperWordName(String superWordName) {
		this.superWordName = superWordName;
	}
	
	public Dependent()
	{
		dependentName = new ArrayList<String>();
	}
	
}
