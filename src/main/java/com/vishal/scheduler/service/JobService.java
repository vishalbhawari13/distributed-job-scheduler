package com.vishal.scheduler.service;

import com.vishal.scheduler.entity.Job;
import com.vishal.scheduler.entity.JobStatus;
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

        job.setStatus(JobStatus.PENDING);
        job.setRetryCount(0);

        if (job.getPriority() == null) {
            job.setPriority(1);
        }

        return jobRepository.save(job);
    }

    @Transactional
    public List<Job> getPendingJobs() {

        return jobRepository.findAndLockPendingJobs(
                JobStatus.PENDING,
                LocalDateTime.now()
        );
    }

    public Job updateJob(Job job) {
        return jobRepository.save(job);
    }

    public Job getJob(Long id) {

        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }

    public List<Job> getFailedJobs() {
        return jobRepository.findByStatus(JobStatus.FAILED);
    }
}