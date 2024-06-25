package com.kosta.care.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.OperationUseCheck;

public interface OperationUseCheckRepository extends JpaRepository<OperationUseCheck, Long> {
	List<OperationUseCheck> findByUseDate(Date useDate);
}
