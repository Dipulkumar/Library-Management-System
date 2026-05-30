package com.localhost.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        return "home"; // This will map to src/main/resources/templates/home.html
    }
    
    @GetMapping("/greeting")
    public String greeting(@RequestParam(required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/error")
    public String error() {
        return "error";
    }
}