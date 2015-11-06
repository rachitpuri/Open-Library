package openlibrary_servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.AdministratorDAO;
import DAO.UserDAO;
import DTO.Administrator;
import DTO.User;

//@WebServlet(description = "Login Servlet", urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet{
	/**
	 * Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if(req.getParameter("SignIn") != null){
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			
			UserDAO userDAO = new UserDAO();
			User user = userDAO.findByUsernameAndPassword(username, password);
		
		    //HttpSession session = req.getSession(false);
			HttpSession session = req.getSession();
			
			if(user != null){
				if(session != null){
					session.setAttribute("loggedInUser", user);
					String message = "Welcome "+username+" !";
					req.setAttribute("message", message);
					req.getRequestDispatcher("UserHome.jsp").forward(req, resp);
				}
			} else {
				String  errorMessage = "The username or password you entered is incorrect.";
				req.setAttribute("errorMessage", errorMessage);
				req.getRequestDispatcher("login.jsp").forward(req, resp);
			}	
		} else if (req.getParameter("signUp") != null) {
			req.getRequestDispatcher("signup.jsp").forward(req, resp);
		} 
		else if (req.getParameter("admin") != null) {
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			
			AdministratorDAO adminDAO = new AdministratorDAO();
			Administrator admin = adminDAO.findByUsernameAndPassword(username, password);
			
			HttpSession session = req.getSession();
			
			if(admin != null){
				if(session != null){
					String message = "Welcome "+username+" !";
					req.setAttribute("message", message);
					req.getRequestDispatcher("AdminHome.jsp").forward(req, resp);
					session.invalidate();
				}
			} else {
				String  errorMessage = "The username or password you entered is incorrect.";
				req.setAttribute("errorMessage", errorMessage);
				req.getRequestDispatcher("login.jsp").forward(req, resp);
			}
		}
	}
}
