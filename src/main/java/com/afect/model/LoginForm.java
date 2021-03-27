package com.afect.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginForm 
{
	private String username;
	private String password;
	
	public LoginForm(@JsonProperty("username") String username, @JsonProperty("password") String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginForm [username=" + username + ", password=" + password + "]";
	}
	
	
}
