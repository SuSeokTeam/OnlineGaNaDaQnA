package CONTROL;

import java.util.ArrayList;

import DATA.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CQADB cqadb = new CQADB("cqa");
		
	//	cqadb.insertKeyword("전석종 바보");
		
		ArrayList<QA> qaList = cqadb.selectCQA();
		//Hannanum hn = new Hannanum();
	//	Classifier cf = new Classifier();
		
		/*
		try {
			cf.parseJosa();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		int size =  qaList.size();
		ExtractKeyword ek = new ExtractKeyword(cqadb);
	//	String sentence  = "사전을 찾아보니 나-가다,나-오다,달-리다로 되어 있는데, 이것이 합성 동사라는 뜻인가요? 사전을 찾아서 합성 동사임을 한눈에 알 수 있는 방법이 있나요?";
		
		for(int i=0 ; i<size; i++){
			QA qa = qaList.get(i);
			ek.extractKeyword(qa.getQuestion(), qa.getNum());

		}
	
	}

}
