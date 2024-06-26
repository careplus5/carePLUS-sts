package com.kosta.care.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

import com.kosta.care.dto.TestDto;
import com.kosta.care.entity.TestFile;

public interface TestService {
	void updateTestStatus(Long testNum, String testStatus) throws Exception;
    List<TestDto> getTodayAcceptedTests(String dept2Name, Date today) throws Exception;
	List<TestDto> getPatientAllTestList(String dept2Name, Long patNum) throws Exception;
	void uploadTestFile(MultipartFile file, Long testNum, Long metNum) throws Exception;
	List<TestFile> getTestFile(Long testNum) throws Exception;
	void uploadTestNotice(Long testNum, Long metNum, String testNotice) throws Exception;
	
}
