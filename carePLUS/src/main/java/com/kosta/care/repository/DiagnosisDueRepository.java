package com.kosta.care.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.DiagnosisDue;

public interface DiagnosisDueRepository extends JpaRepository<DiagnosisDue, Long> {
	List<DiagnosisDue> findByDocNum(Long docNum);
	List<DiagnosisDue> findByDocNumAndDiagnosisDueDate(Long docNum, Date diagnosisDueDate);

}
