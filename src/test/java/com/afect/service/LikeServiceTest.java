package com.afect.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.junit.Before;

import com.afect.model.Like;
import com.afect.model.User;
import com.afect.repository.LikeDao;

class LikeServiceTest {
	private Like like;
	
	@Mock
	LikeDao dao;
	
	@InjectMocks
	LikeService service;
	
	@BeforeEach
    public void setUp()
	{
		MockitoAnnotations.openMocks(this);
		
		like = new Like();
		like.setLike_id(2);
    }
	
	@Test
	void fetchLike() {
		//ASSIGN
		Mockito.when(dao.countPostLikes(2)).thenReturn(15);
		
		//ACT
		int likes = service.getPostLikes(2);
		
		//ASSERT
		assertEquals(likes, 15);
		Mockito.verify(dao, Mockito.times(1)).countPostLikes(2);
	}
	
	@Test
	void deleteLike() {
		//ACT
		service.deleteLike(2);
		
		//ASSERT
		Mockito.verify(dao, Mockito.times(1)).deleteById(2);
	}
	
	@Test
	void createLike() {
		
		service.addLike(like);
		
		//ASSERT
		Mockito.verify(dao, Mockito.times(1)).save(like);
	}

}
