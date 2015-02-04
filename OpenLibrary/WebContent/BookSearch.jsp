<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="DTO.Book, java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Search</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/BookSearchServlet">

<input type="text" name ="searchString" id="search" size="60" placeholder="Search for books!" />
<input type="submit" name="bookSearch" value="Search Books" />

</form>

<table>
<%
List<Book> booksReturned = new ArrayList<Book>();
booksReturned = (List<Book>) session.getAttribute("searchedBooks");

	if(booksReturned != null){
		for(Book b : booksReturned){
%>
				<tr>
					<td> <%=b.getTitle() %> </td>
					<td> <%=b.getPublisher() %> </td>
				</tr>
<%	
			}
	}
	
%>
</table>

</body>
</html>

