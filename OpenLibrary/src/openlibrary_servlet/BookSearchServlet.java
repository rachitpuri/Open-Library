package openlibrary_servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import DTO.Book;

public class BookSearchServlet extends HttpServlet{
	/**
	 * Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if(req.getParameter("bookSearch") != null){
			String bookSearchString = "%" + req.getParameter("searchString") + "%";
			
			AnnotationConfiguration config1 = new AnnotationConfiguration();
			config1.configure("hibernate.cfg.xml");

			SessionFactory sessionFactory = config1.buildSessionFactory();
			Session session1 = sessionFactory.openSession();

			Query q = session1.createQuery("select b from Book b where b.title like :searchString");
			q.setString("searchString", bookSearchString);
			
			List<Book> searchedBooks = new ArrayList<Book>();
			searchedBooks = (List<Book>) q.list();
			
			HttpSession session = req.getSession();
			session.setAttribute("searchedBooks", searchedBooks);
			req.getRequestDispatcher("BookSearch.jsp").forward(req, resp);
			session.invalidate();
		}
	}
}
