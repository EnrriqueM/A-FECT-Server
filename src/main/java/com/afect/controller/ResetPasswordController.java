package com.afect.controller;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afect.model.EmailToken;
import com.afect.service.EmailTokenService;

import net.bytebuddy.utility.RandomString;

@RestController
@RequestMapping(value="/api/reset_password")
@CrossOrigin(origins = "*")
public class ResetPasswordController {

	private EmailTokenService etService;
    private JavaMailSender mailSender;
    private static final Logger LOGGER = Logger.getLogger(ResetPasswordController.class);

	public ResetPasswordController() {
		super();
		LOGGER.info("Init Post Controller");
	}

	@Autowired
	public ResetPasswordController(EmailTokenService etService, JavaMailSender mailSender) {
		super();
		this.etService = etService;
		this.mailSender = mailSender;
		LOGGER.info("Auto Init Post Controller");
	}
	
	/*
	 * Create a new token
	 * */
	@PostMapping("/forgot_password/{email}")
    public ResponseEntity<String> processForgotPassword(HttpServletRequest request, @PathVariable("email")  String email)
	{
		//Create a random string of characters to be a "token"
	    String token = RandomString.make(30);
	     
	    try {
	    	etService.updateResetPasswordToken(token, email);
	    	//Get this url and append the create token
	    	/*String domain_url = request.getRequestURL().toString();
	    	domain_url = domain_url.replace(request.getServletPath(), "");
	    	//getRequestURL - gets the entire url ex)http://localhost3000/api/...
	    	//getServletPath - get servlet path ex) /api/...
	    	System.out.println("getRequestURL: " + request.getRequestURL() + " servlet Path: " + request.getServletPath());
	    	*/
	    	//getHeader("referer") - gets source URL domain ex) http://localhost3000/
	    	String domain_url = request.getHeader("referer");
	        //String resetPasswordLink = domain_url + "/reset_password?token=" + token;
	    	String resetPasswordLink = domain_url + "reset_password/" + token;
	    	LOGGER.info("User with email: " + email + " requested to update password. Token: " + token);
	        sendEmail(email, resetPasswordLink);
	         
	    } /*catch (CustomerNotFoundException ex) {
	        model.addAttribute("error", ex.getMessage());
	    } catch (UnsupportedEncodingException | MessagingException e) {
	        model.addAttribute("error", "Error while sending email");
	    }*/
	    catch(Exception err)
	    {
	    	err.printStackTrace();
	    	LOGGER.error(err.getMessage());
	    }
	         
	    return new ResponseEntity<String>("Resource was created", HttpStatus.CREATED);
    }
     
	/*
	 * Prepares an email
	 * Called by servlet
	 * */
    public void sendEmail (String recipientEmail, String link)
	{
    	System.out.println("Starting Email");
    	MimeMessage message = mailSender.createMimeMessage();              
        MimeMessageHelper helper = new MimeMessageHelper(message);
         
        try 
        {
			helper.setFrom("support@quix.website", "AFect Support");
			helper.setTo(recipientEmail);
			
			String subject = "Here's the link to reset your password";
	         
	        String content = "<h3>Hello,</h3>"
	                + "<p>You have requested to reset your password.</p>"
	                + "<p>Click the link below to change your password:</p>"
	                + "<p><a href=\"" + link + "\">Change my password</a></p>"
	                + "<br>"
	                + "<p>Ignore this email if you do remember your password, "
	                + "or you have not made the request.</p>";
	         
	        helper.setSubject(subject);
	         
	        //(String: test/html, boolean: isHTML?)
	        helper.setText(content, true);
	         
	        mailSender.send(message);
	        System.out.println("Email sent");
		} 
        catch (UnsupportedEncodingException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
    }  
     
     /*
      * When user loads email link
      * */
    @PostMapping("/validateToken")
    public ResponseEntity<String> showResetPasswordForm(@RequestBody EmailToken token) {
    	System.out.println("in reset pass, token: " + token.getToken());
    	boolean isValidToken = etService.getByResetPasswordToken(token.getToken());
    	
    	
    	if(!isValidToken)
    	{
    		LOGGER.warn("recevied invalid token of " + token.getToken());
    		return new ResponseEntity<>("Not found", HttpStatus.BAD_REQUEST);
    	}
    	
    	//Return user id
		return new ResponseEntity<>("Token ACCEPTED", HttpStatus.OK);
    }
     
    /*
     * This will Reset the user's password
     *  */
    @PostMapping
    public ResponseEntity<String> processResetPassword(@RequestBody LinkedHashMap<String,String> pwdForm) 
    {
    	String pwd = pwdForm.get("newPassword");
    	String token = pwdForm.get("token");
    	etService.updatePassword(token, pwd);
    	
    	LOGGER.info("Attempt to update user password of token " + token);
    	
    	//Return user id
		return new ResponseEntity<>("Updated", HttpStatus.OK);
    }
	
	
}
