package DTO;

import java.beans.Transient;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="issue")
public class UserBookIssue {
	
	@EmbeddedId
	private UserBookIssueId userBookIssueId = new UserBookIssueId();
	
	@Column(name="issueDate")
	private Date issueDate;
	
	@Column(name="returnDate")
	private Date returnDate;
	
	@Column(name="userBill")
	private Double userBill;
	
	
	public UserBookIssueId getUserBookIssueId() {
		return userBookIssueId;
	}

	public void setUserBookIssueId(UserBookIssueId userBookIssueId) {
		this.userBookIssueId = userBookIssueId;
	}
	
	@Transient
	public User getUser(){
		return getUserBookIssueId().getUser();
	}
	
	public void setUser(User user){
		getUserBookIssueId().setUser(user);
	}
	
	@Transient
	public Book getBook(){
		return getUserBookIssueId().getBook();
	}
	
	public void setBook(Book book){
		getUserBookIssueId().setBook(book);
	}

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

	public Double getUserBill() {
		return userBill;
	}

	public void setUserBill(Double userBill) {
		this.userBill = userBill;
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
