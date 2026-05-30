package com.localhost.library.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomUserDetails implements UserDetails {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Long id, String name, String email, String username, String password,
                             Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() { return password; }

    @Override
    public String getUsername() { return username; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
    
    public static String PasswordEncoder(String plainPassword) {
    	String encodedPassword = null;
    	try {
    		 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
             encodedPassword = encoder.encode(plainPassword);
    	}catch(Exception e){
    		System.out.println("unable to password");
    		
    	}
    	
    	return encodedPassword;
    }
}


