package com.jobtracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonIgnoreProperties({"jobs", "password"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @NotBlank(message = "Company name is required")
    @Column(name = "company_name", nullable = false, length = 100)
    private String companyName;
    
    @Column(name = "company_logo", length = 255)
    private String companyLogo;
    
    @NotBlank(message = "Position is required")
    @Column(nullable = false, length = 100)
    private String position;
    
    @Column(length = 100)
    private String location;
    
    @Column(name = "job_type", length = 50)
    private String jobType;
    
    @Column(name = "salary_range", length = 50)
    private String salaryRange;
    
    @Column(name = "job_description", columnDefinition = "TEXT")
    private String jobDescription;
    
    @Column(name = "job_url", length = 500)
    private String jobUrl;
    
    @Column(length = 50)
    private String status = "wishlist";
    
    @Column(length = 20)
    private String priority = "medium";
    
    @Column(name = "applied_date")
    private LocalDate appliedDate;
    
    @Column(name = "interview_date")
    private LocalDateTime interviewDate;
    
    @Column(name = "offer_date")
    private LocalDate offerDate;
    
    @Column(name = "deadline")
    private LocalDate deadline;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null || status.isEmpty()) {
            status = "wishlist";
        }
        if (priority == null || priority.isEmpty()) {
            priority = "medium";
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}