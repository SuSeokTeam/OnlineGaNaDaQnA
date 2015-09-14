package DATA;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CQADB {

	Connection con;
	Statement st;
	ResultSet rs;
	
	public CQADB(String dbName) {
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
	public void insertKeyword(String keyword)
	{
		try
		{
			String sql = "insert into keyword values('" + keyword + "' , '" + 1 + "', 'Q')";
			st.executeUpdate(sql);
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
	public boolean findDependent(String keyword, int cqaNum)
	{

		try
		{
			String sql = "select * from dependent where keyword ='" + keyword + "' and cqaNum = '" + cqaNum  + "'";
			rs = st.executeQuery(sql);
			if(rs.next())
			{
				return true;
			}
		
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
		return false;
	}
	
	public void insertDependent(String keyword, int cqaNum)
	{
		try
		{
			
			String sql = "insert into dependent values('" + keyword + "', '" + cqaNum +"')";
			st.executeUpdate(sql);
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
	
	public int selectTF(String keyword)
	{
		int tf  = 0;
		try
		{
			tf  = 0;
			String sql = "select * from keyword where keyword ='" + keyword + "'";
			rs = st.executeQuery(sql);
			while(rs.next())
			{
				tf = rs.getInt("tf");
			}
		
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
		return tf;
	}
	public void updateTF(String keyword, int tf)
	{
		try
		{
			String sql = "update keyword set tf = '"+ tf + "' where keyword ='" + keyword + "'";
			st.executeUpdate(sql);
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
	
	public boolean findKeyword(String keyword)
	{
		try
		{
			String sql = "select * from keyword where keyword ='" + keyword + "'";
			rs = st.executeQuery(sql);
			if(rs.next())
			{
				return true;
			}
		
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
		return false;
	}
	
	public void insertClassify(int cqaNum, String question)
	{

		try
		{
			String keyword = "";
			String sql = "select question from classify where cqanum ='" + cqaNum + "'";
			rs = st.executeQuery(sql);
			
			if(rs.next())
			{
				keyword = rs.getString("question");
				keyword += "#"+ question ;
				sql = "update classify set question = '"+ keyword + "' where cqanum ='" + cqaNum + "'";
				st.executeUpdate(sql);
			}
			else
			{
			//	keyword = rs.getString("question");
				keyword = question;
				sql = "insert into classify values('" + cqaNum + "', '" + keyword +"')";
				st.executeUpdate(sql);
			}
			System.out.println("cqanum : " + cqaNum + " , question : " + keyword);
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
	
	public void updateClassify( int cqaNum , String keyword)
	{
		try
		{
			String sql = "update classify set keyword = '"+ keyword + "' where cqanum ='" + cqaNum + "'";
			st.executeUpdate(sql);
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
}

