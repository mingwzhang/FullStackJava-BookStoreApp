

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteBook
 */
@WebServlet("/DeleteBook")
public class DeleteBook extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con=DBConnection.getConnection();
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		String bname=request.getParameter("bname");
		try
		{
		PreparedStatement pst=con.prepareStatement("delete from books where bname=?");
		pst.setString(1,bname);
			int count=pst.executeUpdate();
		if(count>0)
		{
			RequestDispatcher rd=request.getRequestDispatcher("delete.html");
			rd.include(request, response);
			out.println("<h3><center><font color='red'>"+count+" Books has Deleted Success..!!</font></center></h3>");
		}
	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
