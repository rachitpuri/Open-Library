package DTO;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionOfElements;

@Entity
@Table(name="User")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="firstName")
	private String firstName;
	
	@Column(name="lastName")
	private String lastName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="gender")
	private char gender;
	
	//Issue books functionality
	@OneToMany(fetch=FetchType.LAZY, mappedBy="userBookIssueId.user")
	//@JoinTable(name="issue",
	//		joinColumns=@JoinColumn(name="user"))
	private Collection<UserBookIssue> userBookIssue = new ArrayList<UserBookIssue>();
	
	//Reserve Book functionality
	@OneToMany(fetch=FetchType.LAZY, mappedBy="reservationId.user")
	private Collection<Reservation> reservedBooks = new ArrayList<Reservation>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public Collection<UserBookIssue> getUserBookIssue() {
		return userBookIssue;
	}

	public void setUserBookIssue(Collection<UserBookIssue> userBookIssue) {
		this.userBookIssue = userBookIssue;
	}

	public Collection<Reservation> getReservedBooks() {
		return reservedBooks;
	}

	public void setReservedBooks(Collection<Reservation> reservedBooks) {
		this.reservedBooks = reservedBooks;
	}
	
	

}
