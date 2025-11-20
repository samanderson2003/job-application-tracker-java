package com.jobtracker.repository;

import com.jobtracker.model.Job;
import com.jobtracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByUserOrderByCreatedAtDesc(User user);
    List<Job> findByUserAndStatusOrderByCreatedAtDesc(User user, String status);
    
    @Query("SELECT COUNT(j) FROM Job j WHERE j.user = :user")
    long countByUser(@Param("user") User user);
    
    @Query("SELECT COUNT(j) FROM Job j WHERE j.user = :user AND j.status = :status")
    long countByUserAndStatus(@Param("user") User user, @Param("status") String status);
    
    @Query("SELECT j.status, COUNT(j) FROM Job j WHERE j.user = :user GROUP BY j.status")
    List<Object[]> getJobStatusStatistics(@Param("user") User user);
}