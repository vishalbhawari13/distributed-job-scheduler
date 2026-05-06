package com.vishal.scheduler.worker;

import com.vishal.scheduler.entity.Job;
import com.vishal.scheduler.entity.JobStatus;
import com.vishal.scheduler.repository.JobRepository;
import com.vishal.scheduler.service.JobExecutor;
import com.vishal.scheduler.service.KafkaProducerService;
import com.vishal.scheduler.service.MetricsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobWorker {

    private final JobRepository jobRepository;
    private final JobExecutor jobExecutor;
    private final KafkaProducerService kafkaProducerService;
    private final MetricsService metricsService;

    @KafkaListener(topics = "job-topic", groupId = "job-group")
    @Transactional
    public void consume(String jobId) {

        log.info("Received job: {}", jobId);

        Job job = jobRepository.findById(Long.parseLong(jobId))
                .orElse(null);

        if (job == null) {
            log.error("Job not found: {}", jobId);
            return;
        }

        // 🔥 Idempotency check
        if (job.getStatus() != JobStatus.RUNNING) {
            log.warn("Duplicate job skipped: {}", jobId);
            return;
        }

        try {

            // 🔥 Execute job
            jobExecutor.execute(job);

            job.setStatus(JobStatus.SUCCESS);
            job.setCompletedAt(LocalDateTime.now());

            log.info("Job completed successfully: {}", jobId);

            // ✅ METRIC: SUCCESS
            metricsService.incrementSuccess();

        } catch (Exception e) {

            log.error("Job failed: {}", jobId);

            job.setRetryCount(job.getRetryCount() + 1);
            job.setErrorMessage(e.getMessage());

            if (job.getRetryCount() < 3) {

                int retryDelay =
                        (int) Math.pow(2, job.getRetryCount()) * 10;

                job.setStatus(JobStatus.PENDING);
                job.setScheduleTime(
                        LocalDateTime.now().plusSeconds(retryDelay)
                );

                log.warn("Retrying job {} after {} seconds",
                        jobId,
                        retryDelay);

                // ✅ METRIC: RETRY
                metricsService.incrementRetry();

            } else {

                job.setStatus(JobStatus.FAILED);

                kafkaProducerService.sendToDLQ(jobId);

                log.error("Job permanently failed: {}", jobId);

                // ✅ METRIC: FAILURE
                metricsService.incrementFailure();
            }
        }

        jobRepository.save(job);
    }
}