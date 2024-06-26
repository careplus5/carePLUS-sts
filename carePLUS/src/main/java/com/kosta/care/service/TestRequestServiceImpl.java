package com.kosta.care.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.care.dto.TestRequestDto;
import com.kosta.care.entity.Patient;
import com.kosta.care.entity.TestRequest;
import com.kosta.care.repository.PatientRepository;
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
	@Autowired
	private PatientRepository patientRepository;
	
	@Override
	public TestRequestDto getLatestTestRequestByPatNum(Long patNum) throws Exception {
		return testRequestDslRepository.findLatestByPatNum(patNum);
	}
	
	@Override
	public void updateRequestStatus(Long testRequestNum, String testRequestAcpt) throws Exception {
		System.out.println("AA"+testRequestAcpt);
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

	    List<TestRequest> testRequestList = testRequestRepository.findByTestRequestAcptAndTestName("request",dept2Name);
	    
	    return testRequestList.stream().map(tr->{
	    	TestRequestDto testRequestDto = TestRequestDto.builder()
	    			.patNum(tr.getPatNum())
	    			.testRequestNum(tr.getTestRequestNum()).build();
	    	Optional<Patient> opatient = patientRepository.findById(tr.getPatNum());
	    	if(opatient.isPresent()) {
	    		Patient patient = opatient.get();
	    		testRequestDto.setPatNum(patient.getPatNum());
	    		testRequestDto.setPatName(patient.getPatName());
	    		testRequestDto.setPatJumin(patient.getPatJumin());
	    		testRequestDto.setPatGender(patient.getPatGender());
	    		testRequestDto.setPatBloodType(patient.getPatBloodType());
	    	}
	    	testRequestDto.setDocNum(tr.getDocNum());
	    	testRequestDto.setTestName(tr.getTestName());
	    	testRequestDto.setTestRequestAcpt(tr.getTestRequestAcpt());
	    	testRequestDto.setTestPart(tr.getTestPart());
	    	return testRequestDto;
	    }).collect(Collectors.toList());

	}


}
