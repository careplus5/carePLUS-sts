package com.kosta.care.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.care.dto.TestRequestDto;
import com.kosta.care.entity.TestRequest;
import com.kosta.care.repository.TestRequestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestRequestServiceImpl implements TestRequestService {

	@Autowired
	private TestRequestRepository testRequestRepository;
	
	@Override
	public TestRequestDto getLatestTestRequestByPatNum(Long patNum) throws Exception {
		return testRequestRepository.findLatestByPatNum(patNum);
	}

}
