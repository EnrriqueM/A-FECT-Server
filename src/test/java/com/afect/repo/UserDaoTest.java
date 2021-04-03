package com.afect.repo;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.afect.model.User;
import com.afect.repository.UserDao;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserDaoTest {
	private User user;
	
	//@Autowired
    //private TestEntityManager entityManager;
     
    @Autowired
    private UserDao repository;
    
    @Before
    void init()
    {
    	user = new User();
    	user.setEmail("test@gmail.com");
    	user.setUsername("apples");
    	user.setPassword("123");
    }

	@Test
	void test() {
		//entityManager.persist(user);
		repository.save(user);
		
		User u = repository.findByUsername("apples");
		
		assertEquals(u.getEmail(), "test@gmail.com");
	}

}
