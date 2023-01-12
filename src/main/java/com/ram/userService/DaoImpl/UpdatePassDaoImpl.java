package com.ram.userService.DaoImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.ram.userService.Dao.UpdatePassDao;
import com.ram.userService.Model.UpdatePassRequest;
import com.ram.userService.Model.User;
import com.ram.userService.Repository.UserRep;
import com.ram.userService.util.ResponseStatus;

@Repository
public class UpdatePassDaoImpl  implements UpdatePassDao{

	@Autowired
	UserRep  repository;
	
	@Autowired
	PasswordEncoder  passwordEncoder;
	@Override
	public ResponseStatus updateUserPass(UpdatePassRequest request) {
		
		User user=repository.findByUsername(request.getUsername());
		ResponseStatus status=new ResponseStatus();
		// TODO Auto-generated method stub
		if(user==null)
		{
			status.setResponseCode(429);
			status.setResponseStatus("User Not Exist");
			status.setStatusMessage("user with username "+request.getUsername()+"not exist");
		}
		else
		{
			user.setPassword(passwordEncoder.encode(request.getPassword()));
			  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
			    Date date = new Date();  
			    String registeredDate=formatter.format(date);
			    user.setRegisteredDate(registeredDate);
			    repository.save(user);
			    status.setResponseCode(200);
				status.setResponseStatus("User password Updated");
				status.setStatusMessage("user with username "+request.getUsername()+"Password Updated Successfully");
			    
		}
		return status;
	}

}
