package com.ram.userService.service;

import org.springframework.stereotype.Service;

import com.ram.userService.Model.UpdatePassRequest;
import com.ram.userService.util.ResponseStatus;

@Service
public interface UpdatePassService {

	
	public ResponseStatus updateUserPass(UpdatePassRequest request);
}
