package com.localhost.library.repository;

import com.localhost.library.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);
    Admin findByContactnumber(String contactnumber);
    Admin findByEmailID(String emailID);
}


