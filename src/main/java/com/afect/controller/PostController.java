package com.afect.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afect.model.Post;
import com.afect.model.User;
import com.afect.service.PostService;
import com.afect.service.UserService;

@RestController
@RequestMapping(value="/api/post")
@CrossOrigin(origins = "*")
public class PostController 
{
	private PostService pService;
	private UserService uService;
	private static final Logger LOGGER = Logger.getLogger(PostController.class);
	
	public PostController() {
		super();
		LOGGER.info("Init Post Controller");
	}

	@Autowired
	public PostController(PostService pService, UserService uService) {
		super();
		this.pService = pService;
		this.uService = uService;
		LOGGER.info("Auto Init Post Controller");
	}
	
	/*
	 * Attempt to create a new post
	 * Called when user clicks add post
	 * */
	@PostMapping("/addpost")
	public ResponseEntity<List<Post>> insertPost(@RequestBody LinkedHashMap<String, String> pMap)
	{
		System.out.println("in new post");
		System.out.println(pMap);
		User u = uService.getUserById(Integer.parseInt(pMap.get("userId")));
		
		Post p = new Post(pMap.get("title"), pMap.get("message"), null, u);
		List<Post> usersPost = u.getPosts();
		usersPost.add(p);
		u.setPosts(usersPost);
		
		//Insert to DB
		//uService.insertUser(u);
		pService.insertPost(p);
		
		LOGGER.info("New Post created " + p);
		return new ResponseEntity<List<Post>>(usersPost, HttpStatus.CREATED);
	}
	
	/*
	 * Return all recent post
	 * Called when user is in the View all post site
	 * */
	@GetMapping("/allposts")
	public ResponseEntity<List<Post>> getAllPosts(){
		List<Post> pList = new ArrayList<Post>();
		pList = pService.getAllPost();
		if(pList.size()==0){
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(pList, HttpStatus.OK);
		}
	} 
	
	/*
	 * Rest Controller to request a list of post with a certain title
	 * Called when user is in searching in the navbar
	 * */
	@GetMapping("/title")
	public ResponseEntity<List<Post>> getPostsByLikeTitle(@RequestParam(name="title") String title)
	{
		return new ResponseEntity<>(pService.getByPostsWithLikeTitle(title), HttpStatus.OK);
	}
	
	@GetMapping("/title/{title}")
	public ResponseEntity<List<Post>> getPostByTitle(@PathVariable("title") String title)
	{
		if (pService.getByTitle(title) == null)
		{
			LOGGER.warn("No post of tite: " + title + " found");
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(pService.getByTitle(title), HttpStatus.OK);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Post> getPostByPostId(@PathVariable("id") int id)
	{
		Post p = pService.getById(id);
		if (p == null)
		{
			LOGGER.warn("No post of id: " + id + " found");
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(p, HttpStatus.OK);
	}
	
	/*
	 * Get a user's post
	 * Called when a user is viewing another's profile
	 * */
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Post>> getPostByUserId(@PathVariable("userId") int id)
	{
		User u = uService.getUserById(id);
		
		List<Post> p = pService.getByUserId(u);
		return new ResponseEntity<>(p, HttpStatus.OK);
	}
	
}
