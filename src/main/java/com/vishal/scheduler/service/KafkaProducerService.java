package com.vishal.scheduler.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "job-topic";
    private static final String DLQ_TOPIC = "job-dlq";

    public void sendJob(String jobData) {
        log.info("Sending job to Kafka: {}", jobData);
        kafkaTemplate.send(TOPIC, jobData);
    }

    public void sendToDLQ(String jobData) {
        log.error("Sending job to DLQ: {}", jobData);
        kafkaTemplate.send(DLQ_TOPIC, jobData);
    }
}