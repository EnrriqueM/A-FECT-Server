package com.afect.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="af_token")
public class EmailToken {
	@Id
	@Column(name="email", unique=true, nullable=false)
	private String email;
	
	@Column(name="token", nullable=false)
	private String token;
	
	@Column(name="expiration")
	private LocalDateTime expiration;
	
	public EmailToken(String email, String token, LocalDateTime expiration) {
		super();
		this.email = email;
		this.token = token;
		this.expiration = expiration;
	}
	
	@JsonCreator
	public EmailToken (@JsonProperty("email") String email, @JsonProperty("token") String token)
	{
		super();
		this.email = email;
		this.token = token;
		this.expiration = null;
	}
	
	public EmailToken() {
		super();
	}
	
	//GETTERS and SETTERS
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public LocalDateTime getExpiration() {
		return expiration;
	}
	public void setExpiration(LocalDateTime expiration) {
		this.expiration = expiration;
	}
	
}
