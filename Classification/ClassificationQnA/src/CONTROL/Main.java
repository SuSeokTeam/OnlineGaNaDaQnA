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
		long start = System.currentTimeMillis();
		int size =  qaList.size();
		ExtractKeyword ek = new ExtractKeyword(cqadb);
	//	String sentence  = "해고당하다에서 당하다의 띄어쓰기가 궁금합니다.?";
		
		for(int i=0 ; i<size; i++){
			QA qa = qaList.get(i);
			ek.extractKeyword(qa.getQuestion(), qa.getNum());
	//	ek.extractKeyword(sentence,0);
		}
		long end = System.currentTimeMillis();

		System.out.println( "실행 시간 : " + ( end - start )/1000.0 ); 

	}

}
