package com.afect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afect.model.Post;
import com.afect.model.User;

public interface PostDao extends JpaRepository<Post, Integer>
{
	public List<Post> findAll();
	public List<Post> findByUser(User u);
	public List<Post> findByTitle(String title);
	public Post findById(int id);
}
