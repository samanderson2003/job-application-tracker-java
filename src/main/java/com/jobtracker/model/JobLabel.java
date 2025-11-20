package com.jobtracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "job_labels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobLabel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;
    
    @Column(nullable = false, length = 50)
    private String label;
    
    @Column(length = 20)
    private String color;
}
