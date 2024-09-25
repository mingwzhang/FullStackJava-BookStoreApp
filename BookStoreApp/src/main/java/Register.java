
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
 * Servlet implementation class AddBooks
 */
@WebServlet("/Register")
public class Register extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		
		String first=request.getParameter("first");
		String last=request.getParameter("last");
		String email=request.getParameter("email");
		String uid=request.getParameter("uid");
		String pass1=request.getParameter("pass1");
		String pass2=request.getParameter("pass2");
		String utype=request.getParameter("utype");
		
		Connection con=DBConnection.getConnection();
		if (pass1.equals(pass2))
		{
			try
			{
				PreparedStatement pst=con.prepareStatement("INSERT INTO Usertable VALUES(?, ?, ?, ?, ?, ?,?)");
				pst.setString(1, first);
				pst.setString(2, last);
				pst.setString(3, email);
				pst.setString(4, uid);
				pst.setString(5, pass1);
				pst.setString(6, pass2);
				pst.setString(7, utype);
				int count=pst.executeUpdate();
				if(count>0)
				{
					RequestDispatcher rd=request.getRequestDispatcher("Login.html");
					rd.include(request, response);
					pw.println("<h3><center><font color='lightyellow'>User account successfully added. Please log in.</font></center></h3>");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			RequestDispatcher rd=request.getRequestDispatcher("Register.html");
			rd.include(request, response);
			pw.println("<h3><center><font color='lightyellow'>Please make sure the passwords match.</font></center></h3>");
		}

}
}
