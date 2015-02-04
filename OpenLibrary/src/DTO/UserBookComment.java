package DTO;

import javax.persistence.Embeddable;


@Embeddable
public class UserBookComment {
	
	private int user;
	
	private int book;

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
	
}
