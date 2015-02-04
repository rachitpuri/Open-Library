package DAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import DTO.Book;
import DTO.Reservation;
import DTO.User;

public class ReservationDAO {
	
	SessionFactory sessionFactory;
	
	public ReservationDAO(){
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.configure("hibernate.cfg.xml");
		
		sessionFactory= config.buildSessionFactory();
	}
	
	public Boolean canBeReserved(User user, Book book){
		System.out.println("Inside canBeReserved");
		Session session = sessionFactory.openSession();
		//check if the book is already reserved by the user
		Query q = session.createQuery("select r from Reservation r "
									+ "where r.reservationId.user = :user "
									+ "and r.reservationId.book = :book "
									+ "and r.reservationStatus = 1");
		q.setParameter("user", user);
		q.setParameter("book", book);
		
		if(q.list().isEmpty())
			return true;
		else
			return false;
	}
	
	public void reserveBook(User user, Book book){
		Reservation reservation = new Reservation();
		reservation.setUser(user);
		reservation.setBook(book);
		reservation.setReservationDate(new Date());
		reservation.setReservationStatus(1);
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(reservation);
		session.getTransaction().commit();
		session.close();
	}
	
	public List<User> getReservationUsers(Book book){
		Session session = sessionFactory.openSession();
		Query q = session.createQuery("select r from Reservation r where r.reservationId.book = :book and r.reservationStatus = :status");
		q.setParameter("book", book);
		q.setParameter("status", 1);
		
		List<Reservation> reservations = (List<Reservation>) q.list();
		List<User> reservationUsers = new ArrayList<User>();
		session.beginTransaction();
		for(Reservation r : reservations){
			reservationUsers.add(r.getUser());
			System.out.println("Updating reservations");
			r.setReservationStatus(0);
			session.update(r);
		}
		session.getTransaction().commit();
		session.close();
		
		return reservationUsers;
		
	}
	/*
	public void toggleReservationStatus(User user, Book book){
		System.out.println("Toggling reservation status");
		Session session = sessionFactory.openSession();
		Query q = session.createQuery("select r from Reservation r "
									+ "where r.reservationId.user = :user "
									+ "and r.reservationId.book = :book "
									+ "and r.reservationStatus = :status");
		q.setParameter("user", user);
		q.setParameter("book", book);
		q.setParameter("status", 1);
		
		Reservation reservation = (Reservation) q.list().get(0);
		
		//System.out.println(reservation.getUser().getId() + ", " + reservation.getBook().getBookId() + ", " + reservation.getReservationStatus());
		
		//session.beginTransaction();
		/*for(Reservation r : reservations){
			if(r.getReservationStatus() == 1){
				r.setReservationStatus(0);
				session.update(r);
			}
		}*/
		//reservation.setReservationStatus(0);
		//session.update(reservation);
		//session.getTransaction().commit();
		//session.close();
	//}

}
