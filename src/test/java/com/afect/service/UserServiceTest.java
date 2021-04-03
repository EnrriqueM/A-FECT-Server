package com.afect.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.afect.model.User;
import com.afect.repository.UserDao;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {
	private User dummyUser;
	
    @Mock
    UserDao uDao;
    
	@InjectMocks
    UserService uService;
     
	
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
    }
	
	@Test
	void fetchUserById()
	{
		//ASSIGN
		int id = 100;
		Optional<User> dummyUser2 = Optional.ofNullable(new User());
		Mockito.when(uDao.findById(id)).thenReturn(dummyUser2);
		
		//ACT
		User u2 = uService.getUserById(id);
		
		//ASSERT
		assertNotNull(u2);
		Mockito.verify(uDao, Mockito.times(1)).findById(id);
	}

	@Test
	void fetchUserByEmail() {
		//ASSIGN
		String email = "test@gmail.com";
		Mockito.when(uDao.findByEmail(email)).thenReturn(dummyUser);
		
		//ACT
		User u2 = uService.getUserByEmail(email);
		
		//ASSERT
		assertNotNull(u2);
		Mockito.verify(uDao, Mockito.times(1)).findByEmail(email);
	}
	
	@Test
	void fetchUserByUsernameAndPassword()
	{
		//ASSIGN
		String username = "test";
		String password = "123";
		Mockito.when(uDao.findByUsernameAndPassword(username, password)).thenReturn(dummyUser);
		
		//ACT
		User u2 = uService.getUsernameAndPassword(username, password);
		
		//ASSERT
		assertNotNull(u2);
		Mockito.verify(uDao, Mockito.times(1)).findByUsernameAndPassword(username, password);
	}
	
	@Test
	void fetchUserByUsername() {
		//ASSIGN
		String un = "test";
		Mockito.when(uDao.findByUsername(un)).thenReturn(dummyUser);
		
		//ACT
		User u2 = uService.getUserByUsername(un);
		
		//ASSERT
		assertNotNull(u2);
		Mockito.verify(uDao, Mockito.times(1)).findByUsername(un);
	}
	
	@Test
	void fetchUserByFirstname() {
		//ASSIGN
		String un = "Johnny";
		List<User> dummyList = new ArrayList<>();
		dummyList.add(dummyUser);
		Mockito.when(uDao.findByFirstname(un)).thenReturn(dummyList);
		
		//ACT
		List<User> u2 = uService.getUserByFirstname(un);
		
		//ASSERT
		assertEquals(dummyList.size(), u2.size());	//EXPECTED VS ACTUAL
		Mockito.verify(uDao, Mockito.times(1)).findByFirstname(un);
	}
	
	@Test
    public void fetchAllUsers()
    {
		//ASSIGN
		List<User> dummyList = new ArrayList<>();
		dummyList.add(dummyUser);
		Mockito.when(uDao.findAll()).thenReturn(dummyList);
		
		//ACT
		List<User> uList= uService.getAllUsers();
		
        //ASSERT
		assertEquals(dummyList.size(), uList.size());	//EXPECTED VS ACTUAL
        Mockito.verify(uDao, Mockito.times(1)).findAll();
    }
	
	@Test
    public void fetchUsersWithRelatedFirstnames()
    {
		//ASSIGN
		String wantedFirstname = "John";
		User secondUser = new User();
		secondUser.setFirstname(wantedFirstname);
		List<User> dummyList = new ArrayList<>();
		dummyList.add(dummyUser);
		dummyList.add(secondUser);
		Mockito.when(uDao.findByFirstnameContainingIgnoreCase(wantedFirstname)).thenReturn(dummyList);
		
		//ACT
		List<User> uList= uService.getUsersByFirstname(wantedFirstname);
		
        //ASSERT
		assertEquals(dummyList.size(), uList.size());	//EXPECTED VS ACTUAL
        Mockito.verify(uDao, Mockito.times(1)).findByFirstnameContainingIgnoreCase(wantedFirstname);
    }
	
	@Test
    public void fetchUsersWithRelatedUsernames()
    {
		//ASSIGN
		String wantedUsername = "testing";
		User secondUser = new User();
		secondUser.setFirstname(wantedUsername);
		List<User> dummyList = new ArrayList<>();
		dummyList.add(dummyUser);
		dummyList.add(secondUser);
		Mockito.when(uDao.findByUsernameContainingIgnoreCase(wantedUsername)).thenReturn(dummyList);
		
		//ACT
		List<User> uList= uService.getUsersByUsername(wantedUsername);
		
        //ASSERT
		assertEquals(dummyList.size(), uList.size());	//EXPECTED VS ACTUAL
        Mockito.verify(uDao, Mockito.times(1)).findByUsernameContainingIgnoreCase(wantedUsername);
    }
	
	@Test
    public void createUser()
    {
        User u = new User();
        u.setEmail("test@gmail.com");
         
        uService.insertUser(u);
         
        Mockito.verify(uDao, Mockito.times(1)).save(u);
    }
	
	@Test
    public void updateUser()
    {
        dummyUser.setEmail("updateTest@gmail.com");
         
        uService.updateUser(dummyUser);
         
        Mockito.verify(uDao, Mockito.times(1)).save(dummyUser);
    }
	
	@Test
    public void removeUser()
    {
        User u = new User();
        u.setEmail("test@gmail.com");
         
        uService.deleteUser(u);
         
        Mockito.verify(uDao, Mockito.times(1)).delete(u);
    }

}
