package DTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.w3c.dom.Text;

@Entity
@Table(name="Author")
public class Author {
	
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="goodreadsLink")
	private String goodreadsLink;
	
	@Column(name="imageURL")
	private String imageURL;
	
	@Column(name="description")
	private String about;
	
	@Column(name="works_count")
	private int worksCount;
	
	@Column(name="gender")
	private String gender;
	
	@Column(name="hometown")
	private String hometown;
	
	@Column(name="born_at")
	private String bornAt;
	
	@Column(name="died_at")
	private String diedAt;

	
	@OneToMany(mappedBy="author")
	//@JoinTable(name="author_books", joinColumns=@JoinColumn(name="authorId"), inverseJoinColumns=@JoinColumn(name="bookId"))
	private Collection<BookISBN> books = new ArrayList<BookISBN>();

	public Collection<BookISBN> getBooks() {
		return books;
	}

	public void setBooks(Collection<BookISBN> books) {
		this.books = books;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGoodreadsLink() {
		return goodreadsLink;
	}

	public void setGoodreadsLink(String goodreadsLink) {
		this.goodreadsLink = goodreadsLink;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public int getWorksCount() {
		return worksCount;
	}

	public void setWorksCount(int worksCount) {
		this.worksCount = worksCount;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getBornAt() {
		return bornAt;
	}

	public void setBornAt(String bornAt) {
		this.bornAt = bornAt;
	}

	public String getDiedAt() {
		return diedAt;
	}

	public void setDiedAt(String diedAt) {
		this.diedAt = diedAt;
	}
	
	

}
