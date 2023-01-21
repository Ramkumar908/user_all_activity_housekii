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
   private static final String verifyUrl="http://localhost:8100";
	public ResponseStatus SendEmail(String email, String verifyLink) throws MessagingException {
		
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		 ResponseStatus status=new ResponseStatus();
		 try {
			 
	         MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

	         mimeMessageHelper.setSubject("Email verification ");
	         mimeMessageHelper.setFrom(sender);
	         mimeMessageHelper.setTo(email);
	         boolean html = true;
	         mimeMessageHelper.setText("<b>Hey guys Please click the link for verify </b>,<br><br><br><br><a href=\""+verifyUrl+"/"+verifyLink+"\">Please click the link to verify </a>", html);
	         
	          
	         mailSender.send(mimeMessage);
	             status.setResponseCode(200);
	             status.setResponseStatus("Mail Send Successfully");
				 status.setStatusMessage("User Registraton successfully and code send your registered mail Id");
				 System.out.println("In sendEMail "+status);

	     } catch (MessagingException e) {
	     	status.setResponseCode(426);
			   status.setResponseStatus("Email Send Failed and Register Unsuccess");
			   status.setStatusMessage("Uable to send Mai not get any Response");    
			   
	     }
			return status;
		
}

	

}
