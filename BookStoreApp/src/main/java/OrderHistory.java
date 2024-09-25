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
 * Servlet implementation class View
 */
@WebServlet("/OrderHistory")
public class OrderHistory extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con=DBConnection.getConnection();
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		try
		{
			PreparedStatement pst=con.prepareStatement("SELECT * FROM orderHistory");
			ResultSet rs=pst.executeQuery();
			out.print("<caption>Purchase History<caption>");
			out.print("<table align=\"center\">");
			out.println("<body bgcolor='lightblue'>");
			out.println("<table border=3px align='center' height=200 width=250 padding=20>");
			out.println("<tr>"
					+ "<th>Book Title</th>"
					+ "<th>Author Name</th>"
					+ "<th>Price</th>"
					+ "<th>Stock</th>"
					+ "</tr>");
			while(rs.next())
			{
				out.println("<tr>"
						+ "<td>"+rs.getString(1)+"</td>"
						+ "<td> "+rs.getString(2)+"</td>"
						+ "<td> "+rs.getString(3)+"</td>"
						+ "<td> "+rs.getString(4)+"</td>"
						+ "</tr>");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}