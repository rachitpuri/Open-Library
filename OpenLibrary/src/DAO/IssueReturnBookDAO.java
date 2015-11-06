package DAO;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import DTO.Book;
import DTO.User;
import DTO.UserBookIssue;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class IssueReturnBookDAO {
	
SessionFactory sessionFactory;
	
	public IssueReturnBookDAO(){
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.configure("hibernate.cfg.xml");
		
		sessionFactory= config.buildSessionFactory();
	}
	
	public Boolean canBeIssued(User user, Book book){
		Session session = sessionFactory.openSession();
		//Check if the book is already issued to the user
		Query q = session.createQuery("select ub from UserBookIssue ub "
									+ "where ub.userBookIssueId.user = :user "
									+ "and ub.userBookIssueId.book = :book "
									+ "and ub.returnDate is null");
		q.setParameter("user", user);
		q.setParameter("book", book);
		if(q.list().isEmpty()){
			//Check if user exceeds the limit of  no. of books per user at a time
			Query q2 = session.createQuery("select u from UserBookIssue u where u.userBookIssueId.user = :user and returnDate is null");
			q2.setParameter("user", user);
			if(q2.list().size() < 3){
				if(book.getBookCount() > 0)
					return true;
				else
					return false;
			} 
			else
				return false;
		} 
		else
			return false;
	}
	
	public void issueBook(User user, Book book){
		UserBookIssue userBookIssue = new UserBookIssue();
		userBookIssue.setUser(user);
		userBookIssue.setBook(book);
		userBookIssue.setIssueDate(new Date());
		
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		session.save(userBookIssue);
		
		Query q = session.createQuery("select b from Book b where b.bookId = :bookId");
		q.setParameter("bookId", book.getBookId());
		Book updateBook = (Book) q.list().get(0);
		
		updateBook.setBookCount(updateBook.getBookCount() - 1);
		session.update(updateBook);
		session.getTransaction().commit();
		
		session.close();
		
	}
	
	public List<UserBookIssue> getUserIssuedBooks(User user){
		Session session = sessionFactory.openSession();
		Query q = session.createQuery("select ub from UserBookIssue ub where ub.userBookIssueId.user = :user and ub.returnDate is null");
		q.setParameter("user", user);
		@SuppressWarnings("unchecked")
		List<UserBookIssue> userBooks = (List<UserBookIssue>) q.list();
		return userBooks;
	}
	
	public void returnBooks(User user, List<Integer> bookIds){
		Session session = sessionFactory.openSession();
		
		Query q1 = session.createQuery("select b from Book b where b.bookId in (:bookList)");
		q1.setParameterList("bookList", bookIds);
		@SuppressWarnings("unchecked")
		List<Book> selectedBooks = (List<Book>) q1.list();
		
		Query q2 = session.createQuery("select ub from UserBookIssue ub "
									+ "where ub.userBookIssueId.user = :user and ub.userBookIssueId.book in (:bookList)");
		q2.setParameter("user", user);
		q2.setParameterList("bookList", selectedBooks);
		@SuppressWarnings("unchecked")
		List<UserBookIssue> booksToReturn = (List<UserBookIssue>) q2.list();
		
		session.beginTransaction();
		for(Book b : selectedBooks){
			b.setBookCount(b.getBookCount() + 1);
			session.update(b);
		}
		
		for(UserBookIssue ub : booksToReturn){
			Date returnDate = new Date();
			ub.setReturnDate(returnDate);
			
			Date issueDate = ub.getIssueDate();
			int daysBetween = Days.daysBetween(new DateTime(issueDate), new DateTime(returnDate)).getDays();
			double userBill = 0.0;
			for(Book b : selectedBooks){
				if(b.getBookId() == ub.getBook().getBookId()){
					if(daysBetween == 0)
						userBill = b.getPrice();
					else
						userBill = b.getPrice() * daysBetween;
					break;
				}
			}
			ub.setUserBill(userBill);
			session.update(ub);
		}
		session.getTransaction().commit();
		session.close();
		
		//If a book was in reservation status, then send mail to the users who reserved it
		for(Book b : selectedBooks){
			if(b.getBookCount() == 1)
				getReservationsAndSendMail(b);
		}
	}
	
	public void getReservationsAndSendMail(Book book){
		ReservationDAO reservationDAO = new ReservationDAO();
		List<User> reservationUsers = reservationDAO.getReservationUsers(book);
		
		if(!reservationUsers.isEmpty()){
			for(User user : reservationUsers){
				System.out.println("Sending mail to " + user.getEmail() + "...");
				sendMail(user, book);
			}
		}
		
	}
	
	public void sendMail(User mailUser, Book book){  
		String host = "smtp.gmail.com";  
		final String user = "team.open.library@gmail.com";//change accordingly
		final String password = "openlibrary999";//change accordingly  
		    
		String to = mailUser.getEmail();  
		System.out.println("Mailing at " + mailUser.getEmail());
		
		//Get the session object  
		Properties prop = new Properties();  
		prop.put("mail.smtp.host",host);  
		prop.put("mail.smtp.auth", "true");  
		     
		Properties props = new Properties();  
	    props.setProperty("mail.transport.protocol", "smtp");     
	    props.setProperty("mail.host", "smtp.gmail.com");  
	    props.put("mail.smtp.auth", "true");  
	    props.put("mail.smtp.port", "465");  
	    props.put("mail.debug", "true");  
	    props.put("mail.smtp.socketFactory.port", "465");  
	    props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
	    props.put("mail.smtp.socketFactory.fallback", "false");  
	    javax.mail.Session session = javax.mail.Session.getDefaultInstance
	    							(props, new javax.mail.Authenticator() {
	    										protected PasswordAuthentication getPasswordAuthentication() {  
	    													return new PasswordAuthentication(user,password);  
	    													}
	    										}
	    							);  

	    try{
		   Transport transport = session.getTransport("smtps");  
		   InternetAddress addressFrom = new InternetAddress(user);  

		   MimeMessage message = new MimeMessage(session);  
		   message.setSender(addressFrom);  
		   String msg = "Dear " + mailUser.getFirstName() + ","
		   				+ "\n\n" + "A copy of the book you reserved - " + book.getTitle() + " - is now available in the library."
		   				+ "\n\n If you want to borrow the book, you can login into your Open Library account and click on Issue button in the Book Details page."
		   				+ "\n\n Sincerely,"
		   				+ "\nOpen Library Team";
		   message.setSubject("Reservation notification for " + book.getTitle());  
		   message.setContent(msg, "text/plain");  
		   message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));  

		   transport.connect();  
		   Transport.send(message);  
		   transport.close();
	    } catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	
	public List<UserBookIssue> getUserIssueHistory(User user){
		Session session = sessionFactory.openSession();
		Query q = session.createQuery("select ub from UserBookIssue ub where ub.userBookIssueId.user = :user and ub.returnDate is not null");
		q.setParameter("user", user);
		@SuppressWarnings("unchecked")
		List<UserBookIssue> userBooks = (List<UserBookIssue>) q.list();
		return userBooks;
	}

}
