package com.kosta.care.repository;

import java.util.List;
import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.Test;


public interface TestRepository extends JpaRepository<Test, Long> {
	
    List<Test> findByTestNameAndTestAppointmentDate(String testName, Date testAppointmentDate);

    List<Test> findByTestNameAndTestStatusAndPatNum(String testName, String testStatus, Long patNum);
    
    List<Test> findByMetNum(Long metNum);
}
