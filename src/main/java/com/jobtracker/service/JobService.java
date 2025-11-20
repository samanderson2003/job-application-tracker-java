package com.jobtracker.service;

import com.jobtracker.model.Job;
import com.jobtracker.model.User;
import com.jobtracker.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public List<Job> getAllJobsByUser(User user) {
        return jobRepository.findByUserOrderByCreatedAtDesc(user);
    }
    
    public List<Job> getJobsByUserAndStatus(User user, String status) {
        return jobRepository.findByUserAndStatusOrderByCreatedAtDesc(user, status);
    }

    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }

    public Job createJob(Job job) {
        if (job.getStatus() == null || job.getStatus().isEmpty()) {
            job.setStatus("wishlist");
        }
        return jobRepository.save(job);
    }

    public Job updateJob(Long id, Job jobDetails) {
        Job job = jobRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
        
        job.setCompanyName(jobDetails.getCompanyName());
        job.setCompanyLogo(jobDetails.getCompanyLogo());
        job.setPosition(jobDetails.getPosition());
        job.setLocation(jobDetails.getLocation());
        job.setJobType(jobDetails.getJobType());
        job.setSalaryRange(jobDetails.getSalaryRange());
        job.setJobDescription(jobDetails.getJobDescription());
        job.setJobUrl(jobDetails.getJobUrl());
        job.setStatus(jobDetails.getStatus());
        job.setPriority(jobDetails.getPriority());
        job.setAppliedDate(jobDetails.getAppliedDate());
        job.setInterviewDate(jobDetails.getInterviewDate());
        job.setOfferDate(jobDetails.getOfferDate());
        job.setDeadline(jobDetails.getDeadline());
        job.setNotes(jobDetails.getNotes());
        
        return jobRepository.save(job);
    }
    
    public Job updateJobStatus(Long id, String status) {
        Job job = jobRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
        
        job.setStatus(status);
        
        // Auto-update application date when status changes to applied
        if ("applied".equalsIgnoreCase(status) && job.getAppliedDate() == null) {
            job.setAppliedDate(LocalDate.now());
        }
        
        return jobRepository.save(job);
    }

    public void deleteJob(Long id) {
        Job job = jobRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
        jobRepository.delete(job);
    }
    
    public Map<String, Long> getJobStatistics(User user) {
        Map<String, Long> stats = new HashMap<>();
        
        stats.put("total", jobRepository.countByUser(user));
        stats.put("wishlist", jobRepository.countByUserAndStatus(user, "WISHLIST"));
        stats.put("applied", jobRepository.countByUserAndStatus(user, "APPLIED"));
        stats.put("interview", jobRepository.countByUserAndStatus(user, "INTERVIEW"));
        stats.put("offer", jobRepository.countByUserAndStatus(user, "OFFER"));
        stats.put("rejected", jobRepository.countByUserAndStatus(user, "REJECTED"));
        stats.put("accepted", jobRepository.countByUserAndStatus(user, "ACCEPTED"));
        
        return stats;
    }
}
