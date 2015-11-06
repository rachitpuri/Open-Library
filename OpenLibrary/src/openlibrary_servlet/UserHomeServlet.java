package openlibrary_servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.BookDAO;
import DAO.UserDAO;
import DTO.Author;
import DTO.Book;
import DTO.BookISBN;
import DTO.User;
import DTO.UserBookIssue;

//@WebServlet(description = "Login Servlet", urlPatterns = { "/LoginServlet" })
public class UserHomeServlet extends HttpServlet{
	/**
	 * Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "unused", "null" })
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Book> alladminbooks = null;
		List<Book> allbooks = null;
		List<BookISBN> allISBNbooks = null;
		List<UserBookIssue> allusers = null;
		List<Author> authors = null;
		List<Author> authorslist = null;
		String searchbook = null;
		double revenue;
		if(req.getParameter("searchBooks") != null){
			String option = null;
			option = req.getParameter("user_box");
			searchbook = req.getParameter("search");
			HttpSession session = req.getSession();   
		    try {
		    	if(option.matches("find_book")){
		    		BookDAO bookDAO = new BookDAO();
		    		allISBNbooks = bookDAO.getAllBooks(searchbook);
		    		authorslist = bookDAO.getCompleteAuthorsList(searchbook);
		    		allbooks = bookDAO.getAllAdminBooks(searchbook);
		    	}else {
		    		BookDAO bookDAO = new BookDAO();
		    		authors = bookDAO.getAllAuthors(searchbook);
		    		authorslist = bookDAO.getCompleteAuthorsList(searchbook);
		    	}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    session.setAttribute("listofbooks", allISBNbooks);
    		session.setAttribute("listofAllbooks", allbooks);
		    session.setAttribute("listofauthors", authors);
		    session.setAttribute("AuthorDetail", authorslist);
		    req.getRequestDispatcher("/UserHome.jsp").forward(req, resp);	
		} else if (req.getParameter("signUp") != null) {
			req.getRequestDispatcher("signup.jsp").forward(req, resp);
		} else if(req.getParameter("adminbook") != null){
			String searcheditem = null;
			String option = null;
			UserDAO userobj = new UserDAO();
			User user = new User();
			user = userobj.findByUsername(req.getParameter("search"));
			option = req.getParameter("admin_box");
    		BookDAO bookDAO = new BookDAO();
    		UserDAO userDAO = new UserDAO();
    		try {
    			if(option.matches("search_books")){
    				searcheditem = req.getParameter("search");
    				alladminbooks = bookDAO.getAllAdminBooks(searcheditem);
    				if(allusers != null)
    					allusers.removeAll(allusers);
    			}
    			else{
    				allusers = userDAO.getAllUsers(user);
    				if(!alladminbooks.isEmpty())	
    					alladminbooks.removeAll(allISBNbooks);
    			}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		 HttpSession session = req.getSession();
 		     session.setAttribute("listofbooksAdmin", alladminbooks);
    		 session.setAttribute("listofusers", allusers);
 		     req.getRequestDispatcher("/AdminHome.jsp").forward(req, resp);
    	}else if(req.getParameter("updatebook") != null){
			String newdata = null;
			String option = null;
			String bookid = null;
			bookid = req.getParameter("updatebook");
			option = req.getParameter("combo_box");
			newdata = req.getParameter("newdata");
			if(newdata.length() == 0){
	    		req.getRequestDispatcher("/BookDetailsAdmin.jsp?bid=" + bookid).forward(req, resp);
	    		return;
			}
				
    		BookDAO bookDAO = new BookDAO();
    		try {
				bookDAO.updateBook(bookid, option, newdata);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		try {
    			alladminbooks = bookDAO.getAllAdminBooks("");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		HttpSession session = req.getSession();
    		session.setAttribute("listofbooksAdmin", alladminbooks);
    		req.getRequestDispatcher("/BookDetailsAdmin.jsp?bid=" + bookid).forward(req, resp);
    	}else if(req.getParameter("revenue") != null){
    		UserDAO userobj = new UserDAO();
    		revenue = userobj.getRevenue();
    		HttpSession session = req.getSession();
    		session.setAttribute("revenue", revenue);
    		req.getRequestDispatcher("/AdminHome.jsp").forward(req, resp);
    	} 	
	}
}
