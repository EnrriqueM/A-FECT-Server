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
@Table(name="af_likes")
public class Like {
	@Id
	@Column(name="like_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int like_id;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch=FetchType.LAZY, optional = false)
	@JoinColumn(name="post_id", nullable = false)
	private Post post;	
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="user_id", nullable = false)
	private User user;

	public Like(int like_id, Post post, User user) {
		super();
		this.like_id = like_id;
		this.post = post;
		this.user = user;
	}

	public Like(Post post, User user) {
		super();
		this.post = post;
		this.user = user;
	}

	public Like() {
		super();
	}

	public int getLike_id() {
		return like_id;
	}

	public void setLike_id(int like_id) {
		this.like_id = like_id;
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

	@Override
	public String toString() {
		return "Like [like_id=" + like_id + ", post=" + post + ", user=" + user + "]";
	}
	
}