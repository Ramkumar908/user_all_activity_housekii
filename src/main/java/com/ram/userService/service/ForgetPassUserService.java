package com.ram.userService.service;

import org.springframework.stereotype.Service;

import com.ram.userService.util.ResponseStatus;

@Service
public interface ForgetPassUserService {

	
	public ResponseStatus  getUserForhetPass(String email);
}
