

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateBook
 */
@WebServlet("/UpdateBook")
public class UpdateBook extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Connection con=DBConnection.getConnection();
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		String formTitle=request.getParameter("bname");
		String formPrice=request.getParameter("price");
		try
		{
			PreparedStatement pst=con.prepareStatement("UPDATE books SET price=? WHERE bname=?");
			pst.setString(1, formPrice);
			pst.setString(2, formTitle);
			int count=pst.executeUpdate();
			if(count>0)
			{
				RequestDispatcher rd=request.getRequestDispatcher("update.html");
				rd.include(request, response);
				out.println("<h3><center><font color='lightyellow'>"+count+" books have been updated successfully.</font></center></h3>");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
