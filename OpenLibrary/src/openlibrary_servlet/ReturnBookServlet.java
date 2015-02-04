package openlibrary_servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.IssueReturnBookDAO;
import DTO.User;

public class ReturnBookServlet extends HttpServlet{
	/**
	 * Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		System.out.println("Entrypoint: ReturnBookServlet");
		
		if(req.getParameterValues("checkedBooks") != null){
			String[] checkedBooks = req.getParameterValues("checkedBooks");
			List<Integer> checkedBookIds = new ArrayList<Integer>();
			
			for(int i = 0; i < checkedBooks.length; i++){
				checkedBookIds.add(Integer.parseInt(checkedBooks[i]));
			}
			
			User loggedInUser = (User) session.getAttribute("loggedInUser");
			IssueReturnBookDAO returnBooks = new IssueReturnBookDAO();
			returnBooks.returnBooks(loggedInUser, checkedBookIds);
			
			System.out.println("The selected books have been returned");
			
			req.getRequestDispatcher("UserHome.jsp").forward(req, resp);
		}
		
	}	

}
