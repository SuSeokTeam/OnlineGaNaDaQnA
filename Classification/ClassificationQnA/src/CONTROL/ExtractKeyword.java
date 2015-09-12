package CONTROL;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.security.auth.login.Configuration;

import DATA.Dependent;
import DATA.Morph;
import DATA.Word;
import kr.kaist.ir.korean.data.ConflictedWord;
import kr.kaist.ir.korean.data.TaggedMorpheme;
import kr.kaist.ir.korean.data.TaggedSentence;
import kr.kaist.ir.korean.data.TaggedWord;
import kr.kaist.ir.korean.parser.IntegratedParser;
import kr.kaist.ir.korean.parser.Parser;

public class ExtractKeyword {
	
	ArrayList<String> sentenList;
	ArrayList<ArrayList<Word>> classfier = new ArrayList<ArrayList<Word>>();
	
	ArrayList<String> josaList = new ArrayList<String>();
	
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
	
	public String setSpace(String sentence)
	{
		ArrayList<String> removalSentence = new ArrayList<String>();
		char c;
	//	char punctuation[] = {'(', '[' , ')', ']' ,'{' , '}', '\'', ',' , '.' ,'?'};
		String punctuation[] = {"(", "[" , ")", " ]" ,"{" , "}","'","\"",",",".", "?"};
		String temp = sentence;
	
		for(int j=0; j<punctuation.length; j++)
		{
			temp.replace(punctuation[j], " ");
		}
		
		System.out.println(temp);
		return temp;
	}
	
	public ArrayList<String> splitSpace(String sentence)
	{
		String[] arr = sentence.split(" ");
		ArrayList<String> frequencyWordList = new ArrayList<String>();
		String word = "";
		char c;
		String punctuation[] = {"(", "[" , ")", " ]" ,"{" , "}","'","\"",",",".", "?"};
		String msg = "";
		
		for(int i=0; i < arr.length; i++)
		{
			word = arr[i];
			msg = word;
			
			for(int j=0 ; j<punctuation.length ; j++)
			{
				String punc = punctuation[j];
				if(word.contains(punc))
	 			{	
	 				int index = word.indexOf(punc);
	 				if(index > 0)
	 					msg =  word.substring(0, index);
	 				else
	 			//		msg = word.substring();
	 				break;
	 			}
			}
			if(msg.length() != 0)
			{
				System.out.println(msg);
				frequencyWordList.add(msg);
			}
		}
		System.out.println("=====");
		return frequencyWordList;
	}

	public String RemoveSpace(String sentence)
	{
		sentence = sentence.replaceAll("\\s", ""); 
		
		return sentence;
	}
	
	public void splitComma(String sentnece)
	{
		System.out.println(sentnece);
		sentenList = new ArrayList<String>();
		String[] sentenceList =  sentnece.split(".");
		
		for(int i=0; i<sentenceList.length; i++)
		{
			String sen =  sentenceList[i];
			sentenList.add(sen);
			System.out.println(sen);
		}
	}
	
	public void getDependent(String sentence)
	{
		Parser iParser = null;
		ArrayList<Word> wordList = new ArrayList<Word>();
		
		sentenList = new ArrayList<String>();
		sentenList.add(sentence);
		
		try
		{
			iParser = new IntegratedParser();
			
			for(int i=0 ; i<sentenList.size(); i++)
			{
				TaggedSentence s = iParser.dependencyOf(sentenList.get(i));
	//			System.out.println(s.size() + "          " );
	//			System.out.println(s + "\n=============");
				
				for(TaggedWord tw : s)
				{
			//		System.out.println(tw.getOriginalWord());
			//		System.out.println(tw.getTag());
					System.out.println(tw.toString());
					Word word = parserResult(tw.toString());
					printWord(word);
					wordList.add(word);
					System.out.println("========");
					
				}
				s.clear();
				classifyResult(wordList);
				printWordList();
			}
		
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
	
	public void classifyResult(ArrayList<Word> wordList)
	{
		String superWordName = null;
		ArrayList<Dependent> dpentList = null;
		ArrayList<String> depNameList = null;
		ArrayList<Word> candiWord = null;
		String superName = "";
		boolean isContain = false;
		System.out.println("++++++++++++++++");
		String wordname = "";
		
		for(Word w : wordList)
		{
			
			dpentList = w.getDepentWord();
			wordname = w.getWordName();
			
			
			if(dpentList.size() == 0)
			{
				candiWord = new ArrayList<Word>();
				candiWord.add(w);
				classfier.add(candiWord);
			}
			for(int i=0 ; i<dpentList.size(); i++)
			{
				Dependent d = dpentList.get(i);
				depNameList = d.getDependentName();
				superName = d.getSuperWordName();
				System.out.print(superName  + " : " );
				
				
				if(depNameList.size() == 0 )
				{
					if(i+1 <dpentList.size())
						continue;
					candiWord = new ArrayList<Word>();
					candiWord.add(w);
					classfier.add(candiWord);
					break;
				}
				
				for(int j=0 ;j<depNameList.size(); j++)
				{ 
					String name = depNameList.get(j);
					
					for(Word wd : candiWord)
					{
						if(wd.getWordName().contains(name))
						{
							isContain = true;
							candiWord.add(w);
							break;
						}
					}
					if(isContain)
					{
						break;
					}
					if(isContain ==  false && !wordname.contains(name))
					{
						//classfier.add(candiWord);
						if(j+1 <depNameList.size())
							continue;
						candiWord = new ArrayList<Word>();
						classfier.add(candiWord);
						candiWord.add(w);
						break;
					}
					
					System.out.print(name);
				}
				isContain = false;
				System.out.println();
			}
		}
//		classfier.add(candiWord);
		System.out.println("++++++++++++++++");
	}

	public void printWordList()
	{
		System.out.println("**************");
		
		for(int i =0; i< classfier.size(); i++)
		{
			ArrayList<Word> wordList = classfier.get(i);
			System.out.println(i + " 번째 class");
			for(Word w : wordList)
			{
				System.out.print(w.getWordName() + " ");
			}
			System.out.println();
		}
		System.out.println("***************");
	}
	
	public Word parserResult(String result)
	{
		ArrayList<String> resultList = new ArrayList<String>();
		ArrayList<String> finalList = new ArrayList<String>();
		ArrayList<Word> wordList = new ArrayList<Word>();
		
		String depents[] = result.split("\n");
		for(int i=0; i<depents.length; i++)
		{
			depents[i] = depents[i].replace("\t", "");
			depents[i] = depents[i].replace(" ", "");
			depents[i] = depents[i].replace("|", "");
			
			if(depents[i].length() > 0)
				resultList.add(depents[i]);
		}
		
	/*	for(String msg : resultList)
		{
			System.out.println(msg);
		}*/
	//	System.out.println("========");
		boolean check = false;
		String splitPro[] = {"(","/",")","=",":","^"};
		int leftcount = 0;
		int rightcount = 0;
		int tmp1 = -1;
		String tmp2 = "";
		for(int j=0; j<resultList.size(); j++)
		{
			String msg = resultList.get(j);
			
			for(int i=0 ;i<msg.length(); i++)
			{
				char a = msg.charAt(i);
				
				if(a == '(')
				{
					if(leftcount > rightcount)
						tmp1 = i;
					
					if(leftcount > rightcount + 1)
					{
						
					}
					else
					{
						tmp2 += a;
					}
					leftcount++;
				}
				else if(a == ')')
				{
					if(leftcount > rightcount +1)
					{
						tmp1 = -1;
					}
					else
					{
						tmp2 += a;
					}
					rightcount++;
				}
				else
				{
					if(leftcount > rightcount + 1)
					{
						
					}
					else
					{
						tmp2 += a;
					}
				}
			}
			rightcount = 0;
			leftcount = 0;
			
			resultList.set(j, tmp2);
			tmp2 = "";
			
		}
		
		
		for(String msg : resultList)
		{
			for(int i=0 ;i<splitPro.length; i++)
			{
				msg = msg.replace(splitPro[i], " ");
			}
			finalList.add(msg);
		}
		
		for(int i=0 ;i<finalList.size();i++)
		{
			System.out.println(finalList.get(i));
		}
		
		boolean isdepent = false;
		Word word = new Word();

		ArrayList<Dependent> depList = new ArrayList<Dependent>();
		
		try
		{
		for(int i=0; i<finalList.size();i++)
		{
			String e = finalList.get(i);
	
			if(e.equals("[골격구성]")) //골격구성 체크
			{
				isdepent = true;
				continue;
			}
			else if(e.equals("[참조]")) // 참고는 제거
			{
				isdepent = false;
				break;
			}
			else if(isdepent == true) //골격구성 내의 할일
			{
				String tmp[] = e.split(" ");
				int size  = 0;
				Dependent dpe = null;
				if(tmp[0].contains("의존관계"))
				{
				//	Dependent dp = depList.get(depList.size()-1);
			//		System.out.println(tmp.length);
					
					for(int j=1; j<tmp.length-1; j = j+3)
					{
			//			System.out.println(tmp[j]);
						size = depList.size();
						if(size > 0)
						{
							dpe = depList.get(size-1);
							dpe.addDependent(tmp[j]);
						}
					//	depList.get(depList.size()-1).addDependent(tmp[j]);
					}
				}
				else
				{
					Dependent dp = new Dependent();
					dp.setSuperWordName(tmp[0]);
					depList.add(dp);
				}
			}
			else //나머지 형태소 분석
			{
				String tmp[] = e.split(" ");
				if(tmp[0].contains("의존관계"))
				{
					continue;
				}
				else if(i == 0)
				{
					word.setWordName(tmp[0]);
					ArrayList<Morph> mpList  = new ArrayList<Morph>();
					for(int j=1; j<tmp.length; j=j+3)
					{
						Morph mp = new Morph();
						mp.setMorphName(tmp[j]);
						mp.setOldTag(tmp[j+1]);
						mp.setTag(tmp[j+2]);
						mpList.add(mp);
					}
					word.setMorphList(mpList);
				}
				
				
			}
		}
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
		word.setDepentWord(depList);
		return word;
		
	}
	public void printWord(Word word)
	{
		System.out.println(word.getWordName());
		ArrayList<Dependent> dpList = word.getDepentWord();
		try
		{
			if(dpList.size() > 0)
			{
				for(Dependent dp : dpList)
				{
					System.out.println("지배자 : " + dp.getSuperWordName());
					ArrayList<String> dpnameList = dp.getDependentName();
					for(String s : dpnameList)
					{
						System.out.println("\t" + s);
					}
					
				}
			}
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
		
	}

	public void parseKorean(String sentence)
	{
		Parser iParser = null;

		sentenList = new ArrayList<String>();
		sentenList.add(sentence);
		try
		{
			iParser = new IntegratedParser();
	
			for(int i = 0; i< sentenList.size(); i++)
			{
				TaggedSentence s = iParser.dependencyOf(sentenList.get(i));
				System.out.println(s.toString());
				for(int j=0; j<s.size(); j++)
				{
					TaggedWord w = s.getWordAt(j);
					LinkedList<TaggedWord> dependents =  w.getDependents();
					
					System.out.print(w.getOriginalWord());
					System.out.print(" : ");
					System.out.print(w.getTag());
					System.out.print(" : ");
					System.out.print(w.getRawTag());
					System.out.println();
					
				//	if(dependents.size() > 0)
					//{
						System.out.println("[의존사]");
						
						for(TaggedWord dp : dependents)
						{
					//		LinkedList<TaggedWord> dependentsDp = dp.getDependents();
					/*		for(TaggedMorpheme m : dp)
							{
								System.out.println(m.toString());
								System.out.println(m.getMorpheme());
							}*/
							System.out.print( "\t" + dp.getOriginalWord());
							System.out.print(" : ");
							System.out.print(dp.getTag());
							System.out.print(" : ");
							System.out.print(dp.getRawTag());
							System.out.println();
							System.out.println(dp.toString());
							LinkedList<TaggedWord> dependentsDp = dp.getDependents();
							ConflictedWord cw = (ConflictedWord)dp;
							System.out.println(cw.getOriginalWord());
							/*for(TaggedWord cw : dependentsDp)
							{
								System.out.println(cw.getOriginalWord());
							}*/
									
				//			dp.getDependents();
							 
							/*if(dependentsDp.size() > 0)
							{
								System.out.println( "\t\t" + "[의존사]");
								
								 for(TaggedWord dp2 : dependentsDp )
								 {
									 System.out.print( dp2.getOriginalWord());
									 System.out.print(" : ");
									 System.out.print(dp2.getTag());
									 System.out.print(" : ");
									 System.out.print(dp2.getRawTag());
									 System.out.println();
								}
							 }*/
						}
				//	}
					System.out.println("====");
				}
			}

		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
		
	}

	public void extractKeyword()
	{
		for(int i=0; i< classfier.size(); i++)
		{
			ArrayList<Word> wordList = classfier.get(i);
			
			for(int j=0; j<wordList.size(); j++)
			{
				Word word = wordList.get(j);
				ArrayList<Morph> morphList = word.getMorphList();
				for(int k = 0; k< morphList.size(); k++)
				{
					Morph mh = morphList.get(k);
				//	if()
					
				}
			}
		}
	}
}
