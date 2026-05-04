package com.vishal.scheduler.repository;

import com.vishal.scheduler.entity.Job;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT j FROM Job j WHERE j.status = 'PENDING' AND j.scheduleTime <= :time")
    List<Job> findAndLockPendingJobs(@Param("time") LocalDateTime time);
}