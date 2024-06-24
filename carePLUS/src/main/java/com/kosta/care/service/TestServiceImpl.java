package com.kosta.care.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import java.io.File;
import java.sql.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.care.dto.TestDto;
import com.kosta.care.entity.Test;
import com.kosta.care.entity.TestFile;
import com.kosta.care.repository.TestRepository;
import com.kosta.care.repository.TestFileRepository;

@Service
public class TestServiceImpl implements TestService {
	
	@Value("${upload.path}")
	private String uploadPath;

    @Autowired
    private TestRepository testRepository;
    @Autowired
    private TestFileRepository testFileRepository;

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
    @Override
    @Transactional
    public void uploadTestFile(MultipartFile file, Long testNum, Long metNum) throws Exception {
    	if (file != null && !file.isEmpty()) {
            File directory = new File(uploadPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Save the file locally
            String filePath = uploadPath + File.separator + file.getOriginalFilename();
            File dest = new File(filePath);
            file.transferTo(dest);

            // Retrieve the Test entity by testNum
            Optional<Test> optionalTest = testRepository.findById(testNum);
            if (optionalTest.isPresent()) {
                Test test = optionalTest.get();

                // Create and save the TestFile entity
                TestFile testFile = TestFile.builder()
                                            .test(test)
                                            .testFileType(file.getContentType())
                                            .testFilePath(filePath)
                                            .testFileSize(file.getSize())
                                            .testFileUploadDate(new Date(System.currentTimeMillis()))
                                            .testMetNum(metNum)
                                            .build();

                testFileRepository.save(testFile);
            } else {
                throw new EntityNotFoundException("Test with testNum " + testNum + " not found");
            }
        } else {
            throw new IllegalArgumentException("File is null or empty");
        }
    }
    @Override
    public List<TestFile> getTestFile(Long testNum) throws Exception {
    	List<TestFile> testFiles = testFileRepository.findByTest_testNum(testNum);
        System.out.println(testFiles);
        
        return testFiles;
    }
    @Override
	public void uploadTestNotice(Long testNum, Long metNum, String testNotice) throws Exception {
	    Optional<Test> optionalTest = testRepository.findById(testNum);
	    if (optionalTest.isPresent()) {
	        Test test = optionalTest.get();
	        // 상태를 업데이트
	        test.setTestNotice(testNotice);
	        test.setMetNum(metNum);
	        // 변경된 엔티티를 데이터베이스에 저장
	        testRepository.save(test);
	    } else {
	        throw new EntityNotFoundException("Test request not found with id: " + testNum);
	    }
	}
}
