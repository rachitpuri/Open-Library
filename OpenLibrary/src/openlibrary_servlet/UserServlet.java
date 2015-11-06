package openlibrary_servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.UserDAO;
import DTO.User;

public class UserServlet extends HttpServlet{
	/**
	 * Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		
		if(req.getParameter("signUpSubmit") != null){
			UserDAO userDAO = new UserDAO();
			List<User> allUsers = null;
		    try {
				allUsers = userDAO.getAllUsers();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    String username = req.getParameter("username");
		    String password = req.getParameter("password");
		    String firstName = req.getParameter("first_name");
		    String lastName = req.getParameter("last_name");
		    char gender = req.getParameter("gender").charAt(0);
		    String email = req.getParameter("email");
		    
		    boolean userExists = false;
		    
		    for(User u : allUsers){
		    	if(u.getUsername().equals(username)){
		    		userExists = true;
		    		String message = "The username has already been taken!";
		    		req.setAttribute("errorUser", message);
		    		req.getRequestDispatcher("signup.jsp?message=" +URLEncoder.encode(message, "UTF-8")).forward(req, resp);
		    		break;
		    	}
		    }    
		    if(!userExists){
		    	User newUser = new User();	
		    	newUser.setUsername(username);
		    	newUser.setPassword(password);
		    	newUser.setFirstName(firstName);
		    	newUser.setLastName(lastName);
		    	newUser.setGender(gender);
		    	newUser.setEmail(email);
		    	userDAO.insert(newUser);
		    	
		    	sendMail(newUser);
		    	req.setAttribute("message", "Account created. Please login with your credentials.");
		    	req.getRequestDispatcher("login.jsp").forward(req, resp);
		    }
		}
	}
	
	public void sendMail(User mailUser){  
		String host="smtp.gmail.com";  
		final String user = "team.open.library@gmail.com";
		final String password = "openlibrary999";  
		    
		String to= mailUser.getEmail();  
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
		   Transport transport = session.getTransport();  
		   InternetAddress addressFrom = new InternetAddress(user);  
	
		   MimeMessage message = new MimeMessage(session);  
		   message.setSender(addressFrom);  
		   String msg = "Dear " + mailUser.getFirstName()
				   		+ ",\n\n" 
				   		+ "Your account has been created in the system."
				   		+ "\n\n "
				   		+ "We look forward for you to explore the world of books, enjoy reviewing them and borrow the books you want to read. "
				   		+ "\n\n"
				   		+ "Regards,"
				   		+ "Open Library Team";
		   message.setSubject("Welcome to the Open Library family!");  
		   message.setContent(msg, "text/plain");  
		   message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));  
	
		   transport.connect();  
		   Transport.send(message);  
		   transport.close();
	   } catch(Exception e){
	    	e.printStackTrace();	    	
	   }
	}
}
