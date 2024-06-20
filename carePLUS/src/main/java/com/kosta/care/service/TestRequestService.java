package com.kosta.care.service;

import java.util.List;
import java.util.Map;

import com.kosta.care.dto.TestRequestDto;
import com.kosta.care.entity.TestRequest;

public interface TestRequestService {
	
	TestRequestDto getLatestTestRequestByPatNum(Long patNum) throws Exception;

	void updateRequestStatus(Long testRequestNum, String testRequestAcpt) throws Exception;
	List<TestRequestDto> getAllTestRequests(String dept2Name) throws Exception;
}
