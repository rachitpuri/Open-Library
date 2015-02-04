<%@page import="DAO.UserCommentsDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, DTO.Book, DAO.BookDAO, DTO.User, DTO.BookISBN, DAO.UserCommentsDAO, DTO.Comment, DTO.UserComment, DTO.Author" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Details</title>
<link rel="stylesheet" type="text/css" href="AdminPage.css">
</head>
<body>

<%
List<BookISBN> bookList = new ArrayList<BookISBN>();
if(session.getAttribute("listofbooks") != null)
bookList = (List<BookISBN>) session.getAttribute("listofbooks");

List<Book> books = new ArrayList<Book>();
if(session.getAttribute("listofAllbooks") != null){
books = (List<Book>) session.getAttribute("listofAllbooks");
System.out.println("books size: " +books.size());
}

List<Author> authors = new ArrayList<Author>();
authors = (List<Author>) session.getAttribute("listofauthors");
Collection<BookISBN> authorbooks = null;

int selectedBookId;
BookISBN selectedBook = new BookISBN();
Book bookfound = new Book();
BookISBN selectedBook1 = new BookISBN();

if(bookList != null && bookList.size() > 0){
	System.out.println("Inside booklist with size: " +bookList.size());
	for(BookISBN b : bookList){
		if(b.getBookId() == Integer.parseInt(request.getParameter("bid"))){
			System.out.println("Book found 111");
			selectedBook = b;
			break;
		}
	}
	selectedBookId = selectedBook.getBookId();
	System.out.println("selectedBook ID:" +selectedBook.getBookId());
	for(Book book : books){
		if(book.getBookId() == selectedBook.getBookId() ){
			System.out.println("BOOK FOUND");
			bookfound = book;
			System.out.println("Book Pub:" +bookfound.getPublisher());
			break;
		}
	}
	System.out.println("book found:" +bookfound.getTitle());
	session.setAttribute("selectedBook", bookfound);
}else{
	System.out.println("Inside else of booklist with auhor size:" +authors.size());
	for(Author a : authors){
		System.out.println("Not going inside: ");
		authorbooks = a.getBooks();
		System.out.println("authorbooks size:" +authorbooks.size());
		for(BookISBN b : authorbooks){
			System.out.println("book id is : " +b.getBookId());
			if(b.getBookId() == Integer.parseInt(request.getParameter("bid"))){
				selectedBook1 = b;
				System.out.println("Match Found");
				break;
			}
		}
	}
	selectedBookId = selectedBook1.getBookId();
	BookDAO bookDAO = new BookDAO();
	bookfound = bookDAO.getBook(selectedBook1.getBookId());
	session.setAttribute("selectedBook", bookfound);
}
if(bookList != null && bookList.size() > 0){
System.out.println("Inside Booklist");
%>
<div class="book_container">
<div class = "book_image"><img style="align:left" src="<%=selectedBook.getImageURL() %>">
<table border="1" style="width:100px; margin-top:10px">
  <tr>
    <td style="text-align:center">Price</td>
    <td style="text-align:center"><%=bookfound.getPrice() %></td>		
  </tr>
  <tr>
    <td style="text-align:center">Copies</td>
    <td style="text-align:center"><%=bookfound.getBookCount() %></td>		
  </tr>
</table>
</div>
<div class="right_container_book">
<div class="book_title"><%=selectedBook.getTitle() %></div>
<hr>
<div class="publisher" style="float:right"><b>Publisher - </b><%=bookfound.getPublisher()%>, <%=bookfound.getPublicationYr() %></div>
<br><br>
<div class="book_description" style="padding:5px">
<%=selectedBook.getDescription() %>
</div>
</div>
</div>
<% 
}else {
%>
<div class="book_container">
<div class = "book_image"><img style="align:left" src="<%=selectedBook1.getImageURL()%>">
<table border="1" style="width:100px; margin-top:10px">
  <tr>
    <td style="text-align:center">Price</td>
    <td style="text-align:center"><%=bookfound.getPrice() %></td>		
  </tr>
  <tr>
    <td style="text-align:center">Copies</td>
    <td style="text-align:center"><%=bookfound.getBookCount() %></td>		
  </tr>
</table>
</div>
<div class="right_container_book">
<div class="book_title"><%=selectedBook1.getTitle() %></div>
<hr>
<div class="publisher" style="float:right"><b>Publisher - </b><%=bookfound.getPublisher()%>, <%=bookfound.getPublicationYr() %></div>
<br><br>
<div class="book_description">
<%=selectedBook1.getDescription()%>
</div>
</div>
</div>
<%	
}
%>
<!-- Place your code here! -->

<form action="${pageContext.request.contextPath}/IssueBookServlet" method="post">
<br>
<input type="submit" name="issueBookSubmit" value="Issue Book">
<br>
<%
if(request.getAttribute("bookIssuedMessage") != null){
	%>
	<label for="issueMessage"> 
	<% if ( request.getAttribute( "bookIssuedMessage" ) != null ) { %>  
        <%=request.getAttribute("bookIssuedMessage")%>  
        <% } %>
	</label>
	<%
}
%>
</form>

<form action="${pageContext.request.contextPath}/ReserveBookServlet" method="post">
<br>
<input type="submit" name="reserveBookSubmit" value="Reserve Book">
<%
if(request.getAttribute("bookReservedMessage") != null){
	%>
	<label for="reserveMessage"> 
	<% if ( request.getAttribute( "bookReservedMessage" ) != null ) { %>  
        <%=request.getAttribute( "bookReservedMessage" )%>  
        <% } %>
	</label>
	<%
}
%>
</form>

<form action="${pageContext.request.contextPath}/UserCommentServlet" method="post">
<h4>Leave a Review/Comment</h4>
<label for="user">Name <span class="required">*</span></label> 
<br>
<input id="user" name="user" type="text" size="30" aria-required='true' />
<br><br>
<label for="comment">Review/Comment</label>
<br> 
<textarea id="comment" name="comment" cols="45" rows="8" aria-required="true"></textarea>
<br><br>
<input type="submit" name="commentSubmit" id="submit" value="Post Review/Comment" />

<input type="hidden" name="bookId" value="<%=selectedBookId%>"/>

</form>
</div>

<a href="${pageContext.request.contextPath}/UserHome.jsp">
    <img src="home.png" style="position:absolute; height:30px; width:30px; top:30px; right:30px"></img>
</a>

<h4>Previous Comments</h4><br>
<table style="width:100%">
<%		System.out.println("Lets fetch comments");
		UserCommentsDAO userCommentsDAO = new UserCommentsDAO();
		//User loggedInUser = (User) session.getAttribute("loggedInUser");
		//List<User> users = new ArrayList<User>();
		//users = userCommentsDAO.getUsers(selectedBookId);
		//System.out.println("users size: "+users.size());
		List<Integer> bookComments = userCommentsDAO.getBookComments(selectedBookId);
		List<Comment> comments = new ArrayList<Comment>();
		for(int uc : bookComments){
			Comment comment = userCommentsDAO.getComment(uc);
			comments.add(comment);
		}
		System.out.println("comments size: "+comments.size());
		for(Comment c : comments){
			%>	
			<tr>
				<% if(c.getUserName() != ""){
					%> <td style="width:12%"> <%=c.getUserName() %> </td>
				<% 
				}
				%>
				<td style="width:60%"><%=c.getComment() %></td>
				<td style="width:28%; float:right"><%=c.getCommentTimestamp() %></td>
			</tr>
			<%
		}
	%>

</table>

</body>
</html>