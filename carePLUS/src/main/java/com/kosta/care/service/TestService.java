package com.kosta.care.service;

import java.util.List;
import java.sql.Date;

import com.kosta.care.dto.TestDto;

public interface TestService {
	void updateTestStatus(Long testNum, String testStatus) throws Exception;
    List<TestDto> getTodayAcceptedTests(String dept2Name, Date today) throws Exception;
	List<TestDto> getPatientAllTestList(String dept2Name, Long patNum) throws Exception;
}
