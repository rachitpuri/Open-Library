package openlibrary_servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.Days;

import DAO.IssueReturnBookDAO;
import DTO.User;
import DTO.UserBookIssue;

public class UserBillServlet extends HttpServlet{
	/**
	 * Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		System.out.println("Entrypoint: UserBillServlet");
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
		IssueReturnBookDAO issuedBooks = new IssueReturnBookDAO();
		List<UserBookIssue> currentIssuedBooks = issuedBooks.getUserIssuedBooks(loggedInUser);
		
		for(UserBookIssue ub : currentIssuedBooks){
			Date currentDate = new Date();
			Date issueDate = ub.getIssueDate();
			int daysBetween = Days.daysBetween(new DateTime(issueDate), new DateTime(currentDate)).getDays();
			if(daysBetween == 0)
				ub.setUserBill(ub.getBook().getPrice());
			else
				ub.setUserBill(ub.getBook().getPrice() * daysBetween);
		}
		
		//for(UserBookIssue ub : currentIssuedBooks){
		//	System.out.println(ub.getUserBill());
		//}
		
		req.setAttribute("outstandingBillBooks", currentIssuedBooks);
		
		List<UserBookIssue> issueHistory = issuedBooks.getUserIssueHistory(loggedInUser);
		req.setAttribute("issueHistory", issueHistory);
		
		req.getRequestDispatcher("UserProfile.jsp").forward(req, resp);
		
	}
	
}
