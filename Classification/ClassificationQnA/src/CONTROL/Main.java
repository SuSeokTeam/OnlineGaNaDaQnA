package CONTROL;

import java.util.ArrayList;

import DATA.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	//	CQADB cqadb = new CQADB("cqa");
	//	ArrayList<QA> qaList = cqadb.selectCQA();
		//Hannanum hn = new Hannanum();
	//	Classifier cf = new Classifier();
		
		/*
		try {
			cf.parseJosa();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//qaList.size() 
			for(int i=0 ; i<2; i++)
		{
			QA qa = qaList.get(i);
	//		cf.extractKeyword(qa.getQuestion(), qa.getAnswer());
			hn.ManualWorkflowSetUp(qa.getQuestion());
			System.out.println();
		}
	//	cf.extractKeyword("학창시절, 학창 시절 중 띄어쓰기는 어떤 게 맞나요?", "학창과 시절은 각각의 단어이므로, 학창 시절과 같이 띄어 적는 것이 맞습니다.");
		
		ExtractKeyword ek = new ExtractKeyword();
		try {
			ek.parseJosa();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0 ; i<qaList.size() ; i++)
		{
			QA qa = qaList.get(i);
			System.out.println("Q : " + qa.getQuestion());
			System.out.println("A : " + qa.getAnswer());
			
			System.out.println("Q : " + ek.setSpace(qa.getQuestion()));
			System.out.println("A : " + ek.setSpace(qa.getAnswer()));
		//	ek.splitSpace(qa.getQuestion());
		//	ek.splitSpace( qa.getAnswer());
			System.out.println();
		}
		*/
		ExtractKeyword ek = new ExtractKeyword();
		String sentence  = "비었음, 비었슴 중 어느 것이 맞나요?";
//		String sentence  = "서울에 간다와 서울로 간다의 에와 로의 차이점이 뭔가요?";
		ek.getDependent(sentence);
		//	sentence = ek.RemoveSpace(sentence);
	//	ek.parseKorean(sentence);
		//System.out.println(ek.RemoveSpace(sentence));
		
	}

}
