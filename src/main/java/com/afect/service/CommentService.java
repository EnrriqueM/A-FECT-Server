package com.afect.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afect.model.Comment;
import com.afect.repository.CommentDao;

@Service
public class CommentService {
	private CommentDao cDao;

	@Autowired
	public CommentService(CommentDao cDao) {
		super();
		this.cDao = cDao;
	}

	public CommentService() {
		super();
	}
	
	public void addComment(Comment cmt) {
		cDao.save(cmt);
	}
	
	public List<Comment> getPostComments(int postId){
		List<Comment> cList = new ArrayList<Comment>();
		cList = cDao.findByPost(postId);
		return cList;
	}

	public void deleteComment(int commentId) {
		// TODO Auto-generated method stub
		cDao.deleteById(commentId);
	}
	
	
	
}