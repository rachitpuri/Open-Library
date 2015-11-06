package DTO;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Book")
public class Book {
	
	@Id
	@Column(name="id")
	private int bookId;
	
	@Column(name="title")
	private String title;
	
	@Column(name="publishedOn")
	private int publicationYear;
	
	@Column(name="image")
	private String bookImage;
	
	@Column(name="bookCount")
	private int bookCount;
	
	@Column(name="pricePerDay")
	private double price;
	
	@Column(name="author")
	private int authorId;
	
	@Column(name="publicationYear")
	private String publicationYr;
	
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author", nullable = false)
	public Author getAuthor() {
		return this.authorid;
	}*/
	
	private String publicationMonth;
	
	private String publicationDay;
	
	private String publisher;
	
	private String description;
	
	//Issue books functionality
	@OneToMany(fetch=FetchType.LAZY, mappedBy="userBookIssueId.book")
	//@JoinTable(name="issue",
	//		joinColumns=@JoinColumn(name="book"))
	private Collection<UserBookIssue> userBookIssue = new ArrayList<UserBookIssue>();

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getBookCount() {
		return bookCount;
	}

	public void setBookCount(int bookCount) {
		this.bookCount = bookCount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

	public String getBookImage() {
		return bookImage;
	}

	public void setBookImage(String bookImage) {
		this.bookImage = bookImage;
	}

	public String getPublicationYr() {
		return publicationYr;
	}

	public void setPublicationYr(String publicationYr) {
		this.publicationYr = publicationYr;
	}

	public String getPublicationMonth() {
		return publicationMonth;
	}

	public void setPublicationMonth(String publicationMonth) {
		this.publicationMonth = publicationMonth;
	}

	public String getPublicationDay() {
		return publicationDay;
	}

	public void setPublicationDay(String publicationDay) {
		this.publicationDay = publicationDay;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Collection<UserBookIssue> getUserBookIssue() {
		return userBookIssue;
	}

	public void setUserBookIssue(Collection<UserBookIssue> userBookIssue) {
		this.userBookIssue = userBookIssue;
	}
}
