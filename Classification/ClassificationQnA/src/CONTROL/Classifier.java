package CONTROL;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import DATA.*;

public class Classifier {

	ArrayList<String> josaList = new ArrayList<String>();
//	HashMap<Integer, String> josaList = new HashMap<Integer, String>();
	HashMap<String, Integer> hashmap = new HashMap<String, Integer>();
	
	public void getWordDictionary(String sentence)
	{
	 	String array[] = sentence.split(" ");
	 	
	 	
	 	String temp;
	 	
	 	HashMap<String, Integer> hashmap = new HashMap<String, Integer>();
	 	
	 	for(int i=0; i<array.length; i++)
	 	{
	 		temp = array[i];
	 		if(hashmap.containsKey(temp))
	 		{
	 			hashmap.replace(temp, hashmap.get(temp)+1) ;
	 		}
	 		else
	 		{
	 			hashmap.put(temp, 1);
	 		}
	 	}
	 
	 	
	 	Set key = hashmap.keySet();
	 	
	 	
	 	for(Iterator iterator = key.iterator(); iterator.hasNext();)
	 	{
	 		String keyname = (String)iterator.next();
	 		int value = (Integer)hashmap.get(keyname);
	 		
	 		System.out.println(keyname + " : " + value);
	 	}
	}

	public void getWordDictionary2(String sentence)
	{
	
	 	HashMap<Character, Integer> hashmap = new HashMap<Character, Integer>();
	 	
	 	for(int i=0; i<sentence.length(); i++)
	 	{
	 		
	 		char temp = sentence.charAt(i);
	 		//temp = array[i];
	 		if(hashmap.containsKey(temp))
	 		{
	 			hashmap.replace(temp, hashmap.get(temp)+1) ;
	 		}
	 		else
	 		{
	 			hashmap.put(temp, 1);
	 		}
	 	}
	 
	 	
	 	Set key = hashmap.keySet();
	 	
	 	
	 	for(Iterator iterator = key.iterator(); iterator.hasNext();)
	 	{
	 		char keyname = (char)iterator.next();
	 		int value = (Integer)hashmap.get(keyname);
	 		if(value > 1)
	 			System.out.println(keyname + " : " + value);
	 	}
	}
	
	public void parseJosa() throws Exception
	{
	 	String path = "C://Users//수인//Desktop//시프//조사//조사_기초.txt";
		
		FileInputStream fileInputStream = new FileInputStream(path);
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "MS949");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		boolean check = false;
		boolean result= true;
		int i=0;
		while(result)
		{		
			String str = bufferedReader.readLine();
		
			if(str == null)
			{
				result = false;
				
			}
			else
			{
				for(int j = 0; j<str.length(); j++)
				{
					char c = str.charAt(j);
					if((c >= '\uAC00' && c<= '\uD7AF'))
					{
							
					}
					else
					{
						check = true;
						break;
					}
				}
				
				if(str.length() == 0)
				{
					check = true; 
				}

				
				if(!check)
				{
			//		System.out.println(str);
					josaList.add(str);
					i++;
				}
				check = false;
			}
			
		}
		
		bufferedReader.close();
		inputStreamReader.close();
		fileInputStream.close();
	}
	
	public void extractNoun(String sentence)
	{
		String array[] = sentence.split(" ");
	 	String temp;
	 	String josa;
	 	
	 	boolean check = false;
	 	
	 	System.out.println(" ===== 질문빈도 ===== ");
	 	
	 	for(int i=0; i<array.length; i++)
	 	{
	 		temp = array[i];
	 		
	 		for(int j=0 ; j < josaList.size(); j++)
	 		{
	 			
	 			josa = josaList.get(j);
	 			System.out.println(temp + " " + josa + " " + j);
	 			if(temp.contains(josa))
	 			{
	 				System.out.println(josa);
	 				check = true;
	 				break;
	 			}
	 		}
	 		
	 		if(check)
	 		{
		 		if(hashmap.containsKey(temp))
		 		{
		 			hashmap.replace(temp, hashmap.get(temp)+1) ;
		 		}
		 		else
		 		{
		 			hashmap.put(temp, 1);
		 		}
	 		}
	 		
	 		check = false;
	 	}
	 	
	 	Set key = hashmap.keySet();
	 	
	 	for(Iterator iterator = key.iterator(); iterator.hasNext();)
	 	{
	 		String keyname = (String)iterator.next();
	 		int value = (Integer)hashmap.get(keyname);
	 		
	 		System.out.println(keyname + " : " + value);
	 	}
	}
	
	public void extractAnswer(String sentence)
	{
		String array[] = sentence.split(" ");
	 	String temp;
	 	String josa;
	 	
	 	boolean check = false;
	 	
	 	System.out.println("===== 답빈도 =====");
	 	
	 	for(int i=0; i<array.length; i++)
	 	{
	 		temp = array[i];
	 		
	 		for(int j=0 ; j < josaList.size(); j++)
	 		{
	 			josa = josaList.get(j);
	 			
	 			if(temp.contains(josa))
	 			{
	 				check = true;
	 				break;
	 			}
	 		}
	 		
	 		if(check)
	 		{
		 		if(hashmap.containsKey(temp))
		 		{
		 			System.out.println(temp + " : " + hashmap.get(temp));
		 		}
	 		}
	 		
	 		check = false;
	 	}
	}
	
	//char의 index 빈도수를 나타냄
	public ArrayList<WordChar> getWordCharList(String sentence)
	{
		ArrayList<WordChar> wordcharList = new ArrayList<WordChar>();
			
		for(int i=0; i<sentence.length(); i++)
		{
			char word = sentence.charAt(i);
			int index =  findWord(word, wordcharList);
		
			if(word == ' ')
			{
				continue;
			}
			else if(index == -1)
			{
				WordChar wc = new WordChar();
				wc.setWord(word);
				wc.putIndex(i);
				wordcharList.add(wc);
			}
			else
			{
				WordChar wc = wordcharList.get(index);
				wc.putIndex(i);
			}
		}
			
	/*	for(int i=0; i<wordcharList.size(); i++)
		{
			WordChar wc = wordcharList.get(i);
			System.out.print(wc.getWord()  + " : " );
			
			ArrayList<Integer> intList =  wc.getIndexList();
			
			for(int j=0 ;j<intList.size(); j++)
			{
				System.out.print(intList.get(j) + " ");
			}
			System.out.println();
		}
		*/
		
		return wordcharList;
	}
	
	//띄어쓰기로 분리하여 조사제거
	public ArrayList<String> splitSpace(String sentence)
	{
		String[] arr = sentence.split(" ");
		ArrayList<String> frequencyWordList = new ArrayList<String>();
		/*	String word = "";
		char c;
		String punctuation[] = {"(", "[" , ")", " ]" ,"{" , "}","'","\"",",","."};
		//punctuation
		String msg = "";
		
		for(int i=0; i < arr.length; i++)
		{
			word = arr[i];
			msg = word;
			
			for(int j=0 ;j<punctuation.length; j++)
			{
				String punc = punctuation[j];
				if(word.contains(punc))
	 			{
	 				int index = word.indexOf(punc);
	 				msg =  word.substring(0, index);
	 				frequencyWordList.add(msg);
	 			}
			}
			if(msg.length() != 0)
			{
		//		System.out.println(msg);
				frequencyWordList.add(msg);
			}
		}
	//	System.out.println("=====");*/
		
		for(int i=0; i<arr.length; i++)
		{
			frequencyWordList.add(arr[i]);
		}
		
		return frequencyWordList;
	}
	
	//띄어쓰기 판단 
	public void extractKeyword(String question, String answer)
	{
		System.out.println("Q : " + question);
		System.out.println("A : " + answer);
		
		ArrayList<WordChar> questionList = getWordCharList(question);
		ArrayList<String> frequencyQList =  frequencyWord(questionList, question);
		ArrayList<String> removalJosaQList =  removeJosa(frequencyQList);
		
		ArrayList<String> frequencyAList = splitSpace(answer);
	//	ArrayList<String> removalJosaAList = removeJosa(frequencyAList);
		
		compareToAnswer(removalJosaQList, frequencyAList);
	}
	

	public void getClassify( ArrayList<WordChar> questionList , ArrayList<String> frequencyQList)
	{
		WordChar wc;
		for(int i=0 ;i< questionList.size(); i++)
		{
			wc = questionList.get(i);
			
			
		}
	}
	
	//질문과 담변을 비교하는 것
	public void compareToAnswer(ArrayList<String> removalJosaQList, ArrayList<String> removalJosaAList)
	{
		HashMap<String, Integer> hashmap = new HashMap<String, Integer>();
		
	 	for(int i=0; i < removalJosaAList.size(); i++)
	 	{
	 		String answer = removalJosaAList.get(i);
	 		
	 		for(int j=0; j<removalJosaQList.size(); j++)
	 		{
	 			String question = removalJosaQList.get(j);
	 			
	 			if(answer.contains(question))
	 			{
	 				if(question.length() > 0 && !question.equals(" "))
	 				{
	 				
	 				if(!hashmap.containsKey(question))
		 				hashmap.put(question, 1);
		 			else
		 			{
		 				int key = hashmap.get(question);
		 				hashmap.replace(question, key+1);
		 			}
	 				}
	 			}
	 		}
	 		
	 	/*	if(removalJosaQList.contains(answer))
	 		{
	 			if(!hashmap.containsKey(answer))
	 				hashmap.put(answer, 1);
	 			else
	 			{
	 				int key = hashmap.get(answer);
	 				hashmap.replace(answer, key+1);
	 			}
	 				
	 		} 	*/	
	 	}
	 
	 	
	 	Set key = hashmap.keySet();
	 	
	 	
	 	for(Iterator iterator = key.iterator(); iterator.hasNext();)
	 	{
	 		String keyname = (String)iterator.next();
	 		int value = (Integer)hashmap.get(keyname);
	 		
	 		System.out.println(keyname + " : " + value);
	 	}
	}
	
	//단어의 리스트 중에서 끝에 조사가 포함이 되는 것
	public ArrayList<String> removeJosa(ArrayList<String> frequencyWordList)
	{
		String temp = "";
		String josa = "";
		
		ArrayList<String> removalJosaList = new ArrayList<String>();
		
		for(int i=0; i < frequencyWordList.size(); i++)
	 	{
	 		temp = frequencyWordList.get(i);
	 		
	 		for(int j=0; j< temp.length(); j++)
	 		{
	 			char c = temp.charAt(j);
	 			if(!(c >= '\uAC00' && c<= '\uD7AF'))
				{
	 	//			System.out.println(temp);
	 				removalJosaList.add(temp);
	 				break;
				}
	 		}
	 		
	 		for(int j=0 ; j < josaList.size(); j++)
	 		{
	 			josa = josaList.get(j);
	 			
	 			if(temp.contains(josa))
	 			{
	 				int index = temp.indexOf(josa);
	 				
	 				if(index + josa.length() == temp.length())
	 				{
		 				String msg =  temp.substring(0, index);
		// 				System.out.println(msg + " : " + josa);
		 				removalJosaList.add(msg);
		 				break;
	 				}
	 			}
	 		}
	 	}
		return removalJosaList;
	}
	
	//wordcharList의 빈도수가 2이상인 char을 setnece의 어절로 분리된 단어에 포함되는 것
	public ArrayList<String> frequencyWord(ArrayList<WordChar> wordcharList, String sentence)
	{
		String[] arr = sentence.split(" ");
		ArrayList<String> frequencyWordList = new ArrayList<String>();
		
		for(int i=0; i<wordcharList.size(); i++)
		{
			WordChar wc = wordcharList.get(i);
			
			if(wc.getCount() > 1)
			{
				for(int j=0; j < arr.length;j++)
				{
					String word = "";
					word += wc.getWord();
					
					if(arr[j].contains(word))
					{
						if(!frequencyWordList.contains(arr[j]))
						{
							frequencyWordList.add(arr[j]);
		//					System.out.println(arr[j]);
						}
					}
				}
			}
		}
		return frequencyWordList;
	}
	
	public int findWord(char word, ArrayList<WordChar> wordcharList)
	{
		for(int i=0 ; i< wordcharList.size(); i++)
		{
			if(wordcharList.get(i).getWord() == word)
			{
				return i;
			}
		}
		return -1;
	}
	
}
