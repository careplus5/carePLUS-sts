package com.kosta.care.service;

import java.util.Map;

import com.kosta.care.dto.TestRequestDto;
import com.kosta.care.entity.TestRequest;

public interface TestRequestService {
	
	TestRequestDto getLatestTestRequestByPatNum(Long patNum) throws Exception;
}
