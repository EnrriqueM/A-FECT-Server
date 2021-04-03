package com.afect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.afect.model.Comment;
import com.afect.model.Post;
import com.afect.model.User;

public interface CommentDao extends JpaRepository<Comment, Integer> {
	@Query(value = "SELECT * from af_comments where post_id = ?1", nativeQuery = true)
	public List<Comment> findByPost(int postId);
	
	public Comment findByPostAndUser(Post post, User u);
}
