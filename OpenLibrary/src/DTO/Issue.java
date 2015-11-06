package DTO;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="issue")
public class Issue {
	
	@Id
	@Column(name="userid")
	private int userid;
	
	@Id
	@Column(name="bookid")
	private int bookid;
	
	@Column(name="issueDate")
	private Date issuedate;
	
	@Column(name="returnDate")
	private Date returndate;
	
	@Column(name="userBill")
	private double bill;
	
	public int getUserId() {
		return userid;
	}

	public void setUserId(int id) {
		this.userid = id;
	}

	public int getBookId() {
		return bookid;
	}

	public void setBookId(int id) {
		this.bookid = id;
	}

	public Date getIssueDate() {
		return issuedate;
	}

	public void setIssueDate(Date date) {
		this.issuedate = date;
	}

	public Date getReturnDate() {
		return returndate;
	}

	public void setReturnDate(Date date) {
		this.returndate = date;
	}

	public double getBill() {
		return bill;
	}

	public void setBill(double bill) {
		this.bill = bill;
	}
}
