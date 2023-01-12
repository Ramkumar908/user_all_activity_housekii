package com.ram.userService.Dao;

import com.ram.userService.Model.UpdatePassRequest;
import com.ram.userService.util.ResponseStatus;


public interface UpdatePassDao {
	
	public ResponseStatus updateUserPass(UpdatePassRequest request);

}
