package com.afect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afect.model.Post;
import com.afect.model.User;
import com.afect.repository.PostDao;

@Service
public class PostService 
{
	private PostDao pDao;

	public PostService() 
	{
		super();
	}

	@Autowired
	public PostService(PostDao pDao) 
	{
		super();
		this.pDao = pDao;
	}
	
	public void insertPost(Post p)
	{
		pDao.save(p);
	}
	
	public void deletePost(Post p)
	{
		pDao.delete(p);
	}
	
	public List<Post> getAllPost()
	{
		return pDao.findAll();
	}
	
	public List<Post> getByUserId(User user_id)
	{
		return pDao.findByUser(user_id);
	}
	
	public List<Post> getByTitle(String title)
	{
		return pDao.findByTitle(title);
	}
	
	public List<Post> getByPostsWithLikeTitle(String title)
	{
		return pDao.findByTitleContainingIgnoreCase(title);
	}
	
	public Post getById(int id)
	{
		Post p = pDao.findById(id);
		
		if(p == null)
		{
			//TODO: THROW CUSTOM ERROR
			return null;
		}
		
		return p;
	}
}
