package com.kosta.care.service;


import java.util.List;
import java.util.Map;

import com.kosta.care.dto.CalendarDto;


public interface CalendarService {

//	Map<String,Object> getAllTestTimeList(Long userId) throws Exception;

	List<CalendarDto> getAllSchedules(Long empNum) throws Exception;
}
