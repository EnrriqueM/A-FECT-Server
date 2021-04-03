package com.afect.contoller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.afect.controller.UserController;
import com.afect.model.User;
import com.afect.service.UserService;

@SpringBootTest
class UserControllerTest {

	@Autowired
	private UserController controller;
	
	@MockBean
	private UserService service;
	
	@Test
	public void contextLoads() throws Exception {
		assertNotNull(controller);
	}

}
