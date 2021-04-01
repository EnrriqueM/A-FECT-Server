package com.afect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afect.model.Like;
import com.afect.repository.LikeDao;

@Service
public class LikeService {
	private LikeDao likeDao;

	public LikeService() {
		super();
	}

	@Autowired
	public LikeService(LikeDao likeDao) {
		super();
		this.likeDao = likeDao;
	}
	
	public void addLike(Like like) {
		likeDao.save(like);
	} 
	
	public int getPostLikes(int postId){
		return likeDao.countPostLikes(postId);
	}
	
	public int checkIfUserLiked(int pid, int uid) {
		int like = likeDao.checkLike(pid, uid);
		return like;
	}
	
	public void deleteLike(int id) {
		likeDao.deleteById(id);
	}
	
	public Like getLike(int id) {
		return likeDao.findById(id);
	}
	
}