package com.ram.userService.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SendMailUtility {

	@Autowired
    JavaMailSender mailSender;
	
	 @Value("${spring.mail.username}") private String sender;
	public ResponseStatus SendEmail(String email) throws MessagingException {
		
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		 ResponseStatus status=new ResponseStatus();
		 try {
			 
	         MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

	         mimeMessageHelper.setSubject("Email verification ");
	         mimeMessageHelper.setFrom(sender);
	         mimeMessageHelper.setTo(email);
	         
	          
	             mailSender.send(mimeMessage);
	             status.setResponseCode(200);
	             status.setResponseStatus("Mail Send Successfully");
				 status.setStatusMessage("User Registraton successfully");
				 System.out.println("In sendEMail "+status);

	     } catch (MessagingException e) {
	     	status.setResponseCode(426);
			   status.setResponseStatus("Email Send Failed and Register Unsuccess");
			   status.setStatusMessage("Uable to send Mai not get any Response");    
			   
	     }
			return status;
		
}

	

}
