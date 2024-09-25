

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OrderBooks
 */
@WebServlet("/OrderBooks")
public class OrderBooks extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Connection con=DBConnection.getConnection();
		response.setContentType("text/html");
		PrintWriter  out =response.getWriter();
		String name;
		int stock;
		
		
		try
		{
			PreparedStatement pst1=con.prepareStatement("INSERT orderHistory values(?,?,?,?)");
			PreparedStatement pst2 = con.prepareStatement("UPDATE books SET stock = ? WHERE bname = ?");
			PreparedStatement pst3 =con.prepareStatement("SELECT bname, aname, price, stock FROM books");
			ResultSet rs = pst3.executeQuery();
			Statement st = con.createStatement();
			
			int count = 0;
			while (rs.next())
			{	
				stock = Integer.parseInt(request.getParameter(rs.getString(1)));

				if(rs.getInt(4) >= stock && rs.getInt(4) > 0)
				{
 					int bookOrdered = rs.getInt(4) - stock;
					pst2.setInt(1, bookOrdered);
					pst2.setString(2,rs.getString(1));
					pst2.executeUpdate();
					
					pst1.setString(1, rs.getString(1));
					pst1.setString(2, rs.getString(2));
					pst1.setInt(3, stock * rs.getInt(3));
					pst1.setInt(4, stock);	
				} 	
				count = pst1.executeUpdate();
				
				if(stock <= 0)
				{
					st.execute("DELETE FROM orderHistory WHERE stock <= 0");	
				}
			}
			if(count > 0)
			{	
				RequestDispatcher rd=request.getRequestDispatcher("OrderBooks.jsp");
				rd.include(request, response);
				out.println("<h3><center><font color='green'>Purchased</font></center></h3>");
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
