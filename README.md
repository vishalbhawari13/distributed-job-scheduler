📌 Distributed Fault-Tolerant Job Scheduling Platform

A scalable, distributed job orchestration system built using Spring Boot, Apache Kafka, PostgreSQL, and Docker.

⚡ Designed to handle background jobs reliably using an event-driven, fault-tolerant architecture
Inspired by systems like Airflow · Temporal · Netflix Conductor

🚀 Key Features
⏱️ Time-based job scheduling
📬 Asynchronous job execution using Kafka
🗄️ Persistent job storage with PostgreSQL
🔁 Retry mechanism with exponential backoff
💀 Dead Letter Queue (DLQ) for failed jobs
🧠 Idempotency handling (prevents duplicate execution)
⚡ Concurrent job processing (Kafka consumers)
🎯 Priority-based scheduling
🧵 Async execution using thread pool
🔒 Pessimistic locking (avoids duplicate scheduling)
📊 Job lifecycle tracking
🐳 Dockerized Kafka + Zookeeper setup
🔄 Job Lifecycle
PENDING → RUNNING → SUCCESS / FAILED
🏗️ System Architecture
<p align="center"> <img src="https://github.com/user-attachments/assets/b644f739-8631-4f1c-b2d7-d2ccf14eb341" width="750"/> </p>
⚙️ Tech Stack
Layer	Technology
Language	Java 21
Framework	Spring Boot 3+
Messaging	Apache Kafka
Database	PostgreSQL
ORM	Spring Data JPA
Container	Docker
Build Tool	Maven
🔁 Job Processing Flow
1. API → Create Job (PENDING)
2. Scheduler polls DB every 5 sec
3. Apply locking + priority sorting
4. Mark job as RUNNING
5. Publish job to Kafka
6. Worker consumes job
7. Execute asynchronously
8. SUCCESS → Mark completed
9. FAILURE → Retry (backoff) → DLQ (if max retries exceeded)
💡 Distributed System Concepts
⚡ Event-driven architecture
🔁 Retry with exponential backoff
💀 Dead Letter Queue (DLQ)
🧠 Idempotency handling
🧵 Concurrent processing
🔒 Pessimistic locking
📬 Producer–Consumer pattern
⚙️ Asynchronous execution
🐳 Kafka Setup (Docker)

Start Kafka & Zookeeper:

docker-compose up -d
📡 Services
Kafka → localhost:9092
Zookeeper → localhost:2181
⚙️ Configuration
spring.kafka.bootstrap-servers=localhost:9092

# Consumer
spring.kafka.consumer.group-id=job-group
spring.kafka.consumer.auto-offset-reset=earliest

# Parallel processing
spring.kafka.listener.concurrency=3

# Producer retries
spring.kafka.producer.retries=3
📡 API Endpoints
➕ Create Job
POST /jobs
{
  "jobName": "email-job",
  "payload": "send email",
  "scheduleTime": "2026-05-06T12:00:00",
  "priority": 10
}
🔍 Get Job by ID
GET /jobs/{id}
❌ Get Failed Jobs
GET /jobs/failed
📊 Example Job Table
ID	Job Name	Payload	Retries	Schedule Time	Status	Priority
48	email-job	send email	0	2026-05-05 02:19:00	SUCCESS	10
49	fail-payment-job	payment	3	2026-05-05 02:24:00	FAILED	100


👨‍💻 Author

Vishal Bhawari
💻 Java Backend Developer
⚙️ Spring Boot | Kafka | Distributed Systems
