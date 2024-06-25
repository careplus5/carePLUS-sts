package com.kosta.care.controller;

import java.util.List;
import java.util.Map;
import java.io.FileInputStream;
import java.io.File;
import java.io.OutputStream;
import java.sql.Date;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.care.dto.TestRequestDto;
import com.kosta.care.entity.TestFile;
import com.kosta.care.dto.TestDto;
import com.kosta.care.service.TestRequestService;
import com.kosta.care.service.TestService;

@RestController
public class MetMainController {

	@Autowired
	private TestRequestService testRequestService;
	@Autowired
	private TestService testService;



	@PostMapping("/updateRequestStatus")
    public ResponseEntity<Boolean> updateRequestStatus(@RequestBody Map<String, Object> param) {
        try {
        	System.out.println(param);
            testRequestService.updateRequestStatus(
            		Long.valueOf(String.valueOf(param.get("id"))), String.valueOf(param.get("testRequestAcpt")));
            return new ResponseEntity<Boolean>(true, HttpStatus.OK); // 업데이트 성공 시 200 OK 응답
        } catch (EntityNotFoundException e) {
            // 예외 처리: 검색된 엔터티가 없음
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // 예외 처리: 다른 모든 예외
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

	@GetMapping("/testRequestList")
    public ResponseEntity<List<TestRequestDto>> fetchData(@RequestParam(name = "dept2Name", required = false) String dept2Name) {
        try {
            List<TestRequestDto> testRequestDtos = testRequestService.getAllTestRequests(dept2Name);
            System.out.println(dept2Name);
            return new ResponseEntity<List<TestRequestDto>>(testRequestDtos,HttpStatus.OK); // 요청 성공 시 200 OK 응답과 함께 데이터 반환
        } catch (Exception e) {
            // 예외 처리: 다른 모든 예외
            return new ResponseEntity<List<TestRequestDto>>(HttpStatus.BAD_REQUEST);
        }
    }
	
	@PostMapping("/updateTestStatus")
    public ResponseEntity<Boolean> updateTestStatus(@RequestBody Map<String, Object> param) {
        try {
            testService.updateTestStatus(
            		Long.valueOf(String.valueOf(param.get("id"))), String.valueOf(param.get("testStatus")));
            return new ResponseEntity<Boolean>(true, HttpStatus.OK); // 업데이트 성공 시 200 OK 응답
        } catch (EntityNotFoundException e) {
            // 예외 처리: 검색된 엔터티가 없음
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // 예외 처리: 다른 모든 예외
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

	@GetMapping("/testTodayList")
    public ResponseEntity<List<TestDto>> getTodayAcceptedTests(@RequestParam("dept2Name")String dept2Name,@RequestParam("today")String today ) {
        try {
         	
        	List<TestDto> testDtos =  testService.getTodayAcceptedTests(dept2Name, Date.valueOf(today));
        	return new ResponseEntity<List<TestDto>>(testDtos, HttpStatus.OK); // 요청 성공 시 200 OK 응답과 함께 데이터 반환
        } catch (Exception e) {
        	e.printStackTrace();
            // 예외 처리: 다른 모든 예외
            return new ResponseEntity<List<TestDto>>(HttpStatus.BAD_REQUEST);
        }
    }
	
	@GetMapping("/testAllList")
    public ResponseEntity<List<TestDto>> getPatientAllTestList(@RequestParam("dept2Name")String dept2Name,@RequestParam("patNum")Long patNum ) {
        try {
         	
        	List<TestDto> testDtos =  testService.getPatientAllTestList(dept2Name, patNum);
            return new ResponseEntity<List<TestDto>>(testDtos, HttpStatus.OK); // 요청 성공 시 200 OK 응답과 함께 데이터 반환
        } catch (Exception e) {
            // 예외 처리: 다른 모든 예외
        	e.printStackTrace();
            return new ResponseEntity<List<TestDto>>(HttpStatus.BAD_REQUEST);
        }
    }
	
	@PostMapping("/uploadTestFile")
    public ResponseEntity<Boolean> uploadTestFile(@RequestParam("image") MultipartFile file,
                                              @RequestParam("testNum") Long testNum,
                                              @RequestParam("metNum") Long metNum) {
        try {
        	testService.uploadTestFile(file, testNum, metNum);
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getTestFile")
    public ResponseEntity<List<TestFile>> getTestFile(@RequestParam("testNum") Long testNum) {
        try {
        	List<TestFile> testFileList = testService.getTestFile(testNum);
            return new ResponseEntity<List<TestFile>>(testFileList,HttpStatus.OK);
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity<List<TestFile>>(HttpStatus.NOT_FOUND);
        }
    }
    
	@PostMapping("/uploadTestNotice")
    public ResponseEntity<Boolean> uploadTestNotice(@RequestParam("testNum") Long testNum,
                                              @RequestParam("metNum") Long metNum,
                                              @RequestParam("testNotice") String testNotice) {
		System.out.println(testNum);
        try {
        	testService.uploadTestNotice(testNum, metNum, testNotice);
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

	
	
}
