package com.localhost.library.controller;

import com.localhost.library.model.BookIssue;
import com.localhost.library.model.UserInfo;
import com.localhost.library.security.CustomUserDetails;
import com.localhost.library.service.BookIssueService;
import com.localhost.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private BookIssueService bookIssueService;

    @PreAuthorize("hasAuthority('ALL') or hasAuthority('viewUser')")
    @GetMapping("/users")
    public String listUsers(Model model) {
    	List<UserInfo> users = userService.getAllUsers();
        model.addAttribute("users", users != null ? users : Collections.emptyList());
        return "users"; // users.html
    }

    @PreAuthorize("hasAuthority('ALL') or hasAuthority('addUser')")
    @GetMapping("/users/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new UserInfo());
        return "adduser";
    }

    @PreAuthorize("hasAuthority('ALL') or hasAuthority('addUser')")
    @PostMapping("/users/add")
    public String addUser(@ModelAttribute UserInfo user) {
    	//String rawpwd = user.getEmailID();
    	//String pwd = new BCryptPasswordEncoder().encode(rawpwd);
    	//user.setEmailID(pwd);
        userService.addUser(user);
        return "redirect:/users";
    }

    @PreAuthorize("hasAuthority('ALL') or hasAuthority('updateUser')")
    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        UserInfo user = userService.getUserById(id);
        if(user == null) {
	        return "redirect:/error";
        }
        model.addAttribute("user", user);
        
        return "edituser";
    }

    @PreAuthorize("hasAuthority('ALL') or hasAuthority('updateUser')")
    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute UserInfo userInfo) {
        userService.updateUser(userInfo.getId(), userInfo);
        return "redirect:/users";
    }

    @PreAuthorize("hasAuthority('ALL') or hasAuthority('deleteUser')")
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/users/issuebook/{bookId}")
    public String issueBook(@PathVariable Long bookId) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
    	Long userId = userDetails.getId();
    	BookIssue bookIssue = new BookIssue();
    	bookIssue.setBookId(bookId);
    	bookIssue.setUserId(userId);
    	LocalDate issueDate = LocalDate.now();
    	bookIssue.setIssueDate(issueDate);
    	bookIssue.setDueDate(issueDate.plusDays(15));
    	bookIssueService.saveIssue(bookIssue);
        return "redirect:/books";
    }
}

