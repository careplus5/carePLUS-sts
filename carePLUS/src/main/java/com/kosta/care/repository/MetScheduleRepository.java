package com.kosta.care.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.dto.CalendarDto;
import com.kosta.care.entity.MetSchedule;

public interface MetScheduleRepository extends JpaRepository<MetSchedule, Long> {

	List<MetSchedule> findByMetNum(Long empNum);

}
