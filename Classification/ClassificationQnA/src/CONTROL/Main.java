package CONTROL;

import DATA.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			//CQADB cqadb = new CQADB("cqa");
			//cqadb.selectDB("");
	//	CQADB cqadb = new CQADB("hangman");
	//	cqadb.selectDB2("");
		Classifier cf = new Classifier();
		//cf.getWordDictionary2("아래에서처럼 왁친이라는 단어가 백신의 뜻으로 쓰이는 듯합니다. 독감이 문제가 아니라 왁친이 문제다! ITS NOT THE FLU ... ITS THE VACCINE. 뉴스 등에서도 검색이 되는데요. 일반 출판물에서 이 왁친이라는 단어가 쓰이는 게 적당할까요? 아니면 백신으로 고쳐 써야 할까요??");
		try {
			cf.parseJosa();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cf.extractNoun("아래에서처럼 왁친이라는 단어가 백신의 뜻으로 쓰이는 듯합니다. 독감이 문제가 아니라 왁친이 문제다! ITS NOT THE FLU ... ITS THE VACCINE. 뉴스 등에서도 검색이 되는데요. 일반 출판물에서 이 왁친이라는 단어가 쓰이는 게 적당할까요? 아니면 백신으로 고쳐 써야 할까요??");
	}

}
