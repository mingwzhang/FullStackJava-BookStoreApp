

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CheckUser
 */
@WebServlet("/CheckUser")
public class CheckUser extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		String type=request.getParameter("utype");
		String ID=request.getParameter("uid");
		String PASS=request.getParameter("pwd");
		
		System.out.println(type+ID+PASS);
		HttpSession session=request.getSession();
		
		//Created a Connection Pooling Object to establish the connectivity to DB
		Connection con=DBConnection.getConnection();
		try
		{
			PreparedStatement pst=con.prepareStatement(
					"SELECT id, pass, utype FROM Usertable");
			
			ResultSet rs=pst.executeQuery();
			String retrievedUID="One";
			String retrievedPASS="Two";
			String retrievedType="Three";
			while (rs.next())
			{
				retrievedUID=rs.getString(1);
				retrievedPASS=rs.getString(2);
				retrievedType=rs.getString(3);
				
				System.out.println("Retrieved from DB: "+retrievedUID+" "+retrievedPASS+" "+retrievedType);
				System.out.println("From login form: "+ ID + " "+PASS);
			
				if (ID.equalsIgnoreCase(retrievedUID) && PASS.equals(retrievedPASS))
				{
					switch(retrievedType)
					{
					case "admin":
					case "Admin":
					case "ADMIN":
						System.out.println("We have an ADMIN");
						response.sendRedirect("AdminPage.html");
						break;
					case "user":
					case "User":
					case "USER":
						System.out.println("We have a USER");
						session.setAttribute("userID", retrievedUID);
						response.sendRedirect("CustomerPage.html");
						break;
					default:
						break;
					}
					return;
				}
				else
				{
					continue;
				}
			}	
			System.out.println("We have a USER");
			RequestDispatcher rd=request.getRequestDispatcher("Login.html");
			rd.include(request,response);
			pw.println("<h3><center><font color='red'>Invalid User ID or Password</font></center></h3>");
		}
		catch(ServletException|IOException|SQLException e)
		{
			e.printStackTrace();
		}		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
	}
}
