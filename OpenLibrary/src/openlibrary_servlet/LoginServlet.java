package openlibrary_servlet;

import java.io.IOException;
import java.io.PrintWriter;

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
		// TODO Auto-generated method stub
		//super.doPost(req, resp);
		System.out.println("Inside LoginServlet");
		if(req.getParameter("SignIn") != null){
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			
			UserDAO userDAO = new UserDAO();
			System.out.println("calling findByUsernameAndPassword");
			User user = userDAO.findByUsernameAndPassword(username, password);
		
		    //HttpSession session = req.getSession(false);
			HttpSession session = req.getSession();
			
			if(user != null){
				System.out.println("User is: " + user.getFirstName() + " " + user.getLastName() + "!");
				if(session != null){
					session.setAttribute("loggedInUser", user);
					String message = "Welcome "+username+" !";
					req.setAttribute("message", message);
					req.getRequestDispatcher("UserHome.jsp").forward(req, resp);
					//PrintWriter writer = resp.getWriter();
					//response.sendRedirect("welcome.jsp");
					//writer.println("Welcome " + user.getEmail());				
				}
			} else {
				System.out.println("User not found!");
				String  errorMessage = "The username or password you entered is incorrect.";
				//JOptionPane.showMessageDialog(null,errorMessage,"ERROR",JOptionPane.WARNING_MESSAGE);
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
			System.out.println("calling findByUsernameAndPassword for Admin");
			System.out.println("username is " +username);
			System.out.println("password is " +password);		
			Administrator admin = adminDAO.findByUsernameAndPassword(username, password);
			
			HttpSession session = req.getSession();
			
			if(admin != null){
				System.out.println("Admin is: " + admin.getFirstName() + " " + admin.getLastName() + "!");
				if(session != null){
					//session.setAttribute("loggedInUser", user);
					String message = "Welcome "+username+" !";
					req.setAttribute("message", message);
					req.getRequestDispatcher("AdminHome.jsp").forward(req, resp);
					session.invalidate();
					//PrintWriter writer = resp.getWriter();
					//response.sendRedirect("welcome.jsp");
					//writer.println("Welcome " + user.getEmail());				
				}
			} else {
				System.out.println("Admin not found!");
				String  errorMessage = "The username or password you entered is incorrect.";
				//JOptionPane.showMessageDialog(null,errorMessage,"ERROR",JOptionPane.WARNING_MESSAGE);
				req.setAttribute("errorMessage", errorMessage);
				req.getRequestDispatcher("login.jsp").forward(req, resp);
			}
		}
	}
}
