package com.localhost.library.model;

import jakarta.persistence.*;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long admin_id;

    private String username;
    private String password;
    private String name;

    private String permanet_address;
    private String local_address;
    private String contactnumber;
    private String emailID;
    private String role;
    private String privilege;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date createdAt;


    // Getters and Setters

    public Long getId() {
        return admin_id;
    }

    public void setId(Long admin_id) {
        this.admin_id = admin_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPermanetAddress() {
        return permanet_address;
    }

    public void setPermanetAddress(String permanet_address) {
        this.permanet_address = permanet_address;
    }

    public String getLocalAddress() {
        return local_address;
    }

    public void setLocalAddress(String local_address) {
        this.local_address = local_address;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privileges) {
        this.privilege = privileges;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}


