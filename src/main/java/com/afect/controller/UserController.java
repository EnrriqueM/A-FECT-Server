package com.afect.controller;

import java.util.List;

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
	
	public UserController()
	{
		super();
	}

	@Autowired
	public UserController(UserService uService) {
		super();
		this.uService = uService;
	}
	
	@PostMapping("/checkEmail/{email}")
	public ResponseEntity<String> checkEmail(@PathVariable("email") String email)
	{
		//set email to lower case
		email = email.toLowerCase();
		//If no user is found, then email is available
		if(uService.getUserByEmail(email) == null)
		{
			return new ResponseEntity<String>("Email is available", HttpStatus.OK);
		}
		
		//Email is taken
		return new ResponseEntity<String>("Email is taken", HttpStatus.FOUND);
	}
	
	@PostMapping("/checkUsername/{un}")
	public ResponseEntity<String> checkUsername(@PathVariable("un") String un)
	{
		//If no user is found, then email is available
		if(uService.getUserByUsername(un) == null)
		{
			return new ResponseEntity<String>("un is available", HttpStatus.OK);
		}
		
		//Email is taken
		return new ResponseEntity<String>("Email is taken", HttpStatus.FOUND);
	}
	
	/*
	 * Attempt to register a user
	 * */
	@PostMapping
	public ResponseEntity<String> addUser(@RequestBody User u)
	{
		//Set email to lower case
		u.setEmail(u.getEmail().toLowerCase());
		//First try and find this user by email
		if(!(uService.getUserByEmail(u.getEmail()) == null))
		{
			System.out.println("User email already exists");
			return new ResponseEntity<String>("Failed to create user", HttpStatus.BAD_REQUEST);
		}
		//Try and find by username
		else if(!(uService.getUserByEmail(u.getUsername()) == null))
		{
			System.out.println("Username already exists");
			return new ResponseEntity<String>("Failed to create user", HttpStatus.BAD_REQUEST);
		}
		
		//Create the new user
		try
		{
			uService.insertUser(u);
		}
		catch(Exception e)
		{
			System.out.print(e.getMessage());
			return new ResponseEntity<String>("Failed to create user", HttpStatus.BAD_REQUEST);
		}
		
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
			System.out.println("No user found");
			return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
		}
		System.out.println(" user found! ");
		//Return user id
		return new ResponseEntity<>(u.getUser_id(), HttpStatus.OK);
	}
	
	/*
	 * GET user list by firstname*/
	@GetMapping("/search")
	public List<User> searchUsersByFirstname(@RequestParam(name="firstname") String fn)
	{
		System.out.println(fn);
		List<User> userList = uService.getUsersByFirstname(fn);
		
		//Return user id
		return userList;
	}
	
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
	
	@DeleteMapping("/{username}")
	public ResponseEntity<String> deleteUser(@PathVariable("username") String un)
	{
		User u = uService.getUserByUsername(un);
		
		uService.deleteUser(u);
		
		return new ResponseEntity<>("User Deleted", HttpStatus.GONE);
	}
	
	
}
