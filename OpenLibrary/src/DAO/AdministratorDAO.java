package DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import DTO.Administrator;

public class AdministratorDAO {

	SessionFactory sessionFactory;
	
	public AdministratorDAO(){
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.configure("hibernate.cfg.xml");
		
		//new SchemaExport(config).create(true, true);
		
		sessionFactory= config.buildSessionFactory();
	}
	
	public List<Administrator> getAllAdmin() throws Exception{
		Session session = sessionFactory.openSession();
		Query q = session.createQuery("from User u");
		//List<User> users = q.list();
		return q.list();
	}
	
	public Administrator findByUsernameAndPassword(String username, String password){
		Session session = sessionFactory.openSession();
		//session.beginTransaction();
		String queryString = "select u from Administrator u where u.username=:username and u.password =:password";
		Query q = session.createQuery(queryString);
		q.setString("username", username);
		q.setString("password", password);
		List<Administrator> admin = (List<Administrator>) q.list();
		System.out.println("query executed");
		if(admin.size() > 0)
			return admin.get(0);
		else{
			System.out.println("admin NULL");
			return null;
		}
	}
}
