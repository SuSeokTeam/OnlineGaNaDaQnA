package CONTROL;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javax.security.auth.login.Configuration;

import DATA.CQADB;
import DATA.Dependent;
import DATA.Morph;
import DATA.Word;
import kr.kaist.ir.korean.data.ConflictedWord;
import kr.kaist.ir.korean.data.TaggedMorpheme;
import kr.kaist.ir.korean.data.TaggedSentence;
import kr.kaist.ir.korean.data.TaggedWord;
import kr.kaist.ir.korean.parser.HannanumParser;
import kr.kaist.ir.korean.parser.IntegratedParser;
import kr.kaist.ir.korean.parser.KkokkomaParser;
import kr.kaist.ir.korean.parser.Parser;

public class ExtractKeyword {
	
	ArrayList<ArrayList<TaggedWord>> classfier = new ArrayList<ArrayList<TaggedWord>>();
	ArrayList<String> josaList = new ArrayList<String>();
	ArrayList<TaggedMorpheme> keyword = new ArrayList<TaggedMorpheme>();
	ArrayList<String> keywordArray =null;
	CQADB cqadb = null;
//	ArrayList<String> sentenceList = new ArrayList<String>();
	KkokkomaParser iParser;
	
	public ExtractKeyword(CQADB cqadb)
	{
		this.cqadb = cqadb;
		iParser = new KkokkomaParser();
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
	
	
	public void clear()
	{
		classfier.clear();
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

	public void getString()
	{
	//	Parser iParser = null;
		try {
			Parser iParser = new IntegratedParser();
			TaggedSentence s = iParser.dependencyOf("학창시절, 학창 시절 중 띄어쓰기는 어떤 게 맞나요?");
			System.out.println(s.toString());
			//hp = new HannanumParser();
			//hp.dependencyOf(arg0)
			
			iParser = new IntegratedParser();
			s = iParser.dependencyOf("맑다의 발음이 뭔가요?");
			System.out.println(s.toString());
			
			KkokkomaParser iParser3 = new KkokkomaParser();
			s = iParser3.dependencyOf("학창시절, 학창 시절 중 띄어쓰기는 어떤 게 맞나요?");
			System.out.println(s.toString());
			
			HannanumParser iParser4 = new HannanumParser();
			s = iParser4.dependencyOf("학창시절, 학창 시절 중 띄어쓰기는 어떤 게 맞나요?");
			System.out.println(s.toString());
			
			System.out.println("**");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean findTaggedWord(TaggedWord tw, ArrayList<TaggedWord> tagList)
	{
		for(TaggedWord t: tagList)
		{
			if(t.getOriginalWord().equals(tw.getOriginalWord()))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void extractKeyword(String sentence, int cqaNum)
	{
	
		ArrayList<String> sentenList = new ArrayList<String>();
		sentenList.add(sentence);
		int index = 0;
		try
		{
			String nextSentence = null;
	
			for(int i=0 ; i<sentenList.size(); i++)
			{
				iParser = new KkokkomaParser();
				
				nextSentence = sentenList.get(i);
				System.out.println(nextSentence);
				TaggedSentence s = iParser.dependencyOf(new String(nextSentence));
				
				for(int j=0 ;j<s.size(); j++)
				{
					TaggedWord w = s.getWordAt(j);
					System.out.print(w.getOriginalWord() + " : " );
					
					LinkedList<TaggedWord> listWord= w.getDependents();
					
					for(TaggedWord tw : listWord)
					{
						if(findTaggedWord(tw, classfier.get(classfier.size()-1)))
						{
							ArrayList<TaggedWord> tagwordList2 = classfier.get(classfier.size()-1);
							tagwordList2.add(w);
							classfier.set(classfier.size()-1, tagwordList2);
						}
						
						System.out.print(tw.getOriginalWord() + " , " );
					}
					
					if(listWord.size() < 1)
					{
						ArrayList<TaggedWord> tagwordList2 = new ArrayList<TaggedWord>();
						tagwordList2.add(w);
						classfier.add(tagwordList2);
					}
					
					System.out.println();
				}
				
				String lastWord = s.getOriginalString(null);
//				cqadb.insertClassify(cqaNum, lastWord);
		//		keyword(lastWord);
				
				if(!nextSentence.equals(lastWord))
				{
				//	index = nextSentence.lastIndexOf(lastWord);
					index = lastWord.length()-1;
					
					if(index < nextSentence.length())
					{
						String newString = nextSentence.substring(index, nextSentence.length());
						sentenList.add(newString);
					}
					
				}
				System.out.println();
			}
			printClassfiy();
			attachKeyword();
		//	getKeyword();
		//	clear();
		//	printKeyword();
		//	insertDB(cqaNum);
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
		
	}
	
	public void insertDB(int cqaNum)
	{
		boolean check = false;
		String k = null;
		boolean check2 = false;
		int num =0;
		for(TaggedMorpheme key : keyword)
		{
			k = key.getMorpheme();
			check = cqadb.findKeyword(k);
			if(check)
			{
				check2 = cqadb.findDependent(k, cqaNum);
				if(!check2)
				{
					cqadb.insertDependent(k, cqaNum);
					num = cqadb.selectTF(k) + 1;
					cqadb.updateTF(k, num);
				}
				
			}
			else
			{
				cqadb.insertKeyword(k);
				cqadb.insertDependent(k, cqaNum);
			}
			
			System.out.println(key.toString());
		}
		
	}
	
	public void attachKeyword()
	{
		Queue<TaggedMorpheme> taggedMorphQueue = new LinkedList<TaggedMorpheme>();
		Queue<TaggedWord> tagedQueue = new LinkedList<TaggedWord>();
//		Stack<TaggedWord> tagedStack = new Stack<TaggedWord>();
//		Stack<TaggedMorpheme> tagedMorphStack = new Stack<TaggedMorpheme>();
		keywordArray =  new ArrayList<String>();
		String tmp2 = "";
		boolean check = false;
		int index = 0;
		for(ArrayList<TaggedWord> twList : classfier)
		{
		//	System.out.print("class " + i + " : ");
			for(int i=0 ;i< twList.size(); i++)
			{
				TaggedWord t = twList.get(i);
				//System.out.println(t.getOriginalWord());
				
				for(int j=0; j<t.size(); j++)
				{
					TaggedMorpheme tm = t.getMorphemeAt(j);
			//		System.out.println(tm.getMorpheme().toString());
					System.out.println(tm.toString());
					
					if(tm.getTag().charAt(0)== 'J')
					{
						check = true;
						
						for(;;)
						{
							if(tagedQueue.isEmpty())
								break;
							TaggedWord tmp = tagedQueue.poll();
							tmp2 += tmp.getOriginalWord() + " ";
					//		keywordArray.add(tmp.getOriginalWord());
						}
						for(;;)
						{
							if(taggedMorphQueue.isEmpty())
								break;
							TaggedMorpheme tm2 =  taggedMorphQueue.poll();
							tmp2 += tm2.getMorpheme().toString();

						}
					}
					else
						taggedMorphQueue.add(tm);
				}
				if(!check)
				{
					taggedMorphQueue.clear();
					tagedQueue.add(t);
				}
				check = false;
			}
			tagedQueue.clear();
//			tagedQueue.removeAllElements();
		//	System.out.println();
		//	i++;
		}
		keywordArray.add(tmp2);
	}
	
	public void getKeyword()
	{
		Stack<TaggedMorpheme> tagedStack = new Stack<TaggedMorpheme>();
		keyword = new ArrayList<TaggedMorpheme>();
		
		for(ArrayList<TaggedWord> twList : classfier)
		{
		//	System.out.print("class " + i + " : ");
			for(int i=0 ;i< twList.size(); i++)
			{
				TaggedWord t = twList.get(i);
				//System.out.println(t.getOriginalWord());
				
				for(int j=0; j<t.size(); j++)
				{
					TaggedMorpheme tm = t.getMorphemeAt(j);
			//		System.out.println(tm.getMorpheme().toString());
					System.out.println(tm.toString());
					
					if(tm.getTag().charAt(0)== 'J')
					{
						for(;;)
						{
							if(tagedStack.isEmpty())
								break;
							TaggedMorpheme tmp = tagedStack.pop();
							keyword.add(tmp);
						}
					}
					else
						tagedStack.add(tm);
				}
				
			}
			tagedStack.removeAllElements();
		//	System.out.println();
		//	i++;
		}
	}
	
	public void printClassfiy()
	{
		int i =1;
		for(ArrayList<TaggedWord> twList : classfier)
		{
			System.out.print("class " + i + " : ");
			for(TaggedWord t : twList)
			{
				System.out.print(t.getOriginalWord() + " , ");
			}
			System.out.println();
			i++;
		}
	}
	
	public void printKeyword()
	{
		System.out.println("============");
		for(TaggedMorpheme key : keyword)
		{
			System.out.print(key.toString() + " , ");
		}
		System.out.println();
	}
	
	public void getDependent(String sentence)
	{
	//	Parser iParser = null;
		ArrayList<Word> wordList = new ArrayList<Word>();
		
		ArrayList<String> sentenList = new ArrayList<String>();
		sentenList.add(sentence);
		
		try
		{
		//	Parser iParser = new IntegratedParser();
		//	TaggedSentence s = null;
			String nextSentence = null;
			int lastIndex = -1;
			int firstIndex = 0;
			String prevSentence = null;
			
			for(int i=0 ; i<sentenList.size(); i++)
			{
		//		HannanumParser iParser = new HannanumParser();
		//		KkokkomaParser iParser = new KkokkomaParser();
				Parser iParser = new IntegratedParser();
				
				nextSentence = sentenList.get(i);
				System.out.println(nextSentence);
				TaggedSentence s = iParser.dependencyOf(new String(nextSentence));
				
				for(int j=0; j<s.size(); j++)
				{
					TaggedWord tw = s.getWordAt(j);
				//	System.out.println("========");	
					System.out.println(tw.toString());
					Word word = parserResult(tw.toString());
			//		printWord(word);
					wordList.add(word);
					System.out.println("========");	
				}
				
				
		//		classifyResult(wordList);
		//		printWordList();
			
				
				String lastSentence = s.getLast().getOriginalWord();
				if(sentence.equals(lastSentence))
				{
					break;
				}
				else
				{
				//	sentence.las
					lastIndex = sentence.lastIndexOf(lastSentence);
				//	lastIndex += lastSentence.length();
					
					if(lastIndex != -1 && nextSentence.length() >= lastIndex)
					{
						prevSentence = sentence.substring(lastIndex, nextSentence.length());
						firstIndex = lastIndex+1;
						sentenList.add(prevSentence);
					}
				}
				wordList.clear();
				s.clear();
		//		close();
			}
			
			
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
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
		String splitPro[] = {"(","/",")","=",":","^","+","-"};
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
				else if(a == ' ')
				{
					
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
		
	/*	for(int i=0 ;i<finalList.size();i++)
		{
			System.out.println(finalList.get(i));
		}
		*/
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
	
}
