package com.kosta.care.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import java.sql.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.care.dto.TestDto;
import com.kosta.care.entity.Test;
import com.kosta.care.repository.TestRepository;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestRepository testRepository;

    @Override
    public List<TestDto> getTodayAcceptedTests(String dept2Name,  Date today) throws Exception {
        List<Test> tests = testRepository.findByTestNameAndTestAppointmentDate(dept2Name,today);
        System.out.println(tests);
        // Convert to DTO
        return tests.stream()
	            .map(Test::toDto)
	            .collect(Collectors.toList());
    }
    @Override
	public void updateTestStatus(Long testNum, String testStatus) throws Exception {
	    Optional<Test> optionalTest = testRepository.findById(testNum);
	    if (optionalTest.isPresent()) {
	        Test test = optionalTest.get();
	        // 상태를 업데이트
	        test.setTestStatus(testStatus);
	        test.setTestDate(new Date(System.currentTimeMillis()));
	        // 변경된 엔티티를 데이터베이스에 저장
	        testRepository.save(test);
	    } else {
	        throw new EntityNotFoundException("Test request not found with id: " + testNum);
	    }
	}
    @Override
    public List<TestDto> getPatientAllTestList(String dept2Name,  Long patNum) throws Exception {
        List<Test> tests = testRepository.findByTestNameAndTestStatusAndPatient_PatNum(dept2Name,"완료",patNum);
        System.out.println(tests);
        // Convert to DTO
        return tests.stream()
	            .map(Test::toDto)
	            .collect(Collectors.toList());
    }
}
