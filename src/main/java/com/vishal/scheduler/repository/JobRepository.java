package com.vishal.scheduler.repository;

import com.vishal.scheduler.entity.Job;
import com.vishal.scheduler.entity.JobStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT j
        FROM Job j
        WHERE j.status = :status
        AND j.scheduleTime <= :time
        ORDER BY j.priority DESC, j.scheduleTime ASC
    """)
    List<Job> findAndLockPendingJobs(
            @Param("status") JobStatus status,
            @Param("time") LocalDateTime time
    );

    List<Job> findByStatus(JobStatus status);

    @Query("""
    SELECT j
    FROM Job j
    WHERE j.status = 'RUNNING'
    AND j.startedAt <= :time
""")
    List<Job> findTimedOutJobs(
            @Param("time") LocalDateTime time
    );
}