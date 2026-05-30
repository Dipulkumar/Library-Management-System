package com.localhost.library.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class UserRegistrationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;
   

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)  // allows null
    private UserInfo user;

    @ManyToOne
    @JoinColumn(name = "verifier_id")
    private Admin verifier;

    private LocalDateTime verifiedDate;

//    private LocalDateTime createdAt = LocalDateTime.now();
    
    private LocalDateTime createdAt;
    
    private String username;

    private String password;
    
 // in UserRegistrationRequest.java
    @Transient
    public String getStatus() {
        // maps boolean -> "PENDING" / "APPROVED"
    	String status = "PENDING";
    	if(approvalStatus == 1) {
    		status = "APPROVED";
    	}else if(approvalStatus == 2) {
    		status = "REJECTED";
    	}
    	
        return status;
    }

    // (optional convenience flags if you like)
    @Transient
    public boolean isPending() { return approvalStatus == 0; }

    @Transient
    public boolean isApproved() { return approvalStatus == 1; }


    private int approvalStatus = 0; // false = pending, true = approved
    
    @PrePersist
    public void prePersist() {
    	if (createdAt == null) createdAt = LocalDateTime.now();
    }
    
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

    // --- Getters and Setters ---

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }
    
    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }




    public Admin getVerifier() {
        return verifier;
    }
    
    
    
    public void setVerifier( Admin verifier) {
    	this.verifier = verifier;
    }


    public LocalDateTime getVerifiedDate() {
        return verifiedDate;
    }

    public void setVerifiedDate(LocalDateTime verifiedDate) {
        this.verifiedDate = verifiedDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(int approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
    
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
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
