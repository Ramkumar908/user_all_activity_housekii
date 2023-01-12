package com.ram.userService.service;

import java.text.ParseException;

import org.springframework.stereotype.Service;

import com.ram.userService.Model.User;
import com.ram.userService.util.ResponseStatus;

@Service
public interface UserRegisterService {
	
	
	public ResponseStatus  getUserRegister(User user) throws ParseException;

}
