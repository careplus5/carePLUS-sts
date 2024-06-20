package com.kosta.care.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
	Job findByJobName(String jobName);
}
