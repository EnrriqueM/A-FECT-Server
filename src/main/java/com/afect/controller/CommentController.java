package com.afect.controller;

import java.util.ArrayList;
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

import com.afect.model.Comment;
import com.afect.model.Post;
import com.afect.model.User;
import com.afect.service.CommentService;
import com.afect.service.PostService;
import com.afect.service.UserService;

@RestController
@RequestMapping(value="/api/comment")
@CrossOrigin(origins = "*")
public class CommentController {

	
	private PostService pService;
	private UserService uService;
	private CommentService cService;
	
	public CommentController() {
		super();
	}

	@Autowired
	public CommentController(PostService pService, UserService uService, CommentService cService) {
		super();
		this.pService = pService;
		this.uService = uService;
		this.cService = cService;
	}
	
	@PostMapping("/addcomment")
	public ResponseEntity<List<Comment>> insertLike(@RequestBody LinkedHashMap<String, String> likeMap)
	{
		String message = likeMap.get("comment");
		int postId = Integer.parseInt(likeMap.get("postId"));
		int userId = Integer.parseInt(likeMap.get("userId"));
		
		Post p = pService.getById(postId);
		User u = uService.getUserById(userId);
			
		Comment cmt = new Comment(message,p, u);
			
		List<Comment> postComments = p.getComments();
		postComments.add(cmt);
		p.setComments(postComments);
		
		cService.addComment(cmt);
			return new ResponseEntity<List<Comment>>(postComments, HttpStatus.CREATED);
	}
	
	@GetMapping("/postcomments/{postId}")
	public ResponseEntity<List<Comment>> getLikesByPost(@PathVariable("postId")Integer postId){
		
		List<Comment> cList = new ArrayList<Comment>();
		cList.addAll(cService.getPostComments(postId));
		
		if(cList.size() == 0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(cList, HttpStatus.OK);
		}
		
	}
	
	@DeleteMapping("/delete/{commentId}")
	public void removeLike(@PathVariable("commentId")int commentId){
		cService.deleteComment(commentId);
		
	}
}
