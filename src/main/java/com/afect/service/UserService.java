package com.afect.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afect.model.User;
import com.afect.repository.UserDao;

@Service
public class UserService 
{
	private UserDao userDao;
	
	public UserService()
	{
		super();
	}

	@Autowired
	public UserService(UserDao userDao) {
		super();
		this.userDao = userDao;
	}
	
	public User getUserByUsername(String un)
	{
		User u = userDao.findByUsername(un);
		
		if(u == null)
		{
			return null;
		}
		
		return u;
	}
	
	public List<User> getAllUsers()
	{
		return userDao.findAll();
	}
	
	public void insertUser(User u)
	{
		userDao.save(u);
	}
	
	public List<User> getUserByFirstname(String name)
	{
		return userDao.findByFirstname(name);
	}
	
	public User getUserById(int id)
	{
		Optional<User> u = userDao.findById(id);
		
		return u.get();
	}
	
	public User getUsernameAndPassword(String un, String pwd)
	{
		return userDao.findByUsernameAndPassword(un, pwd);
	}
	
	public User getUserByEmail(String email)
	{
		return userDao.findByEmail(email);
	}
	
	//Ask query to get user with %firstname%
	public List<User> getUsersByFirstname(String fn)
	{
		return userDao.findByFirstnameContainingIgnoreCase(fn);
	}
	
	//Ask query to get user with %firstname%
	public List<User> getUsersByUsername(String un)
	{
		return userDao.findByUsernameContainingIgnoreCase(un);
	}
	
	public void deleteUser(User u)
	{
		userDao.delete(u);
	}
	
	public void updateUser(User u)
	{
		userDao.save(u);
	}
	
}
