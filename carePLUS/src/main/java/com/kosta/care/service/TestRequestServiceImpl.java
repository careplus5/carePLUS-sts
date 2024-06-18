package com.kosta.care.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.care.dto.TestRequestDto;
import com.kosta.care.entity.TestRequest;
import com.kosta.care.repository.TestRequestDslRepository;
import com.kosta.care.repository.TestRequestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestRequestServiceImpl implements TestRequestService {

	@Autowired
	private TestRequestDslRepository testRequestDslRepository;
	
	@Autowired
    private TestRequestRepository testRequestRepository;
	
	@Override
	public TestRequestDto getLatestTestRequestByPatNum(Long patNum) throws Exception {
		return testRequestDslRepository.findLatestByPatNum(patNum);
	}
	
	@Override
	public void updateStatus(Long testRequestNum, String testRequestAcpt) throws Exception {
	    Optional<TestRequest> optionalTestRequest = testRequestRepository.findById(testRequestNum);
	    if (optionalTestRequest.isPresent()) {
	        TestRequest testRequest = optionalTestRequest.get();
	        // 상태를 업데이트
	        testRequest.setTestRequestAcpt(testRequestAcpt);
	        // 변경된 엔티티를 데이터베이스에 저장
	        testRequestRepository.save(testRequest);
	    } else {
	        throw new EntityNotFoundException("Test request not found with id: " + testRequestNum);
	    }
	}

	@Override
	public List<TestRequestDto> getAllTestRequests(String dept2Name) throws Exception {
	    // findPendingTestRequests 메서드를 사용해 '검사요청' 상태인 TestRequest 객체들을 가져옴
	    List<TestRequest> testRequests = testRequestRepository.findPendingTestRequests();

	    // 가져온 TestRequest 객체들을 TestRequestDto 객체로 변환하고, 조건에 맞는 것만 필터링하여 반환
	    return testRequests.stream()
	                       .filter(testRequest -> testRequest.getTestName().equals(dept2Name)) // testName이 dept2Name과 같은 요소만 필터링
	                       .map(TestRequest::toDto) // TestRequest 객체를 TestRequestDto 객체로 변환
	                       .collect(Collectors.toList()); // 변환된 객체들을 리스트로 수집
	}


}
