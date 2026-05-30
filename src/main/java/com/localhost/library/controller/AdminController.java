package com.localhost.library.controller;

import com.localhost.library.model.Admin;
import com.localhost.library.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PreAuthorize("hasAuthority('ALL') or hasAuthority('viewUser')")
    @GetMapping("/admins")
    public String listAdmins(Model model) {
        List<Admin> admins = adminService.getAllAdmins();
        model.addAttribute("admins", admins);
        return "admin"; // loads admin.html
    }

    @PreAuthorize("hasAuthority('ALL') or hasAuthority('viewUser')")
    @GetMapping("/admins/new")
    public String createAdminForm(Model model) {
        model.addAttribute("admin", new Admin());
        model.addAttribute("formAction", "/admins/add");
        model.addAttribute("formTitle", "Add New Admin");
        return "admin_form";
    }

    @PreAuthorize("hasAuthority('ALL') or hasAuthority('viewUser')")
    @PostMapping("/admins/add")
    public String saveAdmin(@ModelAttribute("admin") Admin admin) {
        adminService.createAdmin(admin);
        return "redirect:/admins";
    }

    @PreAuthorize("hasAuthority('ALL') or hasAuthority('viewUser')")
    @GetMapping("/admins/edit/{id}")
    public String editAdminForm(@PathVariable Long id, Model model) {
        Admin admin = adminService.getAdminById(id).orElseThrow(() -> new IllegalArgumentException("Invalid admin ID"));
        model.addAttribute("formAction", "/admins/update/" + admin.getId());
        model.addAttribute("formTitle", "Edit Admin");
        model.addAttribute("admin", admin);
        return "admin_form";
    }

    @PreAuthorize("hasAuthority('ALL') or hasAuthority('viewUser')")
    @PostMapping("/admins/update/{id}")
    public String updateAdmin(@PathVariable Long id, @ModelAttribute Admin admin) {
        adminService.updateAdmin(id, admin);
        return "redirect:/admins";
    }

    @PreAuthorize("hasAuthority('ALL') or hasAuthority('viewUser')")
    @GetMapping("/admins/delete/{id}")
    public String deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return "redirect:/admins";
    }
}
