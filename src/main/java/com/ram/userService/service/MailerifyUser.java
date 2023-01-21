package com.ram.userService.service;

import org.springframework.stereotype.Service;

import com.ram.userService.util.ResponseStatus;

@Service
public interface MailerifyUser {
	
	public ResponseStatus getMailVerified(String otpCode,String username);

}
