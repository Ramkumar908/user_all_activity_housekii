package com.ram.userService.DaoImpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.ram.userService.Dao.UserServiceDao;
import com.ram.userService.Model.User;
import com.ram.userService.Repository.UserRep;
import com.ram.userService.util.ResponseStatus;
import com.ram.userService.util.SendMailUtility;

@Repository
public class UserRegisterDaoImpl  implements UserServiceDao{

	@Autowired
	UserRep  repository;
	
	@Autowired
    JavaMailSender mailSender;
	
	@Autowired
	PasswordEncoder  passwordEncoder;
	
	
	@Autowired
	SendMailUtility utility;
	
	
	 @Value("${spring.mail.username}") private String sender;
	 Random rnd = new Random();
     int number = rnd.nextInt(999999);

     // this will convert any number sequence into 6 character.
     String otpCode= String.format("%06d", number);
	
	@Override
	public ResponseStatus userRegisterDao(User user) throws ParseException {
      User user1=repository.findByUsername(user.getUsername());
      
      String verifyLink="mail/verify/".concat(otpCode).concat("/").concat(user.getUsername());
      System.out.println("Link before sending Mail"+verifyLink);
		ResponseStatus registerStatus=new ResponseStatus();
		if(user1==null)
		{
			User phoneValidate=repository.findByPhone(user.getPhone());
			//System.out.println("UserPhone ->{}"+phoneValidate.toString());
			//System.out.println("Current UserDetails ->{}" +user.toString());
			if(phoneValidate!=null && phoneValidate.getPhone().equals(user.getPhone()))
			{
				//System.out.println("Condition Matched");
				registerStatus.setResponseCode(424);
				registerStatus.setResponseStatus("Register No Already in User");
				registerStatus.setStatusMessage("User Enter phone number already in use");
				return registerStatus;
			}
			if(user.getUsername().length()<5)
			{
				registerStatus.setResponseCode(421);
				registerStatus.setResponseStatus("User Name Length less than 5 char");
				registerStatus.setStatusMessage("UserNameLength Should be more than 5 character");
			   
			   
			}
			else if(user.getPassword().length()<5)
			{
				registerStatus.setResponseCode(422);
				registerStatus.setResponseStatus("User password Length less than 5 char");
				registerStatus.setStatusMessage("User Password Length Should be more than 5 character");
			}
			else {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				user.setisLoggedIn(false);
				//java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
			    Date date = new Date();  
			    String registeredDate=formatter.format(date);
			    System.out.println("String Date "+registeredDate);
			    user.setRegisteredDate(registeredDate);
				user.setOtpCode(otpCode);
				repository.save(user);
				ResponseStatus emailStatus=null;
				try {
					emailStatus=utility.SendEmail(user.getEmail(),verifyLink);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("After sending email"+emailStatus.getResponseCode());
				if(emailStatus.getResponseCode()==200)
				{
					registerStatus.setResponseCode(200);
					registerStatus.setResponseStatus("Register Successfully");
					registerStatus.setStatusMessage("erification code send on your Email "+user.getEmail());
				}
				else
				{
					registerStatus.setResponseCode(426);
					registerStatus.setResponseStatus("Mail Send Failed");
					registerStatus.setStatusMessage("Something went wrong while sending failed ");
				}
				
			}
		}
		else
		{
			registerStatus.setResponseCode(423);
			registerStatus.setResponseStatus("User Already Exist");
			registerStatus.setStatusMessage("Username "+user.getUsername()+" "+ "is alreasy Exist");
			
		}
		return registerStatus;

		
	}

	
	
public ResponseStatus SendEmailFromClass(String email) {
		
	MimeMessage mimeMessage = mailSender.createMimeMessage();
	 ResponseStatus status=new ResponseStatus();
	 try {
		 
         MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

         mimeMessageHelper.setSubject("Testing mail");
         mimeMessageHelper.setFrom(sender);
         mimeMessageHelper.setTo("ramk90806@gmail.com");
         boolean html = true;
         mimeMessageHelper.setText("<b>Hey guys</b>,<br><i>Welcome to my new home</i>", html);
          
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
