package openlibrary_servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.ReservationDAO;
import DAO.UserDAO;
import DTO.Book;
import DTO.User;

public class ReserveBookServlet extends HttpServlet{
	/**
	 * Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		System.out.println("Entrypoint: ReserveBookServlet");
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		Book selectedBook = (Book) session.getAttribute("selectedBook");
		
		ReservationDAO reservationDAO = new ReservationDAO();
		String bookReservedMessage = new String();
		if(reservationDAO.canBeReserved(loggedInUser, selectedBook)){
			reservationDAO.reserveBook(loggedInUser, selectedBook);
			bookReservedMessage = selectedBook.getTitle() + "has been reserved. You'll get a mail when the book becomes available.";
		} else
			bookReservedMessage = "You already have a reservation for this book.";
		
		System.out.println(bookReservedMessage);
		req.setAttribute("bookReservedMessage", bookReservedMessage);
		req.getRequestDispatcher("BookDetails.jsp?bid=" + selectedBook.getBookId()).forward(req, resp);
		
		//PrintWriter writer = resp.getWriter();
		//writer.println("Book Reserved!");
	}
	
	

}
