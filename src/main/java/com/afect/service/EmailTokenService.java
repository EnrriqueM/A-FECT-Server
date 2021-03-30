package com.afect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afect.model.EmailToken;
import com.afect.model.User;
import com.afect.repository.EmailTokenDao;
import com.afect.repository.UserDao;

@Service
public class EmailTokenService {
	private EmailTokenDao etDao;
	private UserDao uDao;

	public EmailTokenService() {
		super();
	}

	@Autowired
	public EmailTokenService(EmailTokenDao etDao, UserDao uDao) {
		super();
		this.etDao = etDao;
		this.uDao = uDao;
	}
	
	/*
	 * Creates a new Token for the given email
	 * */
	public void updateResetPasswordToken(String token, String email){
		
		//Attempt to find a user
		User u = uDao.findByEmail(email);
		
		if(u != null)
		{
			//CREATE TOKEN
			EmailToken currentToken = new EmailToken(email, token);
			etDao.save(currentToken);
			
		}
		else
		{
			//User DNE THROW EXCEPTION
		}
    }
	
     /*
      * Checks if a token exists
      * */
    public boolean getByResetPasswordToken(String token) 
    {
    	EmailToken et = etDao.findByToken(token);
    	
    	if(et != null)
    	{
    		return true;
    	}
    	
    	//THROW EXCEPTION
        return false;
    }
     
    /*
     * Update the user's password
     * */
    public void updatePassword(String token, String newPassword) 
    {
    	EmailToken et = etDao.findByToken(token);
    	
    	if(et != null)
    	{
    		//if Token exists, find user
    		User u = uDao.findByEmail(et.getEmail());
    		
    		//Update user and delete token
    		if(u != null)
    		{
    			u.setPassword(newPassword);
    			uDao.save(u);
    			
    			etDao.delete(et);
    		}
    	}
    }
	
	

}
