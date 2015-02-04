package openlibrary_servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.IssueReturnBookDAO;
import DTO.Book;
import DTO.User;

public class IssueBookServlet extends HttpServlet{
	/**
	 * Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		System.out.println("Entrypoint: IssueBookServlet");
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		Book selectedBook = (Book) session.getAttribute("selectedBook");
		
		IssueReturnBookDAO issueBook = new IssueReturnBookDAO();
		String bookIssuedMessage = new String();
		if(issueBook.canBeIssued(loggedInUser, selectedBook)){
			issueBook.issueBook(loggedInUser, selectedBook);
			bookIssuedMessage = selectedBook.getTitle() + " has been issued to you.";
		}
		else{
			bookIssuedMessage = "Either " + selectedBook.getTitle() + " has already been issued to you or "
								+ "you exceed the limit of number of books issued/person";
		}
		System.out.println("Book Issued");
		
		//PrintWriter writer = resp.getWriter();
		//writer.println("Book Issued!");
		
		req.setAttribute("bookIssuedMessage", bookIssuedMessage);
		req.getRequestDispatcher("BookDetails.jsp?bid=" + selectedBook.getBookId()).forward(req, resp);
		
	}
	
	

}
