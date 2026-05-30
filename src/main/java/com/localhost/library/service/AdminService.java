package com.localhost.library.service;

import com.localhost.library.model.Admin;
import com.localhost.library.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder; // <-- inject encoder

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }
    
    public Admin getUserByContactnumber(String contactnumber) {
        return adminRepository.findByUsername(contactnumber);
    }
    
    public Admin getUserByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
    
    public Admin getUserByEmailID(String emailID) {
        return adminRepository.findByUsername(emailID);
    }

    // ✅ Create Admin with encoded password
    public Admin createAdmin(Admin admin) {
        if (admin.getPassword() != null && !admin.getPassword().isBlank()) {
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        }
        admin.setCreatedAt(new java.util.Date()); // set current time
        return adminRepository.save(admin);
    }

    public Admin updateAdmin(Long id, Admin updatedAdmin) {
        return adminRepository.findById(id).map(admin -> {
            admin.setUsername(updatedAdmin.getUsername());
            if (updatedAdmin.getPassword() != null && !updatedAdmin.getPassword().isBlank()) {
                String pwd = updatedAdmin.getPassword();
                boolean looksEncoded = pwd.startsWith("$2a$") || pwd.startsWith("$2b$") || pwd.startsWith("$2y$");
                admin.setPassword(looksEncoded ? pwd : passwordEncoder.encode(pwd));
            }
            
            admin.setName(updatedAdmin.getName());
            admin.setPermanetAddress(updatedAdmin.getPermanetAddress());
            admin.setLocalAddress(updatedAdmin.getLocalAddress());
            admin.setContactnumber(updatedAdmin.getContactnumber());
            admin.setEmailID(updatedAdmin.getEmailID());
            admin.setRole(updatedAdmin.getRole());
            return adminRepository.save(admin);
        }).orElse(null);
    }

    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }
}


