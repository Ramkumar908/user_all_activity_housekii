package com.ram.userService.serviceImpl;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ram.userService.Dao.UserServiceDao;
import com.ram.userService.Model.User;
import com.ram.userService.service.UserRegisterService;
import com.ram.userService.util.ResponseStatus;

@Service
public class ServiceImpl  implements UserRegisterService{

	@Autowired
	UserServiceDao dao;
	@Override
	public ResponseStatus getUserRegister(User user) throws ParseException {
		
		
		
		return dao.userRegisterDao(user);
	}

}
