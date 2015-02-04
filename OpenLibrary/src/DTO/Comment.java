package DTO;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="review_comments")
public class Comment {
	@Id
	private int commentId;
	
	private String comment;
	
	private Timestamp commentTimestamp;
	
	@Column(name="user_name")
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Timestamp getCommentTimestamp() {
		return commentTimestamp;
	}

	public void setCommentTimestamp(Timestamp commentTimestamp) {
		this.commentTimestamp = commentTimestamp;
	}
	
	

}
