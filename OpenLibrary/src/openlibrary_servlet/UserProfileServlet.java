package openlibrary_servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.IssueReturnBookDAO;
import DTO.User;
import DTO.UserBookIssue;

@SuppressWarnings("serial")
public class UserProfileServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		
		IssueReturnBookDAO userBooks = new IssueReturnBookDAO();
		User loggedInUser = (User) session.getAttribute("loggedInUser"); 
		
		List<UserBookIssue> booksIssued = userBooks.getUserIssuedBooks(loggedInUser);
		session.setAttribute("userIssuedBooks", booksIssued);
		
		req.getRequestDispatcher("UserProfile.jsp").forward(req, resp);
	}
}
