package com.afect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afect.model.Comment;
import com.afect.model.Post;
import com.afect.model.User;

public interface CommentDao extends JpaRepository<Comment, Integer> {
	public List<Comment> findByPost(int postId);
	public Comment findByPostAndUser(Post post, User u);
}
