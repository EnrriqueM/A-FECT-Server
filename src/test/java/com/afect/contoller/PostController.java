package com.afect.contoller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.afect.service.PostService;

@SpringBootTest
class PostController {
	@Autowired
	private PostController controller;
	
	@MockBean
	private PostService service;

	@Test
	public void contextLoads() throws Exception {
		assertNotNull(controller);
	}

}
