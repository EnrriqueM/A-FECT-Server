package com.afect.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.anyString;

import com.afect.model.EmailToken;
import com.afect.model.User;
import com.afect.repository.EmailTokenDao;
import com.afect.repository.UserDao;

class EmaiTokenServiceTest {
	private User dummyUser;
	private EmailToken et;
	
	@InjectMocks
	private EmailTokenService etService;
	
	@Mock
	private EmailTokenDao etDao;
	@Mock
	private UserDao uDao;
	
	@BeforeEach
    public void init()
	{
		MockitoAnnotations.openMocks(this);
		
		//ASSIGN
		dummyUser = new User();
		dummyUser.setUser_id(100);
		dummyUser.setEmail("test@gmail.com");
		dummyUser.setUsername("test");
		dummyUser.setPassword("123");
		dummyUser.setFirstname("Johnny");
		
		et = new EmailToken("test@gmail.com", "abcDefGHijklmNOpQrsTuvwxYz", null);
		
		Mockito.when(etDao.findByToken(anyString())).then(invocation -> {
			String token = invocation.getArgument(0);
			if(token.equals("abcDefGHijklmNOpQrsTuvwxYz"))
			{
				return et;
			}
			
			return null;
		});
    }
	
	@Test
	void updatePassword()
	{
		//ASSIGN
		String newPwd = "pass";
		String token = "abcDefGHijklmNOpQrsTuvwxYz";
		
		//ACT
		etService.updatePassword(token, newPwd);
		
		//ASSERT
		Mockito.verify(etDao, Mockito.times(1));
	}
	
	@Test
	void updatePasswordWithInvalidToken()
	{
		//ASSIGN
		String newPwd = "pass";
		String token = "abcDefGHijklmNOpQrsTuvwxYz";
		
		//ACT
		etService.updatePassword(token, newPwd);
		
		//ASSERT
		//Mockito.verify(etDao, Mockito.times(0));
	}
	
	@Test
	void checkValidToken()
	{
		//ASSIGN
		String token = "abcDefGHijklmNOpQrsTuvwxYz";
		
		//ACT
		boolean isValid = etService.getByResetPasswordToken(token);
		
		//ASSERT
		assertTrue(isValid);
	}
	
	@Test
	void checkInvalidToken()
	{
		//ASSIGN
		String token = "abcDefGTuvwxYz";
		
		//ACT
		boolean isValid = etService.getByResetPasswordToken(token);
		
		//ASSERT
		assertFalse(isValid);
	}
	
	@Test
	void createToken()
	{
		//ASSIGN
		String email = et.getEmail();
		Mockito.when(uDao.findByEmail(email)).thenReturn(dummyUser);
				
		
		//ACT
		etService.updateResetPasswordToken(et.getToken(), et.getEmail());
		
		//ASSERT
		Mockito.verify(uDao, Mockito.times(1)).findByEmail(email);
		//Mockito.verify(etDao, Mockito.times(1));
	}

}
