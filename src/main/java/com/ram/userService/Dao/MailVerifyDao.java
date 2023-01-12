package com.ram.userService.Dao;

import com.ram.userService.util.ResponseStatus;

public interface MailVerifyDao {
	
	public ResponseStatus mailVerifyDao(String username, String otpCode);

}
