package DAO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import DTO.Author;
import DTO.Book;
import DTO.User;
import DTO.UserBookIssue;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//AnnotationConfiguration config = new AnnotationConfiguration();
		//config.configure("hibernate.cfg.xml");
		
		//new SchemaExport(config).create(true, true);
		/*
		SessionFactory sessionFactory= config.buildSessionFactory();
		Session session1 = sessionFactory.openSession();
		Query q = null;
		q = session1.createQuery("select b from Book b where b.author: author");
		Author author = new Author();
		*/
		/*q = session1.createQuery("select u from UserBookIssue u");
		List<UserBookIssue> users = new ArrayList<UserBookIssue>();*/
		//users = (List<UserBookIssue>) q.list();	
		//System.out.println("users are:" +q.list().size());
		//session1.close();
		/*Book b = new Book();
		b.setBookId(10572);
		
		User u = new User();
		u.setId(1);
		
		//DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		
		UserBookIssue userBookIssue = new UserBookIssue();
		userBookIssue.setBook(b);
		userBookIssue.setUser(u);
		userBookIssue.setIssueDate(date);
		userBookIssue.setUserBill(10);
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(userBookIssue);
		session.getTransaction().commit();
		session.close();
*/
		
		List<Integer> books = null;
		books.add(1);
		books.add(2);
	}

}
