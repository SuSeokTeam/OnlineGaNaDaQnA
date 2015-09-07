package DATA;

public class QA {

	int num;
	String question;
	String answer;
	
	public QA(int num, String question, String answer)
	{
		this.num = num;
		this.question = question;
		this.answer = answer;
	}
	public void setNum(int num)
	{
		this.num = num;
	}
	public int getNum()
	{
		return num;
	}
	
	public void setAnswer(String answer)
	{
		this.answer = answer;
	}
	public void setQuestion(String question)
	{
		this.question = question;
	}
	public String getQuestion()
	{
		return question;
	}
	public String getAnswer()
	{
		return answer;
	}
}
