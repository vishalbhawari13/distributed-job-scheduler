package com.vishal.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableScheduling
@EnableAsync   // 🔥 ADD THIS
@SpringBootApplication
public class DistributedJobSchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributedJobSchedulerApplication.class, args);
    }
}