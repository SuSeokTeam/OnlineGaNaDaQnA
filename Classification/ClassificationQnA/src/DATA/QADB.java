package DATA;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class QADB {

	Connection con;
	Statement st;
	ResultSet rs;
	
	public QADB(String dbName) {
		try {
			String driverName = "com.mysql.jdbc.Driver";
			System.out.println(Class.forName(driverName));
			String url = "jdbc:mysql://202.31.202.199:3306/" + dbName + "?useUnicode=true&characterEncoding=utf8" ;
	//		String url = "jdbc:mysql://localhost:3307/" + dbName;
			String id = "root";
			String password = "kle445";
		//	String password = "root";
			con = DriverManager.getConnection(url, id, password);
			st = con.createStatement();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ArrayList<QA> selectCQA()
	{
		ArrayList<QA> cqaList = new ArrayList<QA>();
		
		try
		{
			String sql = "select * from cqalist";
			rs = st.executeQuery(sql);
			while(rs.next())
			{
				QA qa = new QA(rs.getInt("number"), rs.getString("question"), rs.getString("answer"));
				cqaList.add(qa);
			}
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
		
		return cqaList;
	}
	public void insertSentence(int qaNum, String Sentence, int sentenceNum, String type)
	{
		try
		{
			String sql = "insert into sentence values('" + qaNum + "' , '" + Sentence + "' , '" + sentenceNum + "', '" + type + "')";
			st.executeUpdate(sql);
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
	public void insertDepandent(int qaNum, String Sentence, int sentenceNum, int classNum, String type)
	{
		try
		{
			String sql = "insert into dependent values('" + qaNum + "' , '" + sentenceNum + "' , '" + classNum + "' , '" +  Sentence + "', '" + type + "')";
			st.executeUpdate(sql);
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
	public void insertKeyword(int qaNum, int sentenceNum,  int classNum, String keyword, String type)
	{
		try
		{
			String sql = "insert into keyword values('" + qaNum + "' , '" + sentenceNum + "' , '" + classNum  +"' , '" + keyword + "', '" + type + "')";
			st.executeUpdate(sql);
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
	public void insertTFKeyword(int qaNum,  String keyword, String type)
	{
		try
		{
			String sql = "select * from tfkeyword where id = '" + qaNum+ "' and keyword = '" + keyword + "' and type ='" + type  +"'";
			rs = st.executeQuery(sql);
			int tf = 0;
			while(rs.next())
			{
				tf = rs.getInt("tf");
			}
			if(tf == 0)
			{
				sql = "insert into tfkeyword values('" + qaNum  +"' , '" + keyword + "' , '" + 1 + "', '" + type + "')";
				st.executeUpdate(sql);
			}
			else
			{
				tf = tf +1;
				sql = "update tfkeyword set tf = '"+ tf + "' where id = '" + qaNum + "' and keyword = '" + keyword + "' and type ='" + type  +"'";
				st.executeUpdate(sql);
			}
	
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
	public void insertDFKeyword(String keyword, String type)
	{
		try
		{
			String sql = "select * from dfkeyword where  keyword = '" + keyword + "' and type ='" + type  +"'";
			rs = st.executeQuery(sql);
			int df = 0;
			while(rs.next())
			{
				df = rs.getInt("df");
			}
			if(df == 0)
			{
				sql = "insert into dfkeyword values('" +  keyword + "' , '" + 1 + "', '" + type + "')";
				st.executeUpdate(sql);
			}
			else
			{
				df = df+1;
				sql = "update dfkeyword set df = '"+ df + "' where  keyword = '" + keyword + "' and type ='" + type  +"'";
				st.executeUpdate(sql);
			}
	
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
}
