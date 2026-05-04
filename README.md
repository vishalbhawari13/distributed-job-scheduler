# 📌 Distributed Job Scheduler

A **scalable distributed job scheduling system** built using **Spring Boot, Apache Kafka, PostgreSQL, and Docker**.  
The system reliably schedules, processes, and tracks background jobs in a **fault-tolerant, event-driven architecture**.

---

# 🚀 Key Features

- ⏱️ Scheduled job execution (time-based processing)
- 📬 Asynchronous job processing using **Kafka**
- 🗄️ Persistent job tracking with **PostgreSQL**
- 🔁 Retry support for failed jobs
- 📊 Real-time job status tracking  
  `PENDING → RUNNING → SUCCESS / FAILED`
- ⚙️ Producer–Consumer architecture (decoupled services)
- 🧵 Concurrent Kafka consumer processing (scalable workers)
- 🐳 Docker-based Kafka + Zookeeper setup

---

# 🏗️ System Architecture

<p align="center">
  <img src="https://github.com/user-attachments/assets/b644f739-8631-4f1c-b2d7-d2ccf14eb341" width="750"/>
</p>

---

# ⚙️ Tech Stack

- Java 21
- Spring Boot 3.2+
- Spring Kafka
- Spring Data JPA
- PostgreSQL
- Apache Kafka
- Zookeeper
- Docker
- Maven

---




---

# 🔄 Job Lifecycle

PENDING → RUNNING → SUCCESS / FAILED



### Flow:

1. Job created in PostgreSQL (`PENDING`)
2. Scheduler picks eligible jobs
3. Job published to Kafka topic
4. Consumer processes job asynchronously
5. Status updated in database

---

# 🐳 Kafka Setup (Docker)

Start infrastructure:

```bash
docker-compose up -d


Services:
Kafka Broker → localhost:9092
Zookeeper → localhost:2181

⚙️ Configuration
application.properties
spring.kafka.bootstrap-servers=localhost:9092

spring.kafka.consumer.group-id=job-group
spring.kafka.consumer.auto-offset-reset=earliest

spring.kafka.listener.concurrency=3

spring.kafka.producer.retries=3
📊 Example Job Table
id	job_name	payload	retry_count	schedule_time	status
48	Job 74	test	0	2026-05-05 02:19:00	SUCCESS
49	Job 75	test	0	2026-05-05 02:24:00	RUNNING


👨‍💻 Author

Vishal Bhawari
Java Backend Developer | Spring Boot | Microservices | Kafka | System Design
