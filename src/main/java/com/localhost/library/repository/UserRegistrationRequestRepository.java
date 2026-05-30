package com.localhost.library.repository;


import com.localhost.library.model.UserRegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRegistrationRequestRepository extends JpaRepository<UserRegistrationRequest, Long> {
	UserRegistrationRequest findByUsername(String username);
}
