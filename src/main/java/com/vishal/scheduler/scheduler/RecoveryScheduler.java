package com.vishal.scheduler.scheduler;

import com.vishal.scheduler.entity.Job;
import com.vishal.scheduler.entity.JobStatus;
import com.vishal.scheduler.repository.JobRepository;
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
public class RecoveryScheduler {

    private final JobRepository jobRepository;

    @Scheduled(fixedRate = 60000)

    @SchedulerLock(
            name = "recoverTimedOutJobs",
            lockAtMostFor = "PT1M"
    )
    public void recoverTimedOutJobs() {

        List<Job> timedOutJobs =
                jobRepository.findTimedOutJobs(
                        LocalDateTime.now().minusMinutes(5)
                );

        for (Job job : timedOutJobs) {

            log.warn("Recovering timed out job: {}", job.getId());

            job.setStatus(JobStatus.PENDING);

            job.setScheduleTime(LocalDateTime.now());

            jobRepository.save(job);
        }
    }
}