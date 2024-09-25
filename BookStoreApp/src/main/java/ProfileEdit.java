
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
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Profile
 */
@WebServlet("/ProfileEdit")
public class ProfileEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = DBConnection.getConnection();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
		String cpass = request.getParameter("cpass");

		HttpSession session = request.getSession();

		try {
			PreparedStatement pst = con.prepareStatement("SELECT fname, lname, email, id, pass, utype FROM usertable");

			PreparedStatement pst2 = con
					.prepareStatement("UPDATE usertable SET fname=?, lname=?, email=? WHERE id = ?");

			ResultSet rs = pst.executeQuery();
			String logID = (String) session.getAttribute("userID");
			Statement st = con.createStatement();
			
			int count = 0;
			while (rs.next()) {
					if (logID.equals(rs.getString(4))) {
						pst2.setString(4, logID);
						if (pass.equals(rs.getString(5)) && pass.equals(cpass)) {

						pst2.setString(1, fname);
						pst2.setString(2, lname);
						pst2.setString(3, email);
						}else {
							out.println(
									"<h3><center><font color='Red'>Please make sure the password is correct / passwords match.</font></center></h3>");
						
						}
						count = pst2.executeUpdate();
					}
			}
			
			if (count > 0) {
				RequestDispatcher rd = request.getRequestDispatcher("CustomerProfile.html");
				rd.include(request, response);
				out.println("<h3><center><font color='green'>Edit Complete</font></center></h3>");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
