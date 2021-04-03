package com.afect.model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="af_Post")
public class Post{
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
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@ManyToOne(fetch=FetchType.LAZY, optional = false)
	@JoinColumn(name="user_id", nullable = false)
	 private User user;
	
	//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@OneToMany(mappedBy="post", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Like> likes = new ArrayList<>();
	
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

	public Post(int post_id, String title, String message, byte[] image, LocalDateTime dateCreated,
			LocalDateTime dateUpdated, User user, List<Like> likes) {
		super();
		this.post_id = post_id;
		this.title = title;
		this.message = message;
		this.image = image;
		this.dateCreated = dateCreated;
		this.dateUpdated = dateUpdated;
		this.user = user;
		this.likes = likes;
	}

	public Post(String title, String message, byte[] image, LocalDateTime dateCreated, LocalDateTime dateUpdated,
			User user, List<Like> likes) {
		super();
		this.title = title;
		this.message = message;
		this.image = image;
		this.dateCreated = dateCreated;
		this.dateUpdated = dateUpdated;
		this.user = user;
		this.likes = likes;
	}
	
	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	@Override
	public String toString() {
		return "Post [post_id=" + post_id + ", title=" + title + ", message=" + message + 
				", dateCreated=" + dateCreated + ", dateUpdated=" + dateUpdated  + "]";
	}

	
	
	
}
