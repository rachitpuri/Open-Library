package DTO;

import java.beans.Transient;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="reservation")
public class Reservation {

	@EmbeddedId
	private ReservationId reservationId = new ReservationId();
	
	@Column(name="reservationDate")
	private Date reservationDate;
	
	@Column(name="reservationStatus")
	private int reservationStatus;

	public ReservationId getReservationId() {
		return reservationId;
	}

	public void setReservationId(ReservationId reservationId) {
		this.reservationId = reservationId;
	}

	@Transient
	public User getUser(){
		return getReservationId().getUser();
	}
	
	public void setUser(User user){
		getReservationId().setUser(user);
	}
	
	@Transient
	public Book getBook(){
		return getReservationId().getBook();
	}
	
	public void setBook(Book book){
		getReservationId().setBook(book);
	}

	public Date getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}

	public int getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(int reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	
	

}
