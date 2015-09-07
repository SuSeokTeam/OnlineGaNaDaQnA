package DATA;

import java.util.ArrayList;

public class WordChar {

	char word;
	ArrayList<Integer> indexList;
	
	public WordChar()
	{
		indexList = new ArrayList<Integer>();
	}
	
	public void putIndex(int index)
	{
		indexList.add(index);
	}
	public void setWord(char word)
	{
		this.word = word;
	}
	public char getWord()
	{
		return word;
	}
	public ArrayList<Integer> getIndexList()
	{
		return indexList;
	}
	public int getCount()
	{
		return indexList.size();
	}

}
