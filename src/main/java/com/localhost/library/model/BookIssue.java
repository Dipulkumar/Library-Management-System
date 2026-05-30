package com.localhost.library.model;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class BookIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issueId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private LocalDate issueDate;
    
 // NEW: planned return date (rename from your 'returnDate')
    private LocalDate dueDate;

    // NEW: real return date (when user returns)
    private LocalDate actualReturnDate;

    // NEW: running or final fine amount
    private Double fineAmount = 0.0;

    // NEW: fine status
    @Enumerated(EnumType.STRING)
    private FineStatus fineStatus = FineStatus.NONE;


    @Transient
    private Long userId;   // For binding in form
    @Transient
    private Long bookId;

	private Long adminId;
	
	
	 // --- helpers (not persisted) ---
    @Transient @JsonIgnore
    public boolean isReturned() {
        return actualReturnDate != null;
    }
    
    

    // Getters and setters
    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }
    
    
    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(LocalDate actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public Double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(Double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public FineStatus getFineStatus() {
        return fineStatus;
    }

    public void setFineStatus(FineStatus fineStatus) {
        this.fineStatus = fineStatus;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

	public void setIssuedByAdminId(Long adminId) {
		this.setAdminId(adminId);
		
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}
}
