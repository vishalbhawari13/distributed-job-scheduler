📌 Distributed Job Scheduler

A scalable distributed job scheduling system built using Spring Boot, Apache Kafka, PostgreSQL, and Docker.
The system reliably schedules, processes, and tracks background jobs in a fault-tolerant and event-driven architecture.

🚀 Key Features
⏱️ Scheduled job execution (time-based processing)
📬 Asynchronous job processing using Kafka
🗄️ Persistent job tracking with PostgreSQL
🔁 Retry support for failed jobs
📊 Real-time job status tracking (PENDING → RUNNING → SUCCESS)
⚙️ Producer–Consumer architecture for decoupled processing
🧵 Concurrent consumer processing (scalable workers)
🐳 Containerized Kafka + Zookeeper setup using Docker
🏗️ System Architecture

                    <img width="1122" height="1402" alt="image" src="https://github.com/user-attachments/assets/b644f739-8631-4f1c-b2d7-d2ccf14eb341" />

⚙️ Tech Stack
Java 21
Spring Boot 3.2+
Spring Kafka
Spring Data JPA
PostgreSQL
Apache Kafka
Zookeeper
Docker
Maven
📦 Project Structure
distributed-job-scheduler
│
├── controller/        # REST APIs (if any)
├── service/           # Business logic
├── scheduler/         # Job triggering logic
├── kafka/             # Producer & Consumer logic
├── entity/            # JPA entities
├── repository/        # DB layer
├── config/            # Kafka configs
└── DistributedJobSchedulerApplication.java
🔄 Job Lifecycle
Job is created in PostgreSQL (PENDING)
Scheduler picks up eligible jobs
Job is published to Kafka topic
Consumer processes the job asynchronously
Status updated in DB:
PENDING → RUNNING → SUCCESS / FAILED
🐳 Kafka Setup (Docker)

Run infrastructure:

docker-compose up -d

Includes:

Kafka Broker (port 9092)
Zookeeper (port 2181)
⚙️ Configuration
application.properties
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=job-group
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.listener.concurrency=3
📊 Example Job Table
id | job_name | payload | retry_count | schedule_time | status
---|----------|---------|-------------|---------------|---------
48 | Job 74   | test    | 0           | 02:19:00      | SUCCESS
49 | Job 75   | test    | 0           | 02:24:00      | RUNNING
💡 Design Highlights (Interview Ready)
Event-driven architecture using Kafka
Loose coupling between scheduler and execution layer
Horizontal scalability via consumer groups
Fault tolerance with retry mechanism
Database-backed state tracking for reliability
Can scale workers independently without affecting scheduler
📈 Scalability Improvements (Future Scope)
Dead Letter Queue (DLQ) for failed jobs
Redis-based distributed locking
Kubernetes deployment for auto-scaling consumers
Observability using Prometheus + Grafana
Job prioritization system
Multi-tenant job isolation
🧠 Why this project matters

This system simulates a real-world distributed task queue, similar to:

Apache Airflow (simplified)
Celery (Python equivalent)
Enterprise background job systems

It demonstrates:

Distributed systems design
Message queue architecture
Fault-tolerant processing
Production-level Spring Boot design
👨‍💻 Author

Vishal Bhawari
Java Backend Developer | Spring Boot | Microservices | Kafka | System Design
