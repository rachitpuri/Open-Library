<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, DTO.Book, DTO.Author, DTO.BookISBN" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="bootstrap.css">
<link rel="stylesheet" type="text/css" href="userPage.css">
<script src="JS/jquery-1.11.1.js"></script>
<title>UserHome</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/UserHomeServlet" method="post" style="width:500px">
<div class ="container">
<center>
<h4><% if ( request.getAttribute( "message" ) != null ) { %>  
        <%=request.getAttribute( "message" )%>  
        <% } %>
        </h4>
</center>		
<a type="button" style="top:20px; right:20px; float:right; 
		position:absolute" class="btn btn-primary" href="logout.jsp">Logout</a>
<br>
<div>
<select name="user_box">
  <option value="find_book">search book</option>
  <option value="find_author">search author</option>
</select>
<input type="text" name="search" style="width:15%; display:inline" class="form-control" placeholder="Search by Author/Book">
<input type="submit" name="searchBooks" value="Search" style="width:70px; margin-left:10px">
</div>
<hr>
</div>
</form>

<form action="${pageContext.request.contextPath}/UserProfileServlet" method="post">
<input type="submit" name="userProfileSubmit" value="User Profile" style="width:140px; float:right; position:absolute; top:60px; right:20px" class="btn btn-primary">
<br>
</form>


<table class="tablelist" style="width:60%; float:left; margin-left:10px;">
<%
List<BookISBN> booksReturned = new ArrayList<BookISBN>();
booksReturned = (List<BookISBN>) session.getAttribute("listofbooks");

//List<Author> authorname = new ArrayList<Author>();
//authorname = (List<Author>) session.getAttribute("listofAuthorsName");

List<Author> authors = new ArrayList<Author>();
authors = (List<Author>) session.getAttribute("listofauthors");

Collection<BookISBN> authorbooks = null;

if(authors != null){
	for(Author a : authors){
		authorbooks = a.getBooks();
		System.out.println("authorbooks size XXX:" +authorbooks.size());
		for(BookISBN b : authorbooks){
			%>		
			<tr class="display_book cloneObject">
				<td class="image_data"><img style="width:90px; height:120px" src="<%=b.getImageURL() %>"></img></td>
				
				<td class="book_data">	
				<a href="${pageContext.request.contextPath}/BookDetails.jsp?bid=<%=b.getBookId() %>" style="margin-left:10px"><%=b.getTitle() %></a>
				<div style="margin-left:10px">by <a href="${pageContext.request.contextPath}/AuthorDetails.jsp?aid=<%=b.getAuthor().getId() %>" class="authorName" style="display:inline"><%=b.getAuthor().getName() %></a></div>
				</td>
				
				<!--  <td style="width:20%">
				<a type="button" style="margin-left:30px"
					class="btn btn-primary" href="logout.jsp">Want to Buy</a>
				</td> -->
			</tr>		
		<%	
		}
		//authorbooks.clear();
	}
}else{
if(booksReturned != null){
System.out.println(booksReturned.size());
	for(BookISBN b : booksReturned){
%>		
	<tr class="display_book cloneObject">
		<td class="image_data"><img style="width:90px; height:120px" src="<%=b.getImageURL() %>"></img></td>
		
		<td class="book_data">	
		<a href="${pageContext.request.contextPath}/BookDetails.jsp?bid=<%=b.getBookId() %>" style="margin-left:10px"><%=b.getTitle() %></a>
		<div style="margin-left:10px">by <a href="${pageContext.request.contextPath}/AuthorDetails.jsp?aid=<%=b.getAuthor().getId() %>" class="authorName" style="display:inline"><%=b.getAuthor().getName() %></a></div>
		</td>
		
		<!-- <td style="width:20%">
		<a type="button" style="margin-left:30px"
			class="btn btn-primary" href="logout.jsp">Want to Buy</a>
		</td> -->
	</tr>		
<%		
	}
}
}
%>
</table>

</body>
</html>