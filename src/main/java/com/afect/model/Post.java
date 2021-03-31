package com.afect.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="af_Post")
public class Post {
	@Id
	@Column(name="post_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int post_id;
	
	@Column(name="post_title", nullable=false)
	private String title;
	
	@Column(name="message", nullable=false)
	private String message;
	
	@Column(name="image")
	private byte[] image;
	
	@Column(name="dateCreated", nullable=false)
	@CreationTimestamp
	private LocalDateTime dateCreated;
	
	@Column(name="dateUpdated")
	@UpdateTimestamp
	private LocalDateTime dateUpdated;

	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	 private User user;
	
	public Post() 
	{
		super();
	}
	
	public Post(String title, String message, byte[] image, User user) 
	{
		super();
		this.message = message;
		this.image = image;
		this.user = user;
		this.title = title;
	}

	public Post(String title, String message, byte[] image, LocalDateTime dateCreated, LocalDateTime dateUpdated, User user) 
	{
		super();
		this.dateCreated = dateCreated;
		this.dateUpdated = dateUpdated;
		this.message = message;
		this.image = image;
		this.user = user;
		this.title = title;
	}

	public Post(int post_id, String title, String message, byte[] image, 
			LocalDateTime dateCreated, LocalDateTime dateUpdated, User user) 
	{
		super();
		this.post_id = post_id;
		this.dateCreated = dateCreated;
		this.dateUpdated = dateUpdated;
		this.message = message;
		this.image = image;
		this.user = user;
		this.title = title;
	}

	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public LocalDateTime getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(LocalDateTime dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Post [post_id=" + post_id + ", dateCreated=" + dateCreated + ", dateUpdated=" + dateUpdated
				+ ", message=" + message  + ", user=" + user.getUsername() + "]";
	}
	
	
	
}
