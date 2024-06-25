package com.kosta.care.service;


import java.io.File;
import java.sql.Date;


import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import com.kosta.care.dto.SurgeryRequestDto;
import com.kosta.care.dto.TestDto;
import com.kosta.care.entity.DiagnosisDue;
import com.kosta.care.entity.Patient;
import com.kosta.care.entity.Test;
import com.kosta.care.entity.TestFile;

import com.kosta.care.repository.TestRepository;
import com.kosta.care.repository.DiagnosisDueRepository;
import com.kosta.care.repository.PatientRepository;
import com.kosta.care.repository.SurgeryRequestRepository;

import com.kosta.care.repository.TestFileRepository;

@Service
public class TestServiceImpl implements TestService {
	
	@Value("${upload.path}")
	private String uploadPath;

    @Autowired
    private TestRepository testRepository;
    @Autowired
    private TestFileRepository testFileRepository;
    @Autowired
    private SurgeryRequestRepository surgeryRequestRepository;
    @Autowired
    private DiagnosisDueRepository diagnosisDueRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<TestDto> getTodayAcceptedTests(String dept2Name,  Date today) throws Exception {
        List<Test> tests = testRepository.findByTestNameAndTestAppointmentDate(dept2Name,today);
        System.out.println(tests);
        // Convert to DTO
        return tests.stream()
	            .map(t->{
	            	TestDto testDto = TestDto.builder().build();
	            	Optional<Patient> opatient = patientRepository.findById(t.getPatNum());
	            	if(opatient.isPresent()) {
	            		Patient patient = opatient.get();
	    	    		testDto.setPatName(patient.getPatName());
	    	    		testDto.setPatJumin(patient.getPatJumin());
	    	    		testDto.setPatGender(patient.getPatGender());
	    	    		testDto.setPatBloodType(patient.getPatBloodType());
	            	}
	            	testDto.setTestNum(t.getTestNum());
	            	testDto.setDocDiagnosisNum(t.getDocDiagnosisNum());
	            	testDto.setTestRequestNum(t.getTestRequestNum());
	            	testDto.setDocNum(t.getDocNum());
	            	testDto.setMetNum(t.getMetNum());
	            	testDto.setPatNum(t.getPatNum());
	            	testDto.setTestName(t.getTestName());
	            	testDto.setTestStatus(t.getTestStatus());
	            	testDto.setTestPart(t.getTestPart());
	            	testDto.setTestAppointmentDate(t.getTestAppointmentDate());
	            	testDto.setTestAppointmentTime(t.getTestAppointmentTime());
	            	testDto.setTestOutInspectRecord(t.getTestOutInspectRecord());
	            	return testDto;
	            })
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
        List<Test> tests = testRepository.findByTestNameAndTestStatusAndPatient_PatNum(dept2Name,"complete",patNum);
        System.out.println(tests);
        return tests.stream()
	            .map(t->{
	            	TestDto testDto = TestDto.builder().build();
	            	Optional<Patient> opatient = patientRepository.findById(t.getPatNum());
	            	if(opatient.isPresent()) {
	            		Patient patient = opatient.get();
	    	    		testDto.setPatName(patient.getPatName());
	    	    		testDto.setPatJumin(patient.getPatJumin());
	    	    		testDto.setPatGender(patient.getPatGender());
	    	    		testDto.setPatBloodType(patient.getPatBloodType());
	            	}
	            	testDto.setTestNum(t.getTestNum());
	            	testDto.setDocDiagnosisNum(t.getDocDiagnosisNum());
	            	testDto.setTestRequestNum(t.getTestRequestNum());
	            	testDto.setDocNum(t.getDocNum());
	            	testDto.setMetNum(t.getMetNum());
	            	testDto.setPatNum(t.getPatNum());
	            	testDto.setTestName(t.getTestName());
	            	testDto.setTestStatus(t.getTestStatus());
	            	testDto.setTestPart(t.getTestPart());
	            	testDto.setTestAppointmentDate(t.getTestAppointmentDate());
	            	testDto.setTestAppointmentTime(t.getTestAppointmentTime());
	            	testDto.setTestOutInspectRecord(t.getTestOutInspectRecord());
	            	testDto.setTestDate(t.getTestDate());
	            	testDto.setTestResult(t.getTestResult());
	            	testDto.setTestNotice(t.getTestNotice());
	            	return testDto;
	            })
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

            

            // Retrieve the Test entity by testNum
            Optional<Test> optionalTest = testRepository.findById(testNum);
            if (optionalTest.isPresent()) {
                Test test = optionalTest.get();

                // Create and save the TestFile entity
                TestFile testFile = TestFile.builder()
                                            .test(test)
                                            .testFileType(file.getContentType())
                                            .testFilePath(uploadPath)
                                            .testFileSize(file.getSize())
                                            .testFileUploadDate(new Date(System.currentTimeMillis()))
                                            .testMetNum(metNum)
                                            .build();

                testFileRepository.save(testFile);
                
             // Save the file locally
                String filePath = uploadPath + testFile.getTestFileNum();
                System.out.println(filePath);
                File dest = new File(filePath);
                file.transferTo(dest);
                
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
    @Override
    public Map<String,Object> getAllTestTimeList(Long userId) throws Exception {
    	if (userId == null) {
			throw new UsernameNotFoundException("empNum이 Null 찍히는데요?");
		}
    	
    	Map<String, Object> res = new HashMap<>();


		String jobString = userId+"".substring(0, 2);
		


		
		switch (jobString) {
		case "11":
			List<SurgeryRequestDto> surSchedules= surgeryRequestRepository.findByDocNum(userId).stream()
				.map(sr-> SurgeryRequestDto.builder()
							.surgeryRequestNum(sr.getSurgeryRequestNum())
							.patNum(sr.getPatNum())
							.surPeriod(sr.getSurPeriod())
							.surReason(sr.getSurReason())
							.surDate(sr.getSurDate())
							.departmentNum(sr.getDepartmentNum())
							.docNum(sr.getDocNum())
							.build()).collect(Collectors.toList());
			
			res.put("surSchedules", surSchedules);
			
			List<DiagnosisDue> digSchedules = diagnosisDueRepository.findByDocNum(userId);
			res.put("digSchedules", digSchedules);
			break;
		case "14":
			List<Test> testList = testRepository.findByMetNum(userId);
			List<TestDto> schedules = testList.stream()
				            .map(t->{
				            	TestDto testDto = TestDto.builder().build();
				            	Optional<Patient> opatient = patientRepository.findById(t.getPatNum());
				            	if(opatient.isPresent()) {
				            		Patient patient = opatient.get();
				    	    		testDto.setPatName(patient.getPatName());
				    	    		testDto.setPatJumin(patient.getPatJumin());
				    	    		testDto.setPatGender(patient.getPatGender());
				    	    		testDto.setPatBloodType(patient.getPatBloodType());
				            	}
				            	testDto.setTestNum(t.getTestNum());
				            	testDto.setDocDiagnosisNum(t.getDocDiagnosisNum());
				            	testDto.setTestRequestNum(t.getTestRequestNum());
				            	testDto.setDocNum(t.getDocNum());
				            	testDto.setMetNum(t.getMetNum());
				            	testDto.setPatNum(t.getPatNum());
				            	testDto.setTestName(t.getTestName());
				            	testDto.setTestStatus(t.getTestStatus());
				            	testDto.setTestPart(t.getTestPart());
				            	testDto.setTestAppointmentDate(t.getTestAppointmentDate());
				            	testDto.setTestAppointmentTime(t.getTestAppointmentTime());
				            	testDto.setTestOutInspectRecord(t.getTestOutInspectRecord());
				            	testDto.setTestDate(t.getTestDate());
				            	testDto.setTestResult(t.getTestResult());
				            	testDto.setTestNotice(t.getTestNotice());
				            	return testDto;
				            })
				            .collect(Collectors.toList());
			
			res.put("schedules", schedules);
			break;
		default:
			throw new Exception("Unexpected value: " + jobString);
		}
		return res;
    }
}
