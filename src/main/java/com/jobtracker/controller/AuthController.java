package com.jobtracker.controller;

import com.jobtracker.model.User;
import com.jobtracker.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping({"/", "/login"})
    public String showLoginPage(HttpSession session, Model model) {
        // If user is already logged in, redirect to dashboard
        if (session.getAttribute("user") != null) {
            return "redirect:/dashboard";
        }
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, 
                           @RequestParam String password, 
                           HttpSession session,
                           Model model) {
        try {
            Optional<User> userOptional = authService.loginUser(username, password);
            
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                // Store user in session
                session.setAttribute("user", user);
                session.setAttribute("userId", user.getId());
                session.setAttribute("username", user.getUsername());
                return "redirect:/dashboard";
            } else {
                model.addAttribute("error", "Invalid username or password");
                model.addAttribute("user", new User());
                return "login";
            }
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred during login: " + e.getMessage());
            model.addAttribute("user", new User());
            return "login";
        }
    }

    @GetMapping("/register")
    public String showRegisterPage(HttpSession session, Model model) {
        // If user is already logged in, redirect to dashboard
        if (session.getAttribute("user") != null) {
            return "redirect:/dashboard";
        }
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid User user,
                              BindingResult result,
                              @RequestParam String confirmPassword,
                              Model model) {
        // Check for validation errors
        if (result.hasErrors()) {
            return "register";
        }
        
        // Check if passwords match
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "register";
        }
        
        try {
            User registeredUser = authService.registerUser(user);
            model.addAttribute("success", "Registration successful! Please log in.");
            return "redirect:/login?registered=true";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout=true";
    }
}