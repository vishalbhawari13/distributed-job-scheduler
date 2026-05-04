package com.vishal.scheduler.service;

import com.vishal.scheduler.entity.Job;
import org.springframework.stereotype.Component;

@Component
public class JobExecutor {

    public void execute(Job job) throws Exception {

        System.out.println("Executing job: " + job.getJobName());

        if (job.getJobName().contains("fail")) {
            throw new Exception("Simulated failure");
        }

        Thread.sleep(2000);

        System.out.println("Job completed: " + job.getJobName());
    }
}