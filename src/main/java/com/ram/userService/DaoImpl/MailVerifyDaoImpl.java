package com.ram.userService.DaoImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ram.userService.Dao.MailVerifyDao;
import com.ram.userService.Model.User;
import com.ram.userService.Repository.UserRep;
import com.ram.userService.util.ResponseStatus;

@Repository
public class MailVerifyDaoImpl implements MailVerifyDao {

	@Autowired
	private UserRep repository;
	
	@Override
	public ResponseStatus mailVerifyDao(String otpCode,String username) {
		
		ResponseStatus status=new ResponseStatus();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
	    Date date = new Date();  
	    String registeredDate=formatter.format(date);
	    System.out.println("String Date "+registeredDate);
		User user=repository.findByUsername(username);
		if(user.getOtpCode().equals(otpCode))
		{
			user.setisLoggedIn(true);
		    user.setRegisteredDate(registeredDate);
			repository.save(user);
			status.setResponseCode(200);
			status.setResponseStatus("Otp match and Verified ");
			status.setStatusMessage("User Mail is Verified ");
			
		}
		else
		{
			status.setResponseCode(426);
			status.setResponseStatus("Otp not Match  ");
			status.setStatusMessage("Please enter correct Otp  ");	
		}
		
		
		return status;
	}

}
