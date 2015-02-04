<%@page import="DAO.UserCommentsDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, DTO.Book, DTO.BookISBN, DAO.UserCommentsDAO, DTO.Comment, DTO.UserComment, DTO.Author" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Details</title>
<link rel="stylesheet" type="text/css" href="AdminPage.css">
</head>
<body>

<%
System.out.println("Inside author details");
List<Author> authors = new ArrayList<Author>();
authors = (List<Author>) session.getAttribute("AuthorDetail");
System.out.println("authors size: "+authors.size());
Author author = new Author();

if(authors != null){
	for(Author a : authors){
		if(a.getId() == Integer.parseInt(request.getParameter("aid"))){
			author = a;
			System.out.println("found author with name: " +author.getName());
			break;
		}
	}
}
if(authors != null){
%>
<div class="book_container">
<div class = "author_image"><img style="align:left" src="<%=author.getImageURL() %>">
</div>
<div class ="right_container">
<div class="book_title"><%=author.getName() %></div>
<br>
<div class="born"><b style="margin-right:112px">Born </b><%=author.getHometown() %></div>
<div style="margin-left:155px"><%=author.getBornAt() %></div>
<div><b style="margin-right:95px">Gender </b><%=author.getGender() %></div>
<div><b style="margin-right:50px">Works Count </b><%=author.getWorksCount() %></div>
<br>

<div><b>About this Author</b></div>
<div style="margin-left:10px"> <%=author.getAbout() %></div>
</div>
</div>
<% 
}
%>

<a href="${pageContext.request.contextPath}/UserHome.jsp">
    <img src="home.png" style="position:absolute; height:30px; width:30px; top:30px; right:30px"></img>
</a>

</body>
</html>