

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Profile
 */
@WebServlet("/Profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con=DBConnection.getConnection();
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		String fname =request.getParameter("fname");
		String email =request.getParameter("email");
		String id =request.getParameter("id");
		String utype =request.getParameter("utype");
 
		HttpSession session = request.getSession();

		try
		{
			PreparedStatement pst =con.prepareStatement("SELECT fname, lname, email, id, utype FROM usertable");
			ResultSet rs = pst.executeQuery();
			String logID = (String) session.getAttribute("userID");
			
			out.println("<body bgcolor='lightblue'>");
			out.println("<table align='left'>");
			out.println("<header><h1> Profile </h1></header>");
			
			while(rs.next())
			{
				if(logID.equals(rs.getString(4)))
				{							
					out.println("<tr><th>First Name:</th>" + "<td>"+ rs.getString(1) + "</td></tr>" 
							+ "<tr><th>Last Name: </th>"+ "<td>"+ rs.getString(2) + "</td></tr>" 
							+ "<tr><th>Email: </th>"+ "<td>"+ rs.getString(3) + "</td></tr>" 
							+ "<tr><th>ID:  </th>"+ "<td>"+ rs.getString(4) + "</td></tr>" 
							+ "<tr><th>Type: </th>"+ "<td>"+ rs.getString(5) + "</td></tr>" 
							+ "<tr><th>    </th>"+ "<td>"+ "  " + "</td></tr>");
 				}		
			}
 			out.println("<tr><th><a href='CustomerProfile.html'> Edit </a></td></tr>");
			out.print("</table></body>");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
