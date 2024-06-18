package com.kosta.care.service;

import java.util.List;

import com.kosta.care.dto.AlarmDto;

public interface AlarmService {
	void sendAlarmByEmpNum(Long empNum, String alarmCategory,String alarmContent) throws Exception;
	void sendAlarmListByJobNum(Long jobNum, String alarmContent, String alarmCategoy) throws Exception;
	void sendAlarmListByDepartmentNum(String departmentName, String alarmContent, String alarmCategoty) throws Exception;
	List<AlarmDto> sendNotCheckAlarm(Long empNum) throws Exception;
	Long checkAlarm(Long alarmNum) throws Exception;
	void registFcmToken(String fcmToken, Long empNum) throws Exception;
	Boolean deleteAlarmList (Long empNum) throws Exception;
	String changeAlarmStatus(Long empNum, String alarmName) throws Exception;
	List<Boolean> checkAlarmStatus(Long empNum) throws Exception;
}
