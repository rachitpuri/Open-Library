package DTO;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_comments")
public class UserComment {
	@Id
	private int user;
	
	private int book;
	
	//@EmbeddedId
	//private UserBookComment userBook;
	
	private int comment;

	
	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public int getBook() {
		return book;
	}

	public void setBook(int book) {
		this.book = book;
	}
	

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}
	/*
	public UserBookComment getUserBook() {
		return userBook;
	}

	public void setUserBook(UserBookComment userBook) {
		this.userBook = userBook;
	}
	*/
	

}
