package com.afect.controller;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afect.model.User;
import com.afect.service.EmailTokenService;

import net.bytebuddy.utility.RandomString;

@RestController
@RequestMapping(value="/api/reset_password")
@CrossOrigin(origins = "*")
public class ResetPasswordController {

	private EmailTokenService etService;
    private JavaMailSender mailSender;

	public ResetPasswordController() {
		super();
	}

	@Autowired
	public ResetPasswordController(EmailTokenService etService, JavaMailSender mailSender) {
		super();
		this.etService = etService;
		this.mailSender = mailSender;
	}
	
	/*
	 * Create a new token
	 * */
	@PostMapping("/forgot_password/{email}")
    public ResponseEntity<String> processForgotPassword(HttpServletRequest request, @PathVariable("email")  String email)
	{
	    String token = RandomString.make(30);
	    
	    System.out.println("RECIEVEC: " + email + " Token: " + token);
	     
	    try {
	    	etService.updateResetPasswordToken(token, email);
	    	//Get this url and append the create token
	    	String domain_url = request.getRequestURL().toString();
	    	domain_url = domain_url.replace(request.getServletPath(), "");
	        String resetPasswordLink = "http://localhost:3000" + "/reset_password?token=" + token;
	        sendEmail(email, resetPasswordLink);
	        System.out.println(domain_url);
	         
	    } /*catch (CustomerNotFoundException ex) {
	        model.addAttribute("error", ex.getMessage());
	    } catch (UnsupportedEncodingException | MessagingException e) {
	        model.addAttribute("error", "Error while sending email");
	    }*/
	    catch(Exception err)
	    {
	    	err.printStackTrace();
	    }
	         
	    return new ResponseEntity<String>("Resource was created", HttpStatus.CREATED);
    }
     
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
	         
	        String content = "<p>Hello,</p>"
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
		}
    }  
     
     /*
      * When user clicks email link
      * */
    @GetMapping("/reset_password")
    public ResponseEntity<String> showResetPasswordForm(@Param(value = "token") String token) {
    	User u = etService.getByResetPasswordToken(token);
    	
    	if(u == null)
    	{
    		return new ResponseEntity<>("Not found", HttpStatus.BAD_REQUEST);
    	}
    	
    	//Return user id
		return new ResponseEntity<>(token, HttpStatus.OK);
    }
     
    /*
     * This will Reset the user's password
     * Also, Validates the key to make sure it is a valide request
     *  */
    @PostMapping
    public ResponseEntity<String> processResetPassword(@Param("token") String token, @RequestBody LinkedHashMap<String,String> pwd) 
    {
    	/*User u = etService.getByResetPasswordToken(token);
    	
    	if(u == null)
    	{
    		return new ResponseEntity<>("Not Valid", HttpStatus.BAD_REQUEST);
    	}
    	
    	etService.updatePassword(u.getEmail(), pwd);*/
    	
    	System.out.println("Token: " + token);
    	System.out.println("Password: " + pwd.get("password"));
    	
    	//Return user id
		return new ResponseEntity<>("Updated", HttpStatus.OK);
    }
	
	
}
