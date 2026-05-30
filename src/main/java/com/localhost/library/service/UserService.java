package com.localhost.library.service;

import com.localhost.library.model.UserInfo;
import com.localhost.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private static final LocalDateTime LocalDateTime = null;
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

    // Get all users
    public List<UserInfo> getAllUsers() {
        return userRepository.findAll();
    }

    // Add new user
    public UserInfo addUser(UserInfo user) {
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(user.getPassword())); // ✅ Password is encoded
        }
        user.setCreatedAt(LocalDateTime.now()); // ✅ Current timestamp
        user.setMembershipDate(LocalDate.now()); // ✅ Today's date
        return userRepository.save(user); // ✅ Save user
    }


    // Get user by ID
    public UserInfo getUserById(Long userId) {
        return userRepository.findById(userId).orElseGet(null);
    }
    
    public UserInfo getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public UserInfo getUserByContactnumber(String contactnumber) {
        return userRepository.findBycontactnumber(contactnumber);
    }
    
    public UserInfo getUserByEmailID(String emailID) {
        return userRepository.findByEmailID(emailID);
    }


    // Update user
    public UserInfo updateUser(Long userId, UserInfo updatedUser) {
        return userRepository.findById(userId).map(existingUser -> {
        	 if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
                 String pwd = updatedUser.getPassword();
                 boolean looksEncoded = pwd.startsWith("$2a$") || pwd.startsWith("$2b$") || pwd.startsWith("$2y$");
                 existingUser.setPassword(looksEncoded ? pwd : passwordEncoder.encode(pwd));
             }
            existingUser.setName(updatedUser.getName());
            existingUser.setDOB(updatedUser.getDOB());
            existingUser.setIDtype(updatedUser.getIDtype());
            existingUser.setIDnumber(updatedUser.getIDnumber());
            existingUser.setEducation(updatedUser.getEducation());
            existingUser.setPermanent_address(updatedUser.getPermanent_address());
            existingUser.setLocal_address(updatedUser.getLocal_address());
            existingUser.setContactnumber(updatedUser.getContactnumber());
            existingUser.setEmailID(updatedUser.getEmailID());
            existingUser.setCreatedAt(updatedUser.getCreatedAt());
            existingUser.setMembershipDate(updatedUser.getMembershipDate());
            return userRepository.save(existingUser);
        }).orElseGet(null);
    }

    // Delete user by ID
    public boolean deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
    
   
}

