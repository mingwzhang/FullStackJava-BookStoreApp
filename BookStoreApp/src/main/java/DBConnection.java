import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection 
{
	public static Connection getConnection()
	{
		Connection conn=null;
		//String driverName ="oracle.jdbc.driver.OracleDriver"; 

		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "admin");	
			
			//Class.forName(driverName);
			//con=DriverManager.getConnection(url, user, password);
			System.out.println("Connected SUCCESSFULLY.");
		} 
		catch (ClassNotFoundException | SQLException e) 
		{
			System.out.println("FAILED to connect to DB.");
			e.printStackTrace();
		}
		return conn;
	}
	
//	public static void main(String []args)
//	{
//		DBConnection.getConnection();
//	}
}
