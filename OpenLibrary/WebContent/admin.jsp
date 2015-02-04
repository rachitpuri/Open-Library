<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="bootstrap.css">
<script src="JS/jquery-1.11.1.js"></script>

<title>Login</title>
</head>
<body background="login.jpg">

<form action="${pageContext.request.contextPath}/LoginServlet" method="post">
<center><h2 style="color:#006dcc">Open Library</h2></center>
<div class ="container" align="center">		
		<div class ="login" style="width:400px; height:340px; padding-top:200px; background:#E8E8E8; margin:20px">
		<div class="error" style="width:100%; height:20px; background:#E8E8E8; color:#FF0000;">
		<% if ( request.getAttribute( "errorMessage" ) != null ) { %>  
        <%=request.getAttribute( "errorMessage" )%>  
        <% } %> 
        </div>
		<input type="text" name="username" class="form-control" placeholder="Email/Username">
		<input type="password" name="password" class="form-control" placeholder="password">
		<br>
		<input type="submit" name="admin" value="Sign In" class="btn btn-block btn-primary">
		</div>
</div>

</form>

</body>
</html>