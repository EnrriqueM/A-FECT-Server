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

import com.afect.model.Like;
import com.afect.model.Post;
import com.afect.model.User;
import com.afect.service.LikeService;
import com.afect.service.PostService;
import com.afect.service.UserService;

@RestController
@RequestMapping(value="/api/like")
@CrossOrigin(origins = "*")
public class LikeController {
	private PostService pService;
	private UserService uService;
	private LikeService likeService;
	
	public LikeController() {
		super();
	}

	@Autowired
	public LikeController(PostService pService, UserService uService, LikeService likeService) {
		super();
		this.pService = pService;
		this.uService = uService;
		this.likeService = likeService;
	}
	
	@PostMapping("/addlike")
	public ResponseEntity<Integer> insertLike(@RequestBody LinkedHashMap<String, String> likeMap)
	{
		int postId = Integer.parseInt(likeMap.get("postId"));
		int userId = Integer.parseInt(likeMap.get("userId"));
		int count = likeService.checkIfUserLiked(postId, userId);
		if(count != 0) {
			return new ResponseEntity<Integer>(0, HttpStatus.CREATED);
		} else {
			Post p = pService.getById(postId);
			User u = uService.getUserById(userId);
			
			Like like = new Like(p, u);
			
			List<Like> postLikes = p.getLikes();
			postLikes.add(like);
			p.setLikes(postLikes);
			
			//Insert to DB
			likeService.addLike(like);
			
			int likeId = likeService.checkIfUserLiked(postId, userId);
			return new ResponseEntity<Integer>(likeId, HttpStatus.CREATED);
		}
	}
	
	@GetMapping("/postlike/{postId}")
	public ResponseEntity<Integer> getLikesByPost(@PathVariable("postId")Integer postId){
		int count = 0;
		count = likeService.getPostLikes(postId);
		if(count==0) {
			return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(count, HttpStatus.OK);
		}
		
	}
	
	@GetMapping("/checkLike/{postId}/{userId}")
	public ResponseEntity<Integer> checkLikeByPostAndUser(@PathVariable("postId")Integer postId, @PathVariable("userId")Integer userId){
		int likeId = likeService.checkIfUserLiked(postId, userId);
		return new ResponseEntity<Integer>(likeId, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{likeId}")
	public void removeLike(@PathVariable("likeId")int likeId){
		if(likeService.getLike(likeId) != null) {
			likeService.deleteLike(likeId);
		}
		
	}
	
	
}
