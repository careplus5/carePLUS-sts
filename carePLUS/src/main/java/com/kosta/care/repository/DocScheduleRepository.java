package com.kosta.care.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.dto.CalendarDto;
import com.kosta.care.entity.DocSchedule;

public interface DocScheduleRepository extends JpaRepository<DocSchedule, Long> {

	List<DocSchedule> findByDocNum(Long empNum);

}
