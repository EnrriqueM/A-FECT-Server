package com.afect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afect.model.EmailToken;

public interface EmailTokenDao extends JpaRepository<EmailToken, String> 
{
	//Future reference
	//@Query("SELECT c FROM Customer c WHERE c.email = ?1")
	public EmailToken findByEmail(String email);
	
	public EmailToken findByToken(String token);
}
