package DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import DTO.Comment;
import DTO.User;
import DTO.UserBookComment;
import DTO.UserComment;

public class UserCommentsDAO {
	
	SessionFactory sessionFactory;
	
	public UserCommentsDAO(){
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.configure("hibernate.cfg.xml");
		
		//new SchemaExport(config).create(true, true);
		
		sessionFactory= config.buildSessionFactory();
	}
	
	public void insert(Comment comment, int userId, int bookId){
		
		if(comment.getComment() != null){
			Session session = sessionFactory.openSession();
			
			//Insert into review_comments
			session.beginTransaction();
			session.save(comment);
			session.getTransaction().commit();
			
			//Insert into user_comments table
			Query q = session.createQuery("select c.id from Comment c order by c.commentTimestamp desc limit 1");
			
			List<Integer> commentIds = (List<Integer>) q.list();
			int commentId = commentIds.get(0);
			
			UserComment userComment = new UserComment();
			userComment.setUser(userId);
			userComment.setBook(bookId);
			//UserBookComment userBook = new UserBookComment();
			//userBook.setUser(userId);
			//userBook.setBook(bookId);
			//userComment.setUserBook(userBook);
			userComment.setComment(commentId);
			session.beginTransaction();
			session.save(userComment);
			session.getTransaction().commit();
			
			session.close();
			
		}
		
	}
	
	public List<Integer> getBookComments(int bookId){
		Session session1 = sessionFactory.openSession();
		//Query q1 = session1.createQuery("select uc from UserComment uc where uc.book = 10572");
		Query q2 = session1.createQuery("select uc.comment from UserComment uc where uc.book = " + bookId);
		//q2.setString("bookid", "%" + bookId + "%");
		List<Integer> cids = (List<Integer>) q2.list();
		for(int c : cids){
			System.out.println(c);
		}
		
		//List result = session1.createCriteria(UserComment.class).setFetchMode("comment", FetchMode.JOIN).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
				
		//List<UserComment> bookComments = (List<UserComment>) result;
		//for(UserComment uc : bookComments){
		//	System.out.println(uc.getComment());
		//}
		return cids;
	}
	
	public List<User> getUsers(int bookId) throws Exception{
		Session session1 = sessionFactory.openSession();
		//Query q1 = session1.createQuery("select uc from UserComment uc where uc.book = 10572");
		Query q2 = session1.createQuery("select uc.user from UserComment uc where uc.book = " + bookId);
		//q2.setString("bookid", "%" + bookId + "%");
		List<Integer> cids = (List<Integer>) q2.list();
		UserDAO userDAO = new UserDAO();
		List<User> user = new ArrayList<User>();
		if(cids.size() > 0)
			user = userDAO.getCommentUsers(cids);
		else
			return user;
		//List result = session1.createCriteria(UserComment.class).setFetchMode("comment", FetchMode.JOIN).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
				
		//List<UserComment> bookComments = (List<UserComment>) result;
		//for(UserComment uc : bookComments){
		//	System.out.println(uc.getComment());
		//}
		return user;
	}
	
	public Comment getComment(int commentId){
		Session session1 = sessionFactory.openSession();
		Query q = session1.createQuery("select c from Comment c where c.commentId = " + commentId);
		List<Comment> comments = (List<Comment>) q.list();
		Comment comment = comments.get(0);
		return comment;
		
	}
	

}
