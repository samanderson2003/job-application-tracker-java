package com.jobtracker.controller;

import com.jobtracker.model.Job;
import com.jobtracker.model.User;
import com.jobtracker.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    @Autowired
    private JobService jobService;

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        // Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        // Get all jobs for the user
        List<Job> allJobs = jobService.getAllJobsByUser(user);
        
        // Group jobs by status
        Map<String, List<Job>> jobsByStatus = allJobs.stream()
            .collect(Collectors.groupingBy(Job::getStatus));
        
        // Get statistics
        Map<String, Long> statistics = jobService.getJobStatistics(user);
        
        // Add data to model
        model.addAttribute("user", user);
        model.addAttribute("allJobs", allJobs);
        model.addAttribute("wishlistJobs", jobsByStatus.getOrDefault("wishlist", List.of()));
        model.addAttribute("appliedJobs", jobsByStatus.getOrDefault("applied", List.of()));
        model.addAttribute("interviewJobs", jobsByStatus.getOrDefault("interview", List.of()));
        model.addAttribute("offerJobs", jobsByStatus.getOrDefault("offer", List.of()));
        model.addAttribute("rejectedJobs", jobsByStatus.getOrDefault("rejected", List.of()));
        model.addAttribute("acceptedJobs", jobsByStatus.getOrDefault("accepted", List.of()));
        model.addAttribute("statistics", statistics);
        
        return "dashboard";
    }
}