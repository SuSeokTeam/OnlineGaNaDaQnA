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
			String url = "jdbc:mysql://202.31.202.199:3306/" + dbName;
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
	
	
	public void selectDB(String question)
	{
		System.out.println(question);
		
		try
		{
			String sql = "select * from cqalist";
			rs = st.executeQuery(sql);
			while(rs.next())
			{
				System.out.println(rs.getInt("number"));
				System.out.println ( rs.getString("question"));
				System.out.println(rs.getString("answer"));
			}
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
	
}

