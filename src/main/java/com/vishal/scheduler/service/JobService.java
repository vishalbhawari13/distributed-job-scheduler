package com.vishal.scheduler.service;

import com.vishal.scheduler.entity.Job;
import com.vishal.scheduler.repository.JobRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public Job createJob(Job job) {
        job.setStatus("PENDING");
        job.setRetryCount(0);
        return jobRepository.save(job);
    }

    @Transactional
    public List<Job> getPendingJobs() {
        return jobRepository.findAndLockPendingJobs(LocalDateTime.now());
    }

    public void updateJob(Job job) {
        jobRepository.save(job);
    }
}