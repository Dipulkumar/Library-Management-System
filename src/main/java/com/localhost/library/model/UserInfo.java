package com.localhost.library.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    
    private String username;

    private String password;

    private String name;
    
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date DOB;
    
    private String IDtype;
    private String IDnumber;
    private String Education;
    private String permanent_address;
    private String local_address;
    private String contactnumber;
    private String emailID;
    
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;
 
    private LocalDate membershipDate;



    // Getters and setters
    public Long getId() { return user_id; }
    public void setId(Long user_id) { this.user_id = user_id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Date getDOB() { return DOB; }
    public void setDOB(Date DOB) { this.DOB = DOB; }

    public String getIDtype() { return IDtype; }
    public void setIDtype(String IDtype) { this.IDtype = IDtype; }

    public String getIDnumber() { return IDnumber; }
    public void setIDnumber(String IDnumber) { this.IDnumber = IDnumber; }

    public String getEducation() { return Education; }
    public void setEducation(String Education) { this.Education = Education; }

    public String getPermanent_address() { return permanent_address; }
    public void setPermanent_address(String permanent_address) { this.permanent_address = permanent_address; }

    public String getLocal_address() { return local_address; }
    public void setLocal_address(String local_address) { this.local_address = local_address; }

    public String getContactnumber() { return contactnumber; }
    public void setContactnumber(String contactnumber) { this.contactnumber = contactnumber; }

    public String getEmailID() { return emailID; }
    public void setEmailID(String emailID) { this.emailID = emailID; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDate getMembershipDate() { return membershipDate; }
    public void setMembershipDate(LocalDate membershipDate) { this.membershipDate = membershipDate; }
}

