package com.kosta.care.controller;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.care.dto.TestRequestDto;
import com.kosta.care.service.TestRequestService;

@RestController
public class MetMainController {

	@Autowired
	private TestRequestService testRequestService;
	


	@PostMapping("/updateStatus")
    public ResponseEntity<Boolean> updateStatus(@RequestBody Map<String, Object> param) {
        try {
        	System.out.println(param);
            testRequestService.updateStatus(
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
            return new ResponseEntity<List<TestRequestDto>>(testRequestDtos,HttpStatus.OK); // 요청 성공 시 200 OK 응답과 함께 데이터 반환
        } catch (Exception e) {
            // 예외 처리: 다른 모든 예외
            return new ResponseEntity<List<TestRequestDto>>(HttpStatus.BAD_REQUEST);
        }
    }

}
