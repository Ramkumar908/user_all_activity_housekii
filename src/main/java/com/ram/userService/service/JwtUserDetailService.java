package com.ram.userService.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ram.userService.Model.User;
import com.ram.userService.Model.UserDto;
import com.ram.userService.Repository.UserRep;
import com.ram.userService.util.ResponseStatus;

@Service
public class JwtUserDetailService  implements UserDetailsService{
	
	
	@Autowired
	UserRep  repository;
	
	@Autowired
	PasswordEncoder  passwordEncoder;
	
	
	
	public ResponseStatus saveUser(User user)
	{
		User user1=repository.findByUsername(user.getUsername());
		
		ResponseStatus registerStatus=new ResponseStatus();
		if(user1==null)
		{
			User phoneValidate=repository.findByPhone(user.getPhone());
			if(phoneValidate!=null && phoneValidate.getPhone()==user.getPhone())
			{
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
				repository.save(user);
				registerStatus.setResponseCode(200);
				registerStatus.setResponseStatus("Register Success");
				registerStatus.setStatusMessage("User Register is Successfully Done");
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


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=repository.findByUsername(username);
		if(user==null)
		{
			throw new UsernameNotFoundException("User not Found with UserName "+user.getUsername());
			
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),new ArrayList<>());
	}
}
