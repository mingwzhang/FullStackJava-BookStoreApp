

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
@WebServlet("/AddBooks")
public class AddBooks extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		String bname=request.getParameter("bname");
		String aname=request.getParameter("aname");
		int price=Integer.parseInt(request.getParameter("price"));
		int stock=Integer.parseInt(request.getParameter("stock"));
		Connection con=DBConnection.getConnection();
		try
		{
		PreparedStatement pst=con.prepareStatement("insert into books values(?,?,?,?)");
		pst.setString(1,bname);
		pst.setString(2,aname);
		pst.setInt(3, price);
		pst.setInt(4, stock);
		int count=pst.executeUpdate();
		if(count>0)
		{
			RequestDispatcher rd=request.getRequestDispatcher("add.html");
			rd.include(request, response);
			pw.println("<h3><center><font color='lightyellow'>Books has Added Success..!!</font></center></h3>");
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

}
}
