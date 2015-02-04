<%@page import="DAO.UserCommentsDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, DTO.Book, DAO.UserCommentsDAO, DTO.Comment, DTO.UserComment" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Details</title>
<link rel="stylesheet" type="text/css" href="AdminPage.css">
</head>
<body>
<form action="${pageContext.request.contextPath}/UserHomeServlet" method="post">

<%
List<Book> bookList = new ArrayList<Book>();
bookList = (List<Book>) session.getAttribute("listofbooksAdmin");
Book selectedBook = new Book();
for(Book b : bookList){
	if(b.getBookId() == Integer.parseInt(request.getParameter("bid"))){
		System.out.println("BOOK UPDATE FOUND");
		selectedBook = b;
		break;
	}
}
String selectedBookId = selectedBook.getTitle();
System.out.println("Book Id in jsp:" +selectedBook.getPrice());
%>
<div class="book_container">
<div class = "book_image"><img style="align:left" src="<%=selectedBook.getBookImage()%>">
<table border="1" style="width:100px; margin-top:10px">
  <tr>
    <td style="text-align:center">Price</td>
    <td style="text-align:center"><%=selectedBook.getPrice()%></td>		
  </tr>
  <tr>
    <td style="text-align:center">Copies</td>
    <td style="text-align:center"><%=selectedBook.getBookCount()%></td>		
  </tr>
</table></div>
<div class="book_title"><%=selectedBookId %></div>

<br>
<div class="book_description">
<%=selectedBook.getDescription() %>
</div>

<!-- Place your code here! -->
<br>
<div>
<select name="combo_box">
  <option value="update_price">update price</option>
  <option value="update_copies">update copies</option>
</select>
<input type="number" step="any" name="newdata" style="width:25%; display:inline" placeholder="New price/copies">
<input type="submit" name="updatebook" text="submit" style="width:70px; display:inline; margin-left:10px">
</div>
</div>
</form>

<a href="${pageContext.request.contextPath}/AdminHome.jsp">
    <img src="home.png" style="position:absolute; height:30px; width:30px; top:30px; right:30px"></img>
</a>		
</body>
</html>