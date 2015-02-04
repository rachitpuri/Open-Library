<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="DTO.Author, java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Author Search</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/AuthorSearchServlet">

<input type="text" name ="searchString" id="search" size="60" placeholder="Enter Author's name" />
<input type="submit" name="authorSearch" value="Search Author" />

</form>

<table>
<%
List<Author> authorsReturned = new ArrayList<Author>();
authorsReturned = (List<Author>) session.getAttribute("searchedAuthor");

	if(authorsReturned != null){
		for(Author a : authorsReturned){
%>
				<tr>
					<td> <%=a.getName() %> </td>
					<td> <img alt="Not available" src="<%=a.getImageURL() %>"> </td>
				</tr>
<%	
			}
	}
	
%>
</table>


</body>
</html>