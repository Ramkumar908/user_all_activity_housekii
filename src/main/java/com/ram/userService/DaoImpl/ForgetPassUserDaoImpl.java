package com.ram.userService.DaoImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ram.userService.Dao.ForgetPassUserDao;
import com.ram.userService.Model.User;
import com.ram.userService.Repository.UserRep;
import com.ram.userService.util.ResponseStatus;
import com.ram.userService.util.SendMailUtility;

@Repository
public class ForgetPassUserDaoImpl  implements ForgetPassUserDao{

	@Autowired
	UserRep  repo;
	
	
	@Autowired
	SendMailUtility utiity;
	@Override
	public ResponseStatus forgetUserPassDao(String email) {
		
		 Random rnd = new Random();
	     int number = rnd.nextInt(999999);

	     // this will convert any number sequence into 6 character.
	        
	     String otpCode= String.format("%06d", number);
		 User user=repo.findByEmail(email);
		 ResponseStatus status=new ResponseStatus();
		if(user==null)
		{
		    status.setResponseCode(429);
		    status.setResponseStatus("User not found");
		    status.setStatusMessage("User with email "+email+" is not exist please Sign Up");
		}
		else
		{
		        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
			    Date date = new Date();  
			    String registeredDate=formatter.format(date);
		     user.setOtpCode(otpCode);
		     user.setRegisteredDate(registeredDate);
		     repo.save(user);
			try {
				System.out.println("Before Mail Send the error we get");
				status=utiity.SendEmail(email);
				int StatusCode=status.getResponseCode();
				if(StatusCode==200)
				{
					     status.setResponseCode(200);
					    status.setResponseStatus("Mail send Successfully");
					    status.setStatusMessage("Send Successfully ");
				}
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				 status.setResponseCode(426);
				    status.setResponseStatus("Mail send Failed");
				    status.setStatusMessage("Getting some issue while sending email");
			}
		}
		
		return status;
	}

}
