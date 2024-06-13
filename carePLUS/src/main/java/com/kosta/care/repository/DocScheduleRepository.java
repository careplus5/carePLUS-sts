package com.kosta.care.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.DocSchedule;

public interface DocScheduleRepository extends JpaRepository<DocSchedule, Long> {

}
