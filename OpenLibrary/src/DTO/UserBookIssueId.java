package DTO;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
public class UserBookIssueId implements java.io.Serializable{
	/*
	@Column(name="issueDate")
	private Date issueDate;
	
	@Column(name="returnDate")
	private Date returnDate;
	
	@Column(name="userBill")
	private int userBill;
	*/
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Book book;

	/*
	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public int getUserBill() {
		return userBill;
	}

	public void setUserBill(int userBill) {
		this.userBill = userBill;
	}
	*/
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
	/*
	public boolean equals(Object instance) {
        if(instance == null)
            return false;

        if(!(instance instanceof UserBookIssue))
            return false;

        UserBookIssue other = (UserBookIssue) instance;
        if(user.getId() != other.getUser().getId())
            return false;

        if(book.getBookId() != other.getBook().getBookId())
            return false;

        // ATT: use immutable fields like joinedDate in equals() implementation
        if(!(issueDate.equals(other.getIssueDate())))
            return false;

        return true;
    }
	*/
	
}
