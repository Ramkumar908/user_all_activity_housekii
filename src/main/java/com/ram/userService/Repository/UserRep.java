package com.ram.userService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ram.userService.Model.User;

@Repository
public interface UserRep extends JpaRepository<User,Long> {


	User findByUsername(String username);

	User findByPhone(String phone);

	User findByEmail(String email);

}
