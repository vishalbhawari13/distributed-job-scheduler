package com.vishal.scheduler.controller;

import com.vishal.scheduler.entity.Job;
import com.vishal.scheduler.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    public Job createJob(@RequestBody Job job) {
        return jobService.createJob(job);
    }

    @GetMapping("/{id}")
    public Job getJob(@PathVariable Long id) {
        return jobService.getJob(id);
    }

    @GetMapping("/failed")
    public List<Job> failedJobs() {
        return jobService.getFailedJobs();
    }
}