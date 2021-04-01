package com.afect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afect.model.User;

//<object, Primary key>
public interface UserDao extends JpaRepository<User, Integer>
{
	public List<User> findAll();
	public User findByUsername(String username);
	public User findByEmail(String email);
	public List<User> findByFirstname(String firstname);
	public User findByUsernameAndPassword(String username, String password);
	
	public List<User> findByFirstnameContainingIgnoreCase(String firstname);
	public List<User> findByUsernameContainingIgnoreCase(String username);
}










