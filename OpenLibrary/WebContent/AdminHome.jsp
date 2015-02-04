<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, DTO.Book, DTO.User, DTO.UserBookIssue" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="bootstrap.css">
<link rel="stylesheet" type="text/css" href="userPage.css">
<style>
th{
    border: 1px solid black;
    border-collapse: collapse;
    text-align: center;
}
td{
    border: 1px solid black;
    border-collapse: collapse;
}
</style>
<script src="JS/jquery-1.11.1.js"></script>
<script>
function getBook(object) {
	var bookTitle = $(object).parent().find('bookname').html();
	console.log(bookTitle);
}</script>  
<title>AdminHome</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/UserHomeServlet" method="post">
<div class ="container">
<center><h4>${message}</h4></center>		
<a type="button" style="top:20px; right:20px; float:right; 
		position:absolute" class="btn btn-primary" href="logout.jsp">Logout</a>
<br>
<div>
<!-- <input type="radio" name="criteria" value="book" checked style="margin-right:3px">book
<input type="radio" name="criteria" value="user"  style="margin-right:3px">user
 -->
<select name="admin_box">
  <option value="search_books">search books</option>
  <option value="search_users">search users</option>
</select>

<input type="text" name="search" style="width:15%; display:inline" class="form-control" placeholder="Search Book/User">
<input type="submit" name="adminbook" value="SUBMIT" style="width:70px; display:inline; margin-left:10px">
<div style="display:inline; margin-left: 100px">Library Revenue: ${revenue}</div>
<input type="submit" name="revenue" value="revenue" style="width:70px; display:inline; margin-left:50px">
</div>

<hr>

</div>
</form>

<table class="tablelist" style="width:60%; float:left">
<%
List<Book> booksReturned = new ArrayList<Book>();
booksReturned = (List<Book>) session.getAttribute("listofbooksAdmin");

if(booksReturned != null){
System.out.println("books:" +booksReturned.size());
int i = booksReturned.size();	
for(Book b : booksReturned){
%>		
	<tr class="display_book cloneObject">
		<td class="image_data"><img style="width:90px; height:120px" src="<%=b.getBookImage() %>"></img></td>
		
		<td class="book_data">	
		<div>
		<a class="bookname" href="${pageContext.request.contextPath}/BookDetailsAdmin.jsp?bid=<%=b.getBookId() %>" style="margin-left:10px"><%=b.getTitle()%></a>
		</div>
		<div style="margin-left:10px">by <div class="authorName" style="display:inline"><%=b.getAuthorId() %></div></div>
		<div style="margin-left:10px">
		<!-- Rating: <label>2.5</label> by <label>100</label> users  -->
		</div>
		</td>
		
	</tr>		
<%		
	}
}
%>

</table>

<table class="tablelist" style="width:100%; float:left">
<%
List<UserBookIssue> usersReturned = new ArrayList<UserBookIssue>();
usersReturned = (List<UserBookIssue>) session.getAttribute("listofusers");

if(usersReturned != null){
System.out.println("hello:" +usersReturned.size());
int i = usersReturned.size();
%>	
<tr>
    <th style="width:10%">UserName</th>
    <th style="width:45%">Books Issued</th>		
</tr>
<% 
for(UserBookIssue u : usersReturned){
%>		
	<tr class="display_book">
		<td class="user_name" style="text-align:center"><div> <%=u.getUser().getFirstName()%> </div></td>
		<td>
		<ul>
  			<li><%=u.getBook().getTitle() %></li>
		</ul>
		</td>
			
	</tr>		
<%		
	}
}
%>
</table>

<%
//if(request.getAttribute("revenue") != null){
//String str = (String)request.getAttribute("revenue");
//double value = 0;
//if(str != null)
//	value = Double.parseDouble(str);
//if(!str.isEmpty()){
%>
<!--  <div>Library Revenue: ${revenue}</div>  -->
<%
//}
%>
</body>
</html>