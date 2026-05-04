package com.vishal.scheduler.scheduler;

import com.vishal.scheduler.entity.Job;
import com.vishal.scheduler.service.JobService;
import com.vishal.scheduler.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JobScheduler {

    private final JobService jobService;
    private final KafkaProducerService kafkaProducerService;

    @Scheduled(fixedRate = 5000)
    public void processJobs() {

        List<Job> jobs = jobService.getPendingJobs();

        for (Job job : jobs) {

            // 🔥 prevent duplicate scheduling
            if (!"PENDING".equals(job.getStatus())) continue;

            job.setStatus("RUNNING");
            jobService.updateJob(job);

            kafkaProducerService.sendJob(job.getId().toString());
        }
    }
}