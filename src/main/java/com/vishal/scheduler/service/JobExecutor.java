package com.vishal.scheduler.service;

import com.vishal.scheduler.entity.Job;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobExecutor {

    public void execute(Job job) throws Exception {

        log.info("Executing job: {}", job.getJobName());

        // Simulate failure
        if (job.getJobName().contains("fail")) {
            throw new Exception("Simulated failure");
        }

        // Simulate processing time
        Thread.sleep(5000);

        log.info("Job completed: {}", job.getJobName());
    }
}