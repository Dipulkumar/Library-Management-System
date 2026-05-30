package com.localhost.library.controller;

import com.localhost.library.model.Admin;
import com.localhost.library.model.UserInfo;
//import com.localhost.library.model.UserInfo;
import com.localhost.library.model.UserRegistrationRequest;
import com.localhost.library.security.CustomUserDetails;
import com.localhost.library.service.AdminService;
import com.localhost.library.service.UserRegistrationRequestService;
import com.localhost.library.service.UserService;
import java.util.Base64;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class UserRegistrationController {

    @Autowired
    private UserRegistrationRequestService service;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AdminService adminService;
    


    @PreAuthorize("permitAll()")
    @GetMapping("/registration")
    public String showForm(Model model) {       
    	if (!model.containsAttribute("registrationRequest")) {
	        model.addAttribute("registrationRequest", new UserRegistrationRequest());
	    }
        return "registration_form";
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/registration/submit")
    public String submitRequest(@ModelAttribute("registrationRequest") UserRegistrationRequest registrationRequest, RedirectAttributes redirectAttributes) {
        boolean isUserNameExist = false;
        boolean isEmailIDExist = false;
        boolean isContactNumberExist = false;
    	String userName = registrationRequest.getUsername();
        UserInfo user = userService.getUserByUsername(userName);
        if (user == null) {
        	Admin admin = adminService.getUserByUsername(userName);
        	
        	if(admin != null) {
        		isUserNameExist = true;
        	}
        }else {
        	isUserNameExist = true;
        }
        
        String EmailID = registrationRequest.getEmailID();
        user = userService.getUserByUsername(EmailID);
        if (user == null) {
        	Admin admin = adminService.getUserByUsername(EmailID);
        	
        	if(admin != null) {
        		isEmailIDExist = true;
        	}
        }else {
        	isEmailIDExist = true;
        }
        
        String contactnumber = registrationRequest.getContactnumber();
        user = userService.getUserByUsername(contactnumber);
        if (user == null) {
        	Admin admin = adminService.getUserByUsername(contactnumber);
        	
        	if(admin != null) {
        		isContactNumberExist = true;
        	}
        }else {
        	isContactNumberExist = true;
        }
        
        if(isUserNameExist || isEmailIDExist || isContactNumberExist ) {
        	 if(isUserNameExist) {
        		 redirectAttributes.addFlashAttribute("error", "User already exists, please choose another username.");
        	 }
        	 if(isEmailIDExist) {
        		 redirectAttributes.addFlashAttribute("error", "User already exists, please choose another EmailID.");
        	 }
        	 
        	 if(isContactNumberExist) {
        		 redirectAttributes.addFlashAttribute("error", "User already exists, please choose another contact number.");
        	 }
        	 
             redirectAttributes.addFlashAttribute("registrationRequest", registrationRequest);
        	return "redirect:/registration";
        }
        	
    	String encodePassword = registrationRequest.getPassword();
    	System.out.println("-------------------encodePassword : "+ encodePassword);      
        byte[] decodedBytes = Base64.getDecoder().decode(encodePassword);

        // Convert bytes to String
        String decodedPassword = new String(decodedBytes);
    	System.out.println("-------------------decodedPassword : "+ decodedPassword);      
        registrationRequest.setPassword(CustomUserDetails.PasswordEncoder(decodedPassword));
        
    	service.submitRequest(registrationRequest);
        return "redirect:/login";
    }

    @PreAuthorize("hasAuthority('ALL')")
    @GetMapping("/registration/list")  
    public String listRequests(Model model) {
        model.addAttribute("requests", service.getAllRequests());
        return "registration_list";
    }

    @PreAuthorize("hasAuthority('ALL')")
    @PostMapping("/registration/approve/{id}")
    public String approveRequest(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        service.approveRequest(id,userDetails.getId(),1); // You can replace 1L with actual verifierId
        return "redirect:/registration/list";
    }
    
    @PreAuthorize("hasAuthority('ALL')")
    @PostMapping("/registration/reject/{id}")
    public String rejectRequest(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        service.approveRequest(id, userDetails.getId(),2); // You can replace 1L with actual verifierId
        return "redirect:/registration/list";
    }
}
