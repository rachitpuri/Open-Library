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

import DTO.Author;
import DTO.Book;

public class AuthorSearchServlet extends HttpServlet{
	/**
	 * Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("In BookSearchServlet!");
		
		if(req.getParameter("authorSearch") != null){
			String authorSearchString = "%" + req.getParameter("searchString") + "%";
			
			AnnotationConfiguration config1 = new AnnotationConfiguration();
			config1.configure("hibernate.cfg.xml");

			SessionFactory sessionFactory = config1.buildSessionFactory();
			Session session1 = sessionFactory.openSession();

			Query q = session1.createQuery("select a from Author a where a.name like :searchString");
			q.setString("searchString", authorSearchString);
			
			List<Author> searchedAuthor = new ArrayList<Author>();
			searchedAuthor = (List<Author>) q.list();
			
			//PrintWriter writer = resp.getWriter();
			//writer.println("In Book Search servlet");
			
			HttpSession session = req.getSession();
			session.setAttribute("searchedAuthor", searchedAuthor);
			req.getRequestDispatcher("AuthorSearch.jsp").forward(req, resp);
			session.invalidate();
		}
		
	}

}
