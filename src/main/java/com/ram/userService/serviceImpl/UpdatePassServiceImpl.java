package com.ram.userService.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ram.userService.Dao.UpdatePassDao;
import com.ram.userService.Model.UpdatePassRequest;
import com.ram.userService.service.UpdatePassService;
import com.ram.userService.util.ResponseStatus;
@Service
public class UpdatePassServiceImpl  implements UpdatePassService{

	@Autowired
	UpdatePassDao dao;
	@Override
	public ResponseStatus updateUserPass(UpdatePassRequest request) {
		
		return dao.updateUserPass(request);
	}

}
