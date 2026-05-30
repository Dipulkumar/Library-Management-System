package com.localhost.library.controller;

import com.localhost.library.model.BookIssue;
import com.localhost.library.model.UserInfo;
import com.localhost.library.repository.BookRepository;
import com.localhost.library.repository.UserRepository;
import com.localhost.library.security.CustomUserDetails;
import com.localhost.library.service.BookIssueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class BookIssueController {
	
	private static final Logger logger = Logger.getLogger(BookIssueController.class.getName());


    @Autowired
    private BookIssueService bookIssueService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BookRepository bookRepository;

    @PreAuthorize("permitAll()")
    @GetMapping("/bookissues")
    public String listIssues(Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
    	Collection<? extends GrantedAuthority> privileges = userDetails.getAuthorities();
    	List<BookIssue> issues;
    	List<String> privilegeList = privileges.stream()
    	    .map(GrantedAuthority::getAuthority)
    	    .collect(Collectors.toList());
    	if (privilegeList.contains("ALL") || privilegeList.contains("viewBookIssue")) {
    		issues = bookIssueService.getAllIssues();
    	}else {
    		UserInfo loggedInUser = userRepository.findById(userDetails.getId()).get();
    		issues = bookIssueService.getAllIssuesByUser(loggedInUser);
    	}
    	
    	bookIssueService.calculateLateFee(issues);
    	
        model.addAttribute("bookissues", issues);
        return "bookissue";
    }

    @GetMapping("/bookissues/add")
    public String showAddForm(Model model) {
        model.addAttribute("bookIssue", new BookIssue());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("books", bookRepository.findAll());
        return "addbookissue";
    }

    @PostMapping("/bookissues/save")
    public String saveIssue(
    		@RequestParam Long userId,
            @RequestParam List<Long> bookIds,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate issueDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate
		) {

        for (Long bookId : bookIds) {
            BookIssue issue = new BookIssue();
            issue.setBookId(bookId);
            issue.setUserId(userId);
            issue.setIssueDate(issueDate);
            issue.setDueDate(dueDate);
            try {
            	bookIssueService.saveIssue(issue);
            }catch(Exception ex) {
            	logger.log(Level.SEVERE, "unable to issue book id: " + bookId + " for user id: " + userId, ex); 
            }
        }
        return "redirect:/bookissues";
    }

    @GetMapping("/bookissues/delete/{id}")
    public String deleteIssue(@PathVariable Long id) {
        bookIssueService.deleteIssue(id);
        return "redirect:/bookissues";
    }
    
    // NEW: return a book (actual return)
    @PostMapping("/bookissues/return/{id}")
    public String returnBook(@PathVariable Long id){
        bookIssueService.returnBook(id);
        return "redirect:/bookissues";
    }
    
    // NEW: pay fine (marks as PAID)
    @PostMapping("/bookissues/pay/{id}")
    public String payFine(@PathVariable Long id) {
        bookIssueService.payFine(id);
        return "redirect:/bookissues";
    }
}