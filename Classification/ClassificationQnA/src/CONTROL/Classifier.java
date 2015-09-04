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

public class Classifier {

	ArrayList<String> josaList = new ArrayList<String>();
//	HashMap<Integer, String> josaList = new HashMap<Integer, String>();
	
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
	
	//���¼� �м���
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
		//		josaList.put(i, str);
				josaList.add(str);
				i++;
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
	 	HashMap<String, Integer> hashmap = new HashMap<String, Integer>();
	 	boolean check = false;
	 	
	 	for(int i=0; i<array.length; i++)
	 	{
	 		temp = array[i];
	 		
	 		for(int j=0 ; j < josaList.size(); j++)
	 		{
	 			josa = josaList.get(j);
	 			
	 			if(temp.contains(josa))
	 			{
	 				check = true;
	 				System.out.println(temp + " : "+ check);
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
	
}
