package com.ram.userService.Dao;

import org.springframework.stereotype.Repository;

import com.ram.userService.util.ResponseStatus;

@Repository
public interface ForgetPassUserDao {
	
	public ResponseStatus  forgetUserPassDao(String email);

}
