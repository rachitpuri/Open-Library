package DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import DTO.Author;
import DTO.Book;
import DTO.BookISBN;

public class BookDAO {

	static SessionFactory sessionFactory;
	
	public BookDAO(){
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.configure("hibernate.cfg.xml");
		sessionFactory= config.buildSessionFactory();
	}
		
	public void updateBook (String bookid, String option, String newdata) throws Exception{
		Session session1 = sessionFactory.openSession();
		//Session session = HibernateUtil.getSessionFactory().openSession();
		session1.getTransaction().begin();
		String query;
		if(option.matches("update_price"))
			query = "update Book b set b.price = :price where b.bookId = :bookid";
		else
			query = "update Book b set b.bookCount = :count where b.bookId = :bookid";
		Query q = session1.createQuery(query);
		
		q.setString("bookid", bookid);
		if(option.matches("update_price"))
			q.setString("price", newdata);
		else
			q.setString("count", newdata);
		
		q.executeUpdate();
		session1.getTransaction().commit();
		session1.close();
		System.out.println("DB copies updated");
	}
		
	@SuppressWarnings("unchecked")
	public List<Book> getAllAdminBooks(String bookSearched) throws Exception{
		Session session1 = sessionFactory.openSession();
		Query q = null;
		if(bookSearched.isEmpty()){
			System.out.println("No search string");
			q = session1.createQuery("select b from Book b");
		}
		else{
			q = session1.createQuery("select b from Book b where b.title like :searchString");
			q.setString("searchString", "%" + bookSearched + "%");
		}
		List<Book> books = new ArrayList<Book>();
		books = (List<Book>) q.list();	
		return books;
	}
	
	@SuppressWarnings("unchecked")
	public List<BookISBN> getAllBooks(String bookSearched) throws Exception{
		Session session1 = sessionFactory.openSession();
		Query q = null;
		if(bookSearched.isEmpty())
			q = session1.createQuery("select b from BookISBN b");
		else{
			q = session1.createQuery("select b from BookISBN b where b.title like :searchString");
			q.setString("searchString", "%" + bookSearched + "%");
		}
		List<BookISBN> books = new ArrayList<BookISBN>();
		books = (List<BookISBN>) q.list();	
		return books;
	}
	
	public Book getBook(int bookid) throws Exception{
		Session session1 = sessionFactory.openSession();
		Query q = null;
		q = session1.createQuery("select b from Book b where b.id = :id");
		q.setString("id", "" +bookid);
		Book book = new Book();
		book = (Book) q.list().get(0);	
		return book;
	}
	
	@SuppressWarnings("unchecked")
	public List<Author> getBooksAuthors (List<Book> books) throws Exception{
		Session session1 = sessionFactory.openSession();
		List<Author> authors = new ArrayList<Author>();
		List<Integer> authorIds = new ArrayList<Integer>();
		Query q = null;
		if(books.isEmpty()){
			authors = null;
			authorIds = null;
		}
		else{
			for(int i=0; i<books.size(); i++){
				authorIds.add(books.get(i).getAuthorId());
			}
			q = session1.createQuery("select a from Author a where a.id in (:bookslist)");
			q.setParameterList("bookslist", authorIds);
		}
		authors = (List<Author>) q.list();
		System.out.println("author's name size :" +authors.size());
		return authors;
	}
	
	@SuppressWarnings("unchecked")
	public List<Author> getCompleteAuthorsList(String author) throws Exception{
		Session session1 = sessionFactory.openSession();
		Query q = null;
		q = session1.createQuery("select a from Author a");
		List<Author> authors = new ArrayList<Author>();
		authors = (List<Author>) q.list();	
		return authors;
	}
	
	@SuppressWarnings("unchecked")
	public List<Author> getAllAuthors(String author) throws Exception{
		Session session1 = sessionFactory.openSession();
		Query q = null;
		if(author.isEmpty())
			q = session1.createQuery("select a from Author a");
		else{
			q = session1.createQuery("select a from Author a where a.name like :searchstring");
			q.setString("searchstring", "%" + author + "%");
		}
		List<Author> authors = new ArrayList<Author>();
		authors = (List<Author>) q.list();	
		return authors;
	}
}
