package com.afect.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afect.model.LoginForm;
import com.afect.model.Post;
import com.afect.model.User;
import com.afect.service.UserService;

@RestController
@RequestMapping(value="/api/user")
@CrossOrigin(origins = "*")
public class UserController {
	private UserService uService;
	private static final Logger LOGGER = Logger.getLogger(UserController.class);
	
	public UserController()
	{
		super();
		LOGGER.info("Init User Controller");
	}

	@Autowired
	public UserController(UserService uService) {
		super();
		this.uService = uService;
		LOGGER.info("Auto Init User Controller");
	}
	
	/*
	 * Checks if an email is not already associated with an account
	 * Called when user is filling out the Registration form in /Register
	 * */
	@PostMapping("/checkEmail/{email}")
	public ResponseEntity<String> checkEmail(@PathVariable("email") String email)
	{
		//set email to lower case
		email = email.toLowerCase();
		//If no user is found, then email is available
		if(uService.getUserByEmail(email) == null)
		{
			LOGGER.trace("Email: " + email + " is available");
			return new ResponseEntity<String>("Email is available", HttpStatus.OK);
		}
		
		//Email is taken
		return new ResponseEntity<String>("Email is taken", HttpStatus.FOUND);
	}
	
	/*
	 * Check is a username is available to use
	 * Called when user is filling out the Registration form in /Register
	 * */
	@PostMapping("/checkUsername/{un}")
	public ResponseEntity<String> checkUsername(@PathVariable("un") String un)
	{
		//If no user is found, then email is available
		if(uService.getUserByUsername(un) == null)
		{
			LOGGER.trace("Username: " + un + " is available");
			return new ResponseEntity<String>("un is available", HttpStatus.OK);
		}
		
		//Email is taken
		return new ResponseEntity<String>("Email is taken", HttpStatus.FOUND);
	}
	
	/*
	 * Attempt to register a user
	 * Called when user hits the Register button in /Register
	 * */
	@PostMapping
	public ResponseEntity<String> addUser(@RequestBody User u)
	{
		//Set email to lower case
		u.setEmail(u.getEmail().toLowerCase());
		//First try and find this user by email
		if(!(uService.getUserByEmail(u.getEmail()) == null))
		{
			LOGGER.warn("Attempted to register with an existing email");
			return new ResponseEntity<String>("Failed to create user", HttpStatus.BAD_REQUEST);
		}
		//Try and find by username
		else if(!(uService.getUserByEmail(u.getUsername()) == null))
		{
			LOGGER.warn("Attempted to register with an existing username");
			return new ResponseEntity<String>("Failed to create user", HttpStatus.BAD_REQUEST);
		}
		
		//Create the new user
		try
		{
			uService.insertUser(u);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			return new ResponseEntity<String>("Failed to create user", HttpStatus.BAD_REQUEST);
		}
		
		LOGGER.info("New user created: " + u);
		return new ResponseEntity<String>("Resource was created", HttpStatus.CREATED);
	}
	/*
	 * Attempt to login a user
	 * If found, return user id
	 * */
	@PostMapping("/login")
	public ResponseEntity<Integer> loginUser(@RequestBody LoginForm login)
	{
		System.out.println(login);
		User u = uService.getUserByUsername(login.getUsername());
		System.out.println(u);
		
		//If no user found OR password does not match, Return 404
		if (u == null || !u.getPassword().equals(login.getPassword()))
		{
			LOGGER.warn("User of: " + login + "failed to login");
			return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
		}
		
		LOGGER.info(u.getUsername() + " has logged in");
		//Return user id
		return new ResponseEntity<>(u.getUser_id(), HttpStatus.OK);
	}
	
	/*
	 * GET a list of users with firstname containing ?
	 * Called when user searches in the navbar
	 * */
	@GetMapping("/searchFirstnames")
	public List<User> searchUsersByFirstname(@RequestParam(name="firstname") String fn)
	{
		System.out.println(fn);
		List<User> userList = uService.getUsersByFirstname(fn);
		
		//Return user id
		return userList;
	}
	
	/*
	 * Get a list of users with username containing ?
	 * Called when user searches in the navbar
	 * */
	@GetMapping("/searchUsernames")
	public List<User> searchUsersByUsernames(@RequestParam(name="username") String un)
	{
		List<User> userList = uService.getUsersByUsername(un);
		
		//Return user id
		return userList;
	}
	
	/*
	 * Return a user by Id
	 * Called when attempting to view some ones profile
	 * */
	@GetMapping("/{username}")
	public ResponseEntity<List<Post>> getUserByUserName(@PathVariable("username") String un)
	{
		System.out.println(un);
		if (uService.getUserByUsername(un) == null)
		{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		System.out.println("Here: " + uService.getUserByUsername(un).getPosts());
		return new ResponseEntity<>(uService.getUserByUsername(un).getPosts(), HttpStatus.OK);
	}
	
	/*
	 * Return a user by Id
	 * Called when attempting to view some ones profile
	 * */
	@GetMapping()
	public ResponseEntity<User> getUserByUserId(@RequestParam(name="id") int id)
	{
		User u = uService.getUserById(id);
		if (u == null)
		{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(u, HttpStatus.OK);
	}
	
	/*
	 * Request to delete a user
	 * */
	@DeleteMapping("/{username}")
	public ResponseEntity<String> deleteUser(@PathVariable("username") String un)
	{
		LOGGER.warn("Attempting to delete: " + un);
		User u = uService.getUserByUsername(un);
		
		if(u != null)
		{
			uService.deleteUser(u);
		}
		
		return new ResponseEntity<>("User Deleted", HttpStatus.GONE);
	}
	
	
}
