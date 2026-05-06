package com.vishal.scheduler.scheduler;

import com.vishal.scheduler.entity.Job;
import com.vishal.scheduler.entity.JobStatus;
import com.vishal.scheduler.service.JobService;
import com.vishal.scheduler.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobScheduler {

    private final JobService jobService;
    private final KafkaProducerService kafkaProducerService;

    @Scheduled(fixedRate = 5000)

    @SchedulerLock(
            name = "processJobsLock",
            lockAtLeastFor = "PT5S",
            lockAtMostFor = "PT30S"
    )
    public void processJobs() {

        List<Job> jobs = jobService.getPendingJobs();

        if (jobs.isEmpty()) {
            return;
        }

        log.info("Found {} pending jobs", jobs.size());

        for (Job job : jobs) {

            if (job.getStatus() != JobStatus.PENDING) {
                continue;
            }

            job.setStatus(JobStatus.RUNNING);

            job.setStartedAt(LocalDateTime.now());

            jobService.updateJob(job);

            kafkaProducerService.sendJob(job.getId().toString());

            log.info("Job scheduled: {}", job.getId());
        }
    }
}