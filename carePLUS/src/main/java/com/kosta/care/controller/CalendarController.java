package com.kosta.care.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.care.dto.EmployeeDto;
import com.kosta.care.service.EmployeeSerivce;
import com.kosta.care.service.CalendarService;


@RestController
public class CalendarController {
	@Autowired
	private EmployeeSerivce employeeSerivce;
	@Autowired
	private CalendarService calendarService;

	@GetMapping("/userInfo")
	public ResponseEntity<EmployeeDto> getUserInfo(@RequestParam Map<String, Object> param) {
		Long userId = Long.parseLong((String)param.get("userId"));
		try {
			return new ResponseEntity<EmployeeDto>(employeeSerivce.detail(userId), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<EmployeeDto>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/schedules")
    public ResponseEntity<Map<String,Object>> getAllTestTimeList(@RequestParam("userId") Long userId) {
        try {
             Map<String, Object> schedules = calendarService.getAllTestTimeList(userId);
             if (schedules == null || schedules.isEmpty()) {
                 throw new Exception("No schedules found");
             }
             return new ResponseEntity<>(schedules, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
