package com.kosta.care.service;

import java.util.List;


import com.kosta.care.dto.TestRequestDto;


public interface TestRequestService {
	
	TestRequestDto getLatestTestRequestByPatNum(Long patNum) throws Exception;

	void updateRequestStatus(Long testRequestNum, String testRequestAcpt) throws Exception;
	List<TestRequestDto> getAllTestRequests(String dept2Name) throws Exception;
}
