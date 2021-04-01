package com.afect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.afect.model.Like;

public interface LikeDao  extends JpaRepository<Like, Integer> {
	//@Query("SELECT count(*) from Likes")
	@Query(value = "SELECT count(*) from af_likes where post_id = ?1", nativeQuery = true)
	public int countPostLikes(int postId);
	
	@Query(value = "SELECT count(*) from af_likes where post_id=:postId and user_id=:userId ", nativeQuery = true)
	public int checkLike(@Param("postId")Integer postId, @Param("userId")Integer userId);
	
	public Like findById(int id);
}
