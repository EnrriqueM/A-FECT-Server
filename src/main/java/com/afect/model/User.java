package com.afect.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="af_User")
public class User 
{
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int user_id;
	
	@Column(name="firstname")
	private String firstname;
	
	@Column(name="lastname")
	private String lastname;
	
	@Column(name="username", unique=true, nullable=false)
	private String username;
	
	@Column(name="user_email", unique=true, nullable=false)
	private String email;
	
	@Column(name="user_password", nullable=false)
	private String password;
	
	@JsonIgnore		//Prevent infinite loop from json
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Post> posts = new ArrayList<>();
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Like> likes = new ArrayList<>();
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Comment> comments = new ArrayList<>();
	
	//Constructors
	public User() {
		super();
	}
	
	@JsonCreator
	public User(
			@JsonProperty("firstname") String firstname, 
			@JsonProperty("lastname") String lastname, 
			@JsonProperty("username") String username, 
			@JsonProperty("email") String email, 
			@JsonProperty("password") String password
			) 
	{
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public User(int user_id, String firstname, String lastname, String username, String email, String password,
			List<Post> posts, List<Like> likes, List<Comment> comments) {
		super();
		this.user_id = user_id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.posts = posts;
		this.likes = likes;
		this.comments = comments;
	}

	public User(String firstname, String lastname, String username, String email, String password, List<Post> posts,
			List<Like> likes, List<Comment> comments) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.posts = posts;
		this.likes = likes;
		this.comments = comments;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString()
	 {
		return "User [user_id=" + user_id + ", firstname=" + firstname + ", lastname=" + lastname + ", username="
				+ username + ", email=" + email + ", password=" + password + "]";
	}

	
	
}