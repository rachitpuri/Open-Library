<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="DTO.User,DTO.Book, java.util.*, DTO.UserBookIssue" %>
<%@ page 	import="org.hibernate.Query,org.hibernate.Session,org.hibernate.SessionFactory,
					org.hibernate.cfg.AnnotationConfiguration,org.hibernate.tool.hbm2ddl.SchemaExport" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="bootstrap.css">
<script src="JS/jquery-1.11.1.js"></script>
<title>User Profile</title>
</head>
<body>
<% 
User user = (User) session.getAttribute("loggedInUser");
String userFullName = user.getFirstName() + " " + user.getLastName();	
List<UserBookIssue> booksIssued = new ArrayList<UserBookIssue>();
booksIssued = (List<UserBookIssue>) session.getAttribute("userIssuedBooks");
%>

<h3><%=userFullName %> - profile </h3>
<hr>

<%
if(booksIssued != null && booksIssued.size() > 0){
		%>
<h4>Books Issued</h4>
<form action="${pageContext.request.contextPath}/ReturnBookServlet">
<div style="width:50%">
<div style="width:75%; float:left">
<table style="border: 1px solid black; margin-left:10px; width:500px">
		<%
		for(UserBookIssue ub : booksIssued){
			%>
			<tr>
				<td> <input style="margin-left:5px" type="checkbox" name="checkedBooks" value="<%=ub.getBook().getBookId() %>"> <%=ub.getBook().getTitle() %> </td>				
			</tr>
			<%
		}
		%>
</table>
</div>
<%
if(booksIssued.size() > 0){
%>
<div style="width:25%; float:left">
<input type="submit" name="returnSubmit" value="Return Books" style="width:140px; margin-left:10px" class="btn btn-primary">
</div>
<%
}
%>
</div>
</form>
<%
}else
{
%>
<h4>Books Issued : 0</h4>
<%
}
%>
<div style="margin-top:100px">
<form action="${pageContext.request.contextPath}/UserBillServlet">
<br>
<input type="submit" name="userBillSubmit" value="View Your Bill" style="width:180px; margin-left:10px; float:left; left:10px" class="btn btn-primary">
</form>
</div>
<%
List<UserBookIssue> outstandingBillBooks = (List<UserBookIssue>) request.getAttribute("outstandingBillBooks");

if(outstandingBillBooks != null && outstandingBillBooks.size() > 0){
%>
<br>
<br>
<h4>Current Outstanding Bill</h4>
<table style="border: 1px solid black; margin-left:10px; width:500px">
	<%
	for(UserBookIssue ub : outstandingBillBooks){
	%>
		<tr style="padding:5px">
			<td> <%=ub.getBook().getTitle() %> </td>
			<td> <%=ub.getUserBill() %> </td>
		</tr>
	<%
	}
	%>
</table>
<%
}else
	{
%>
<br>
<br>
<h4>Current Outstanding Bill : 0</h4>
	<%	
	}
	%>

<%
List<UserBookIssue> issueHistory = (List<UserBookIssue>) request.getAttribute("issueHistory");
if(issueHistory != null){
%>
<br>
<h4>Previous Bills</h4>
<table style="border: 1px solid black; margin-left:10px; width:500px">
	<%
	for(UserBookIssue ub : issueHistory){
	%>
		<tr>
			<td style="margin-left:5px"> <%=ub.getBook().getTitle() %> </td>
			<td style="margin-right:5px; float:right;"> <%=ub.getUserBill() %> </td>
		</tr>
	<%
	}
%>
</table>
<%
}
%>


<a href="${pageContext.request.contextPath}/UserHome.jsp">
    <img src="home.png" style="position:absolute; height:30px; width:30px; top:30px; right:30px"></img>
</a>

</body>
</html>