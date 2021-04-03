package com.afect.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.afect.model.Post;
import com.afect.model.User;
import com.afect.repository.PostDao;

@RunWith(MockitoJUnitRunner.class)
class PostServiceTest {
	private User dummyUser;
	private Post p;
	
	@Mock
	PostDao pDao;
	
	@InjectMocks
	PostService pService;
	
	@BeforeEach
    public void setUp()
    {
		MockitoAnnotations.openMocks(this);
		
		//ASSIGN
		dummyUser = new User();
		dummyUser.setUser_id(100);
		dummyUser.setEmail("test@gmail.com");
		dummyUser.setUsername("test");
		dummyUser.setPassword("123");
		dummyUser.setFirstname("Johnny");
		
		p = new Post();
		p.setUser(dummyUser);
		p.setPost_id(42);
		p.setTitle("The Title");
		p.setMessage("Hello there");
    }
	
	@Test
	void fetchPostsBySimilarTitle()
	{
		//ASSIGN
		String title = "Title";
		List<Post> allPost = new ArrayList<>();
		allPost.add(p);
		Mockito.when(pDao.findByTitleContainingIgnoreCase(title)).thenReturn(allPost);
		
		//ACT
		List<Post> pList = pService.getByPostsWithLikeTitle(title);
		
		//ASSERT
		assertEquals(allPost.size(), pList.size());	//EXPECTED VS ACTUAL
        Mockito.verify(pDao, Mockito.times(1)).findByTitleContainingIgnoreCase(title);
	}
	
	@Test
	void fetchPostByTitle()
	{
		//ASSIGN
		String title = "The Title";
		List<Post> allPost = new ArrayList<>();
		allPost.add(p);
		Mockito.when(pDao.findByTitle(title)).thenReturn(allPost);
		
		//ACT
		List<Post> pList = pService.getByTitle(title);
		
		//ASSERT
		assertEquals(allPost.size(), pList.size());	//EXPECTED VS ACTUAL
        Mockito.verify(pDao, Mockito.times(1)).findByTitle(title);
	}
	
	@Test
	void fetchPostById()
	{
		//ASSIGN
		int id = 42;
		Mockito.when(pDao.findById(id)).thenReturn(p);
		
		//ACT
		Post post = pService.getById(id);
		
		//ASSERT
		assertEquals(p.getPost_id(), post.getPost_id());	//EXPECTED VS ACTUAL
        Mockito.verify(pDao, Mockito.times(1)).findById(id);
	}
	
	@Test
	void fetchPostByUserId()
	{
		//ASSIGN
		List<Post> allPost = new ArrayList<>();
		allPost.add(p);
		Mockito.when(pDao.findByUser(dummyUser)).thenReturn(allPost);
		
		//ACT
		List<Post> userPosts = pService.getByUserId(dummyUser);
		
		//ASSERT
		assertEquals(allPost.size(), userPosts.size());	//EXPECTED VS ACTUAL
        Mockito.verify(pDao, Mockito.times(1)).findByUser(dummyUser);
	}
	
	@Test
	void fetchAllPost()
	{
		//ASSIGN
		List<Post> allPost = new ArrayList<>();
		allPost.add(p);
		Mockito.when(pDao.findAll()).thenReturn(allPost);
		
		//ACT
		List<Post> pList = pService.getAllPost();
		
		//ASSERT
		assertEquals(allPost.size(), pList.size());	//EXPECTED VS ACTUAL
        Mockito.verify(pDao, Mockito.times(1)).findAll();
	}
	
	@Test
	void deletePost()
	{
		pService.deletePost(p);
		
		Mockito.verify(pDao, Mockito.times(1)).delete(p);
	}

	@Test
	void createPost() {
		//ACT
		pService.insertPost(p);
		
		Mockito.verify(pDao, Mockito.times(1)).save(p);
	}

}
