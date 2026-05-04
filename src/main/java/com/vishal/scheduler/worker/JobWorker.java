package com.vishal.scheduler.worker;

import com.vishal.scheduler.entity.Job;
import com.vishal.scheduler.repository.JobRepository;
import com.vishal.scheduler.service.JobExecutor;
import com.vishal.scheduler.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class JobWorker {

    private static final Logger log = LoggerFactory.getLogger(JobWorker.class);

    private final JobRepository jobRepository;
    private final JobExecutor jobExecutor;
    private final KafkaProducerService kafkaProducerService;

    @KafkaListener(topics = "job-topic", groupId = "job-group")
    @Transactional
    public void consume(String jobId) {

        log.info("🔥 Received job from Kafka: {}", jobId);

        Job job = jobRepository.findById(Long.parseLong(jobId)).orElse(null);

        if (job == null) {
            log.error("❌ Job not found: {}", jobId);
            return;
        }

        // 🔥 Idempotency check (CRITICAL)
        if (!"RUNNING".equals(job.getStatus())) {
            log.warn("⚠️ Skipping duplicate job: {}", jobId);
            return;
        }

        try {
            log.info("🚀 Executing job: {}", job.getJobName());

            jobExecutor.execute(job);

            job.setStatus("SUCCESS");
            log.info("✅ Job completed: {}", job.getJobName());

        } catch (Exception e) {

            job.setRetryCount(job.getRetryCount() + 1);

            if (job.getRetryCount() < 3) {

                log.warn("🔁 Retrying job: {} (attempt {})", jobId, job.getRetryCount());

                job.setStatus("PENDING");
                job.setScheduleTime(LocalDateTime.now().plusSeconds(10));

            } else {

                log.error("💀 Job failed permanently: {}", jobId);

                job.setStatus("FAILED");

                // 🔥 send to DLQ
                kafkaProducerService.sendToDLQ(jobId);
            }
        }

        jobRepository.save(job);
    }
}