package com.kosta.care.service;


import java.util.Map;


public interface CalendarService {
//	List<CalendarDto> getAllSchedules(Long empNum) throws Exception;
	Map<String,Object> getAllTestTimeList(Long userId) throws Exception;
}
