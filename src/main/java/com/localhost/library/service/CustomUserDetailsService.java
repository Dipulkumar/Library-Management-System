package com.localhost.library.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.localhost.library.model.Admin;
import com.localhost.library.model.UserInfo;
import com.localhost.library.repository.UserRepository;
import com.localhost.library.security.CustomUserDetails;
import com.localhost.library.repository.AdminRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        UserInfo user = userRepository.findByUsername(username);
        if (user == null) {
        	Admin admin = adminRepository.findByUsername(username);
        	
        	if(admin == null) {
        		throw new UsernameNotFoundException("User not found");
        	}else {
        		
        		List<GrantedAuthority> authorities = new ArrayList<>();
        		String adminRole = admin.getRole();
        		authorities.add(new SimpleGrantedAuthority("ROLE_" + adminRole.toUpperCase()));
        		
        		String adminPrivilege = admin.getPrivilege();
        		String[] adminPrivileges = adminPrivilege.split(",");
        		// Add privileges
    	        for (String p : adminPrivileges) {
    	            authorities.add(new SimpleGrantedAuthority(p));
    	        }
    	        
        		 return new CustomUserDetails(
        				 admin.getId(),
        				 admin.getName(),
        				 admin.getEmailID(),
        				 admin.getUsername(),
        				 admin.getPassword(),
        				 authorities
		          );
        	}
            
        }else {
        	 // Convert our User to Spring Security's UserDetails
            return new CustomUserDetails(
            	user.getId(),
            	user.getName(),
            	user.getEmailID(),
    	        user.getUsername(),
    	        user.getPassword(),
    	        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
            );
        }

       
    }
}

