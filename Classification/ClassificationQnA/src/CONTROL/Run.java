package CONTROL;

import DATA.CQADB;

public class Run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String dbName = "koreancqa";
		ExtractTopic et = new ExtractTopic(dbName);
	
		et.extractTopic("Q");
		
	}

}
