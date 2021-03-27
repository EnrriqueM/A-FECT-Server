package com.afect.controller;

import java.util.LinkedHashMap;
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
	
	@PostMapping
	public ResponseEntity<String> addUser(@RequestBody User u)
	{
		//User u = new User(uMap.get("username"), uMap.get("password"), uMap.get("email"));
		try
		{
			uService.insertUser(u);
		}
		catch(Exception e)
		{
			System.out.print(e.getMessage());
			return new ResponseEntity<String>("Failed ot create user", HttpStatus.BAD_REQUEST);
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
