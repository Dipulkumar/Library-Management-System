package com.localhost.library.service;

import com.localhost.library.model.Admin;
import com.localhost.library.model.UserInfo;
import com.localhost.library.repository.AdminRepository;
import com.localhost.library.model.UserRegistrationRequest;
import com.localhost.library.repository.UserRegistrationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRegistrationRequestService {

    @Autowired
    private UserRegistrationRequestRepository userRequestRepository;
    


    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AdminRepository adminRepository;

    public void submitRequest(UserRegistrationRequest request) {
    	userRequestRepository.save(request);
    }

    public List<UserRegistrationRequest> getAllRequests() {
        return userRequestRepository.findAll();
    }

    public UserRegistrationRequest getById(Long id) {
        return userRequestRepository.findById(id).orElse(null);
    }
    
    public UserRegistrationRequest getByUserName(String username) {
        return userRequestRepository.findByUsername(username);
    }

    public void approveRequest(Long id, Long verifierId,int approvalStatus) {
        UserRegistrationRequest registrationRequest = getById(id);
        Admin verifier = adminRepository.findById(verifierId).orElse(null);
        UserInfo savedUser = null;
        if(registrationRequest != null && approvalStatus == 1) {
        	try {
        		UserInfo user = new UserInfo();
        		user.setName(registrationRequest.getName());
        		user.setDOB(registrationRequest.getDOB());
        		user.setEducation(registrationRequest.getEducation());
        		user.setContactnumber(registrationRequest.getContactnumber());
        		user.setEmailID(registrationRequest.getEmailID());
        		user.setIDnumber(registrationRequest.getIDnumber());
        		user.setIDtype(registrationRequest.getIDtype());
        		user.setLocal_address(registrationRequest.getLocal_address());
        		user.setPermanent_address(registrationRequest.getPermanent_address());
        		user.setUsername(registrationRequest.getUsername());
        		user.setPassword(registrationRequest.getPassword());
                savedUser = userService.addUser(user);
            	registrationRequest.setUser(savedUser);
        	} catch (Exception e) {
        	    // Code to handle the exception
        		System.out.println("unable to create user" + e.getMessage());
        	}

        }
        if (registrationRequest != null ) {
        	registrationRequest.setVerifier(verifier); // Admin object must be fetched or constructed
        	registrationRequest.setVerifiedDate(java.time.LocalDateTime.now());
        	registrationRequest.setApprovalStatus(approvalStatus);
            userRequestRepository.save(registrationRequest);
        }
    }
}

