package openlibrary_servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.fabric.xmlrpc.base.Data;

import DAO.UserCommentsDAO;
import DTO.Comment;
import DTO.User;

public class UserCommentServlet extends HttpServlet{
	/**
	 * Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		System.out.println("Entrypoint: UserCommentServlet");
		
		if(req.getParameter("commentSubmit") != null){
			
			Comment comment = new Comment();
			comment.setComment(req.getParameter("comment"));
			
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			comment.setCommentTimestamp(timestamp);
			
			String name = (String) req.getParameter("user");
			System.out.println("name is "+name);
			if(name.length() < 1)
				comment.setUserName("Anonymous");
			else
				comment.setUserName(name);
			
			User loggedInUser = (User) session.getAttribute("loggedInUser");
			int userId = loggedInUser.getId();
			
			int bookId = (Integer.parseInt(req.getParameter("bookId")));
			
			UserCommentsDAO usercommentsDAO = new UserCommentsDAO();
			usercommentsDAO.insert(comment, userId, bookId);
			
			//PrintWriter writer = resp.getWriter();
			//writer.println("Book Id:" + bookId + "Comment: " + req.getParameter("comment"));
			System.out.println("Book Id:" + bookId + "Comment: " + req.getParameter("comment"));
			
			req.getRequestDispatcher("BookDetails.jsp?bid=" + bookId).forward(req, resp);
			
		}
		
	}
	
	

}
