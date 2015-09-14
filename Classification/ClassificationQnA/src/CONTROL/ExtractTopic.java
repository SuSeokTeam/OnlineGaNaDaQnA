package CONTROL;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import kr.kaist.ir.korean.data.TaggedMorpheme;
import kr.kaist.ir.korean.data.TaggedSentence;
import kr.kaist.ir.korean.data.TaggedWord;
import kr.kaist.ir.korean.parser.KkokkomaParser;
import DATA.*;

public class ExtractTopic {

	QADB qadb = null;
	KkokkomaParser iParser = null;
	ArrayList<String> keywordList = null;

	public ExtractTopic(String dbName)
	{
		qadb = new QADB(dbName);
		iParser = new KkokkomaParser();
		keywordList = new ArrayList<String>();
	}
	
	public void extractTopic(String type)
	{
		ArrayList<QA> qaList = qadb.selectCQA();
		
		//qaList.size()
		for(int i=0; i<qaList.size(); i++)
		{
			QA q = qaList.get(i);
			splitSentece(q, type);
		}
	}

	public void splitSentece(QA qa, String type)
	{
		ArrayList<String> sentenceList = new ArrayList<String>();
		String sentence = "";
		String nextSentence = "";
		
		if(type.equals("Q"))
		{
			sentence = qa.getQuestion();
		}
		else
		{
			sentence = qa.getAnswer();
		}
		sentenceList.add(sentence);
		int id = qa.getNum();
		int index = -1;
		for(int i=0; i< sentenceList.size(); i++)
		{
			sentence = sentenceList.get(i);
			try {
				if(sentence.length()> 0)
				{
					System.out.println("======== SentenceSplit ========");
					TaggedSentence ts = iParser.dependencyOf(sentence);
					
					nextSentence = ts.getOriginalString(null);
					System.out.println(id +" : " + i +  " : " + nextSentence+ " : " +   type );
					qadb.insertSentence(id, nextSentence, i, type);
		//			System.out.println(ts.toString());
					getDepedent(id, i,ts, type);
					
					index = getIndex(sentence, nextSentence);
					if(index < sentence.length())
					{
						nextSentence = sentence.substring(index);
						sentenceList.add(nextSentence);
					}
					getDFKeyword(type);
				}
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void getDFKeyword(String type)
	{
		for(String s : keywordList)
		{
			qadb.insertDFKeyword( s, type);
		}
		keywordList.clear();
	}
	
	public int getIndex(String sentence, String splitSentence)
	{
		int j =0; 
		int i =0;
		char a  = ' ';
		char b = ' ';
		while(true)
		{
			if(sentence.length() <= i | splitSentence.length() <= j)
				break;
			a = sentence.charAt(i);
			b = splitSentence.charAt(j);
			if(a == ' ')
			{
				i++;
				if(sentence.length() <= i)
					break;
				a = sentence.charAt(i);
			}
			if(b == ' ')
			{
				j++;
				if(splitSentence.length() <= j)
					break;
				b = splitSentence.charAt(j);
			}
			
			if( a == b)
			{
				i++;
				j++;
			}
			else if(a == ' ' || b == ' ')
			{
				continue;
			}
			else
			{
				break;
			}
		}
		return i;
	}
	
	public void getDepedent(int id, int index, TaggedSentence ts, String type)
	{
		ArrayList<String> depList = new ArrayList<String>();
		ArrayList<TaggedWord> tagList = new ArrayList<TaggedWord>();
		String dep = "";
		Queue<TaggedWord> taggedQueue = new LinkedList<TaggedWord>();

		int j=0; 
		for(int i=0 ;i< ts.size(); i++)
		{
			TaggedWord current = ts.getWordAt(i);
			taggedQueue.add(current);
			
			
			if(current.getRawTag() != null )
			{
				if(current.getRawTag().equals("의존 연결") || current.getRawTag().equals("대등 연결") || current.getRawTag().equals("보조 연결") )
				{
					System.out.println("======== Classfy ========");
					while(true)
					{
						if(taggedQueue.isEmpty())
							break;
						TaggedWord next =  taggedQueue.poll();
						dep += next.getOriginalWord().toString() + " ";
						tagList.add(next);
					}
					System.out.println(id + " : " + index + " : " + j + " : " + dep + " : " + type);
					qadb.insertDepandent(id, dep, index,  j, type);
					getKeyword(id, index, j, dep, type, tagList);
					
					depList.add(dep);
					dep =  "";
					j++;
					tagList.clear();
				}
			}
		}
		while(true)
		{
			if(taggedQueue.isEmpty())
				break;
			TaggedWord next =  taggedQueue.poll();
			dep += next.getOriginalWord().toString() + " ";
			tagList.add(next);
		}
		System.out.println("======== Classfy ========");
		System.out.println(id + " : " + index + " : " + j + " : " + dep + " : " + type);
		qadb.insertDepandent(id, dep, index,  j, type);
		getKeyword(id, index, j, dep, type, tagList);
		
		
		depList.add(dep);
		
	}
	
	
	public void getKeyword(int id, int sentenceId, int classId, String sentence, String type, ArrayList<TaggedWord> tagList)
	{
		System.out.println("======== keyword ========");
		Queue<TaggedWord> taggedQueue = new LinkedList<TaggedWord>();
		boolean check = false;
		String dep  = "";
		
		for(int i=0; i< tagList.size(); i++)
		{
			TaggedWord tw = tagList.get(i);
			
			for(int j=0; j<tw.size(); j++)
			{
				TaggedMorpheme tm = tw.getMorphemeAt(j);
				
				if(tm.getTag().charAt(0) == 'J')
				{
					dep = "";
					while(true)
					{
						if(taggedQueue.isEmpty())
							break;
						
						TaggedWord twp = taggedQueue.poll();
						dep += twp.getOriginalWord() + " ";
					}
					
					taggedQueue.clear();
					for(int k=0; k<j; k++)
					{
						TaggedMorpheme tmp = tw.getMorphemeAt(k);
						dep += tmp.getMorpheme();
					}
					dep += " ";
					System.out.println(id + " : " + sentenceId + " : " + classId + " : " + dep + " : " + type);
					qadb.insertKeyword(id, sentenceId, classId, dep, type);
					qadb.insertTFKeyword(id, dep, type);
					if(!keywordList.contains(dep))
					{
						keywordList.add(dep);
					}
					
					check = true;
				}
				
			}
			if(!check)
			{
				taggedQueue.add(tw);
			}
			check = false;
			
		}
	}

}
