package com.afect.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="af_comments")
public class Comment {
	@Id
	@Column(name="commen_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int commen_id;
	
	@Column(name="comment")
	private String comment;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch=FetchType.LAZY, optional = false)
	@JoinColumn(name="post_id", nullable = false)
	private Post post;	
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="user_id", nullable = false)
	private User user;

	public Comment() {
		super();
	}

	public Comment(int commen_id, String comment, Post post, User user) {
		super();
		this.commen_id = commen_id;
		this.comment = comment;
		this.post = post;
		this.user = user;
	}

	public Comment(String comment, Post post, User user) {
		super();
		this.comment = comment;
		this.post = post;
		this.user = user;
	}

	public int getCommen_id() {
		return commen_id;
	}

	public void setCommen_id(int commen_id) {
		this.commen_id = commen_id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
