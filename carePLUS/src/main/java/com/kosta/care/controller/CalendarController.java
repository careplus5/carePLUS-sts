package com.kosta.care.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.care.dto.CalendarDto;
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
	
//	@GetMapping("/schedules")
//    public ResponseEntity<Map<String,Object>> getAllTestTimeList(@RequestParam("userId") Long userId) {
//        try {
//             Map<String, Object> schedules = calendarService.getAllTestTimeList(userId);
//             if (schedules == null || schedules.isEmpty()) {
//                 throw new Exception("No schedules found");
//             }
//             return new ResponseEntity<>(schedules, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
	@GetMapping("/schedules")
	public ResponseEntity<List<CalendarDto>> getAllSchedules(
			@RequestParam(name = "userId", required = false) Long userId) {
		try {
			// calendarService.getAllSchedules(userId) 호출 결과를 반환합니다.
			List<CalendarDto> schedules = calendarService.getAllSchedules(userId);
			System.out.println("프론트에서 가져온 아뒤 " + userId);
			return new ResponseEntity<List<CalendarDto>>(schedules, HttpStatus.OK); // HTTP 200 OK 상태와 함께 스케줄 목록 반환
		} catch (Exception e) {
			// 예외가 발생하면 스택 트레이스를 출력합니다.
			e.printStackTrace(); // 예외 로깅

			// 예외 처리 후 HTTP 500 Internal Server Error 상태와 빈 리스트 반환
			return new ResponseEntity<List<CalendarDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}
