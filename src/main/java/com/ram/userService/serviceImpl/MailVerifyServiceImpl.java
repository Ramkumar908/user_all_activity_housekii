package com.ram.userService.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ram.userService.Dao.MailVerifyDao;
import com.ram.userService.service.MailerifyUser;
import com.ram.userService.util.ResponseStatus;

@Service
public class MailVerifyServiceImpl  implements MailerifyUser{

	
	@Autowired
	private MailVerifyDao dao;
	@Override
	public ResponseStatus getMailVerified(String otpCode,String username) {
		// TODO Auto-generated method stub
		return dao.mailVerifyDao(otpCode,username);
	}

}
