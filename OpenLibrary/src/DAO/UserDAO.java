package DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import DTO.Book;
import DTO.User;
import DTO.UserBookIssue;

public class UserDAO {

	SessionFactory sessionFactory;
	
	public UserDAO(){
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.configure("hibernate.cfg.xml");
		
		//new SchemaExport(config).create(true, true);
		
		sessionFactory= config.buildSessionFactory();
	}
	
	public List<User> getAllUsers() throws Exception{
		Session session = sessionFactory.openSession();
		
		Query q = session.createQuery("from User u");
		//List<User> users = q.list();
		return q.list();
		
	}
	
	public String getCount(String username) throws Exception{
		Session session1 = sessionFactory.openSession();
		Query q = null;
		//String searchbook = "%" + bookSearched + "%";
		if(username.isEmpty())
			q = session1.createQuery("select (count distinct u.username) from User u, Book b" +
					"Issue i where i.bookid = b.bookId");
		else{
			q = session1.createQuery("select (count distinct u.username) from User u, Book b" +
					"Issue i where u.username = :username and i.bookid = b.bookId");
			q.setString("searchString", username);
		}
		return String.valueOf(q.list().size());
	}
	
	public List<UserBookIssue> getAllUsers(User userobj) throws Exception{
		Session session1 = sessionFactory.openSession();
		Query q = null;
		if(userobj == null)
			q = session1.createQuery("select u from UserBookIssue u where u.returnDate is null");
		else{
			q = session1.createQuery("select u from UserBookIssue u " +
						"where u.userBookIssueId.user = :user and u.returnDate is null");
			q.setParameter("user", userobj);
		}
		List<UserBookIssue> users = new ArrayList<UserBookIssue>();
		users = (List<UserBookIssue>) q.list();	
		System.out.println("users count :" +q.list().size());
		return users;
	}
	
	public List<User> getCommentUsers(List<Integer> listUids) throws Exception{
		Session session1 = sessionFactory.openSession();
		Query q = null;
		q = session1.createQuery("select u from User u where u.id in (:uids)");
		q.setParameterList("uids", listUids);
		List<User> users = new ArrayList<User>();
		users = (List<User>) q.list();	
		System.out.println("users count in getCommentUsers:" +q.list().size());
		return users;
	}
	
	public void insert(User user){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
		session.close();
	}
	
	public double getRevenue(){
		Session session = sessionFactory.openSession();
		Query q = session.createQuery("select sum(i.bill) from Issue i");
		//session.close();
		List<Double> value = q.list();
		return value.get(0);
	}
	
	public User findByUsernameAndPassword(String username, String password){
		Session session = sessionFactory.openSession();
		//session.beginTransaction();
		String queryString = "select u from User u where u.username=:username and u.password =:password";
		Query q = session.createQuery(queryString);
		q.setString("username", username);
		q.setString("password", password);
		List<User> users = (List<User>) q.list();
		System.out.println("query executed");
		if(users.size() > 0)
			return users.get(0);
		else
			return null;	
	}
	
	public User findByUsername(String username){
		Session session = sessionFactory.openSession();
		//session.beginTransaction();
		String queryString = "select u from User u where u.username=:username";
		Query q = session.createQuery(queryString);
		q.setString("username", username);
		List<User> users = (List<User>) q.list();
		System.out.println("query executed");
		if(users.size() > 0)
			return users.get(0);
		else
			return null;	
	}
}
