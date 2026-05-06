package com.vishal.scheduler.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobName;

    @Column(columnDefinition = "TEXT")
    private String payload;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime scheduleTime;

    @Enumerated(EnumType.STRING)
    private JobStatus status;

    private Integer retryCount;


    private Integer priority;


    private LocalDateTime startedAt;


    private LocalDateTime completedAt;


    private String errorMessage;
}