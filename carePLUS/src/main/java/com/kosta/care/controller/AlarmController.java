package com.kosta.care.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.care.dto.AlarmDto;
import com.kosta.care.service.AlarmService;

@RestController
public class AlarmController {

	@Autowired
	private AlarmService alarmService;

	@GetMapping("/sendAlarm/{empNum}")
	public ResponseEntity<String> sendAlarm(@PathVariable Long empNum) {
		try {
			alarmService.sendAlarmByEmpNum(empNum, "공지사항", "된거맞지?!");
			return new ResponseEntity<String>("알림전송 성공,", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("알림전송 실패", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/alarmList")
	public ResponseEntity<List<AlarmDto>> alarmList(@RequestBody Map<String, Object> param) {
		Long empNum = Long.parseLong((String) param.get("empNum"));
		try {
			List<AlarmDto> alaramDtoList = alarmService.sendNotCheckAlarm(empNum);
			return new ResponseEntity<List<AlarmDto>>(alaramDtoList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<AlarmDto>>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/checkAlarm")
	public ResponseEntity<Long> checkAlarm(@RequestBody Map<String, Long> param) {
		System.out.println(param);
		Long alarmNum = param.get("alarmNum");
		try {
			alarmService.checkAlarm(alarmNum);
			return new ResponseEntity<Long>(alarmNum, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/changeFCMToken")
	public ResponseEntity<String> changeFCMToken(@RequestBody Map<String, Object> param) {
		System.out.println(param);
		String fcmToken = (String) param.get("fcmToken");
		System.out.println(fcmToken);
		Long empNum = Long.parseLong((String) param.get("empNum"));
		try {
			alarmService.registFcmToken(fcmToken, empNum);
			return new ResponseEntity<String>("변경 성공", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("변경 실패", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/deleteAlarm")
	public ResponseEntity<Boolean> deleteAlarm(@RequestBody Map<String, Object> param) {
		Long empNum = Long.parseLong((String) param.get("empNum"));
		try {
			return new ResponseEntity<Boolean>(alarmService.deleteAlarmList(empNum), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/changeAlarmStatus")
	public ResponseEntity<String> changeAlarmStatus(@RequestBody Map<String, Object> param) {
		String empNumString = (String) param.get("empNum");
		Long empNum = Long.parseLong(empNumString);
		System.out.println(empNum);
		String alarmName = (String) param.get("alarmName");
		System.out.println(alarmName);
		try {
			return new ResponseEntity<String>(alarmService.changeAlarmStatus(empNum, alarmName), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/checkAlarmStatus")
	public ResponseEntity<List<Boolean>> checkAlarmStatus(@RequestBody Map<String, Object> param) {
		Long empNum = Long.parseLong((String) param.get("empNum"));
		try {
			return new ResponseEntity<List<Boolean>>(alarmService.checkAlarmStatus(empNum), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Boolean>>(HttpStatus.BAD_REQUEST);
		}
	}
}
