package com.afect.controller;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afect.model.Post;
import com.afect.model.User;
import com.afect.service.PostService;
import com.afect.service.UserService;

@RestController
@RequestMapping(value="/api/post")
public class PostController 
{
	private PostService pService;
	private UserService uService;
	
	public PostController() {
		super();
	}

	@Autowired
	public PostController(PostService pService, UserService uService) {
		super();
		this.pService = pService;
		this.uService = uService;
	}
	
	@PostMapping()
	public ResponseEntity<String> insertPost(@RequestBody LinkedHashMap<String, String> pMap)
	{
		System.out.println(pMap.get("username"));
		User u = uService.getUserByUsername(pMap.get("username"));
		System.out.println(u);
		
		Post p = new Post(pMap.get("title"), pMap.get("message"), null, u);
		List<Post> usersPost = u.getPosts();
		usersPost.add(p);
		u.setPosts(usersPost);
		uService.insertUser(u);
		
		User u2 = uService.getUserByUsername(pMap.get("username"));
		List<Post> usersPost2 = u2.getPosts();
		System.out.println(usersPost2);
		
		return new ResponseEntity<String>("Resource was created", HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<List<Post>> getUserByUserName(@PathVariable("id") String id)
	{
		System.out.println("id: " + id);
		if (pService.getByTitle(id) == null)
		{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(pService.getByTitle(id), HttpStatus.OK);
	}
	
}
