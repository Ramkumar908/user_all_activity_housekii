package com.ram.userService.Dao;

import java.text.ParseException;

import com.ram.userService.Model.User;
import com.ram.userService.util.ResponseStatus;

public interface UserServiceDao {
	
	public ResponseStatus  userRegisterDao(User user) throws ParseException;

}
