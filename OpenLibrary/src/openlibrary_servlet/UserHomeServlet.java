package openlibrary_servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.AdministratorDAO;
import DAO.BookDAO;
import DAO.UserDAO;
import DTO.Administrator;
import DTO.Author;
import DTO.Book;
import DTO.BookISBN;
import DTO.Issue;
import DTO.User;
import DTO.UserBookIssue;

//@WebServlet(description = "Login Servlet", urlPatterns = { "/LoginServlet" })
public class UserHomeServlet extends HttpServlet{
	/**
	 * Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doPost(req, resp);
		System.out.println("Inside UserHomeServlet");
		List<Book> alladminbooks = null;
		List<Book> allbooks = null;
		List<BookISBN> allISBNbooks = null;
		List<UserBookIssue> allusers = null;
		List<Author> authors = null;
		List<Author> authorslist = null;
		List<Author> booksAuthors = null;
		String searchbook = null;
		double revenue;
		if(req.getParameter("searchBooks") != null){
			String option = null;
			option = req.getParameter("user_box");
			searchbook = req.getParameter("search");
			System.out.println("calling getAllBooks");
			HttpSession session = req.getSession();   
		    try {
		    	if(option.matches("find_book")){
		    		System.out.println("Inside find by book ....");
		    		BookDAO bookDAO = new BookDAO();
		    		allISBNbooks = bookDAO.getAllBooks(searchbook);
		    		authorslist = bookDAO.getCompleteAuthorsList(searchbook);
		    		allbooks = bookDAO.getAllAdminBooks(searchbook);
		    		//authors = null;
		    		//authorslist = null;
		    		//booksAuthors = bookDAO.getBooksAuthors(allbooks);
		    	}else {
		    		System.out.println("Inside find by author ....");
		    		BookDAO bookDAO = new BookDAO();
		    		authors = bookDAO.getAllAuthors(searchbook);
		    		//allISBNbooks = null;
		    		//allbooks = null;
		    		authorslist = bookDAO.getCompleteAuthorsList(searchbook);
		    		//req.setAttribute("findauthor", "puri");
		    		
		    		System.out.println("authors size: "+authors.size());
		    	}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    System.out.println("Books are:");
		    session.setAttribute("listofbooks", allISBNbooks);
    		session.setAttribute("listofAllbooks", allbooks);
		    session.setAttribute("listofauthors", authors);
		    session.setAttribute("AuthorDetail", authorslist);
		    //System.out.println("allbooks size::" +allbooks.size());
		    req.getRequestDispatcher("/UserHome.jsp").forward(req, resp);
		    //session.invalidate();
			
		} else if (req.getParameter("signUp") != null) {
			req.getRequestDispatcher("signup.jsp").forward(req, resp);
		} else if(req.getParameter("adminbook") != null){
			System.out.println("Inside Admin");
			String searcheditem = null;
			String option = null;
			String count = null;
			UserDAO userobj = new UserDAO();
			User user = new User();
			user = userobj.findByUsername(req.getParameter("search"));
			option = req.getParameter("admin_box");
    		BookDAO bookDAO = new BookDAO();
    		UserDAO userDAO = new UserDAO();
    		try {
    			if(option.matches("search_books")){
    				System.out.println("inside search books admin");
    				searcheditem = req.getParameter("search");
    				alladminbooks = bookDAO.getAllAdminBooks(searcheditem);
    				if(allusers != null)
    					allusers.removeAll(allusers);
    			}
    			else{
    				System.out.println("inside search users admin");
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
			System.out.println("Inside Update Book ADMIN");
			String newdata = null;
			String option = null;
			String bookid = null;
			bookid = req.getParameter("updatebook");
			option = req.getParameter("combo_box");
			newdata = req.getParameter("newdata");
			if(newdata.length() == 0){
				System.out.println("calling back BookDetailsAdmin newdata empty");
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
    		System.out.println("calling back BookDetailsAdmin");
    		req.getRequestDispatcher("/BookDetailsAdmin.jsp?bid=" + bookid).forward(req, resp);
    	}else if(req.getParameter("revenue") != null){
    		System.out.println("Inside revenue");
    		UserDAO userobj = new UserDAO();
    		revenue = userobj.getRevenue();
    		HttpSession session = req.getSession();
    		session.setAttribute("revenue", revenue);
    		System.out.println("calling back AdminHome from revenue");
    		req.getRequestDispatcher("/AdminHome.jsp").forward(req, resp);
    	} 
		
	}
}
