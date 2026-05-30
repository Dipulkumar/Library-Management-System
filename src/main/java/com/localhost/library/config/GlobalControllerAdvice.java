package com.localhost.library.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.localhost.library.security.CustomUserDetails;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addCommonAttributes(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

            model.addAttribute("name", userDetails.getName());
            model.addAttribute("emailID", userDetails.getEmail());
        }
    }
}
