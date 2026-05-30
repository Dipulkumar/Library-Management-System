package com.localhost.library.repository;

import com.localhost.library.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfo, Long> {

	UserInfo findByUsername(String username);
	UserInfo findBycontactnumber(String contactnumber);
	UserInfo findByEmailID(String EmailID);
}

