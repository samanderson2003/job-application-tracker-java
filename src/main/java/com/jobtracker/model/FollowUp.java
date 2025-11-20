package com.jobtracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "follow_ups")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowUp {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;
    
    @Column(name = "follow_up_date")
    private LocalDate followUpDate;
    
    @Column(length = 200)
    private String subject;
    
    @Column(columnDefinition = "TEXT")
    private String message;
    
    @Column
    private Boolean sent = false;
    
    @Column(name = "sent_at")
    private LocalDateTime sentAt;
}
