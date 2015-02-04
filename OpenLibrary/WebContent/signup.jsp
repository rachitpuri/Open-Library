<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="bootstrap.css">
<link href="registrationPage.css" type="text/css" rel="stylesheet"/>
<title>Sign Up</title>
<script type="text/javascript">
     function validate()
     {
    	 var formvalid = true;
         var fname = document.forms["userform"]["first_name"].value;
         var lname = document.userform.last_name.value;
         var usrname = document.userform.username.value;
         var passwrd = document.userform.password.value;
         var email = document.userform.email.value;
         var gender = document.userform.gender.value;
         var filter=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
         if(fname == null || fname == ""){
        	 alert("First name must be filled out");
        	 return false;
         }
         if(lname == null || lname == ""){
        	 alert("Last name must be filled out");
        	 return false;
         }
          if(usrname == null || usrname == ""){
        	 alert("Username must be filled out");
        	 return false;
         }
         if(usrname.length > 8){
        	 alert("Max username length is 8 characters");
        	 return false;
         }
         if(passwrd == null || passwrd == ""){
        	 alert("Password must be filled out");
        	 return false;
         }
         if(passwrd == usrname) {
             alert("Error: Password must be different from Username");
             return false;
           }
         if(email == null || email == "") {
             alert("Email must be filled out");
             return false;
           }
         if (!(filter.test(email))){
        	 alert("Please input a valid email address");
        	 return false;
         }
         if(gender == null || gender == "") {
             alert("Gender must be filled out");
             return false;
           }  
         return formvalid;
 }
function usercheck(){
	var msg = "";
	<% if (request.getAttribute("errorUser") != null) {%>
	 msg = "The username has already been taken!";
	<%}%>
	
	console.log("Msg : " + msg);
	if( msg != ""){
		alert(msg);
	}
}
     
     
</script>
</head>

<body onload="usercheck()">
<form name="userform" action="${pageContext.request.contextPath}/UserServlet" method="post" onsubmit="return validate()">

<div class="container mainDiv" style="width:50% position:center">
	<h1>Create your Library Account</h1>
	
<div class="registerForm" >
            <div class="errorMessageDiv"><p class="errormsg"></p></div>
            <label>Name :</label>
            <input type="text" name="first_name" class="form-control firstName" placeholder="First Name" />
            <input type="text" name="last_name" class="form-control lastName" placeholder="Last Name" />
            <label>User Name :</label>
            <input type="text" name="username" class="form-control username" placeholder="User name" />
            <label>Password :</label>
            <input type="password" name="password" class="form-control password" placeholder="Password" />
            <label>Email :</label>
            <input type="text" name ="email" class="form-control email" placeholder="Email" />
            <label>Gender :</label>
            <input type="text" name="gender" class="form-control phonenumber" placeholder="Gender" />
            <br>
            <div class="formButtons">
                <input type="submit" name="signUpSubmit" class="btn btn-primary registerAction createAccount" value="Create Account"/>
            </div>

        </div>

</form>
</body>
</html>