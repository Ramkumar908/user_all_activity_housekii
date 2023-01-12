package com.ram.userService.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ram.userService.Dao.ForgetPassUserDao;
import com.ram.userService.service.ForgetPassUserService;
import com.ram.userService.util.ResponseStatus;

@Service
public class ForgetUserPassServieImpl implements  ForgetPassUserService {

	@Autowired
	ForgetPassUserDao fogetUserDao;
	
	@Override
	public ResponseStatus getUserForhetPass(String email) {
		// TODO Auto-generated method stub
		return fogetUserDao.forgetUserPassDao(email);
	}

}
