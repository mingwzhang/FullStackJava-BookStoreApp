<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Order Books</title>
</head>
<body bgcolor='lightblue'>
	<sql:setDataSource var="purchase" driver="com.mysql.jdbc.Driver"
		url="jdbc:mysql://localhost:3306/book" user="root" password="admin" />

	<sql:query dataSource="${purchase}" var="result">
			select * from books;
      	</sql:query>
	
	<form action="OrderBooks" method="post">
	<table border="1" width="75%">
		<tr>
			<th>Book</th>
			<th>Author</th>
			<th>Price</th>
			<th>Stock</th>
			<th>Purchase Amount</th>
		</tr>

		<c:forEach var="row" items="${result.rows}">
			<tr>
				<td><c:out value="${row.bname}" /></td>
				<td><c:out value="${row.aname}" /></td>
				<td><c:out value="${row.price}" /></td>
				<td><c:out value="${row.stock}" /></td>
				
				<td><input type=number value = 0 name="${row.bname}" /></td>
         </tr>
		</c:forEach>
	</table>
	<input type="submit" value="Purchase" />
	</form>
</body>
</html>