package com.afect.controller;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afect.model.User;
import com.afect.service.UserService;

@RestController
@RequestMapping(value="/api/user")
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
	
	@PostMapping()
	public ResponseEntity<String> insertFood(@RequestBody LinkedHashMap<String, String> uMap)
	{
		User u = new User(uMap.get("username"), uMap.get("password"), uMap.get("email"));
		
		return new ResponseEntity<String>("Resource was created", HttpStatus.CREATED);
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> getUserByUserName(@PathVariable("username") String un)
	{
		if (uService.getUserByUsername(un) == null)
		{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<User>(uService.getUserByUsername(un), HttpStatus.OK);
	}
	
	@DeleteMapping("/{username}")
	public ResponseEntity<String> deleteUser(@PathVariable("username") String un)
	{
		User u = uService.getUserByUsername(un);
		
		uService.deleteUser(u);
		
		return new ResponseEntity<>("User Deleted", HttpStatus.GONE);
	}
	
	
}
