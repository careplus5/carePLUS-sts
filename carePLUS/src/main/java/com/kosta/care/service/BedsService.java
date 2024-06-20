package com.kosta.care.service;

import java.util.List;

import com.kosta.care.entity.Admission;

public interface BedsService {
	
	List<Integer> getWardsByDepartment(Integer department);
    List<Integer> getRoomsByWard(Integer ward);
    List<Integer> getBedsByRoom(Integer room);
//    List<Long> getBedsByRoom(Integer room);
    Admission getBedDetails(Long bed);
	
	// 담당과 눌렀을 때
	
	// 병동 눌렀을 때
	
	// 병실 눌렀을 때
	
	// 베드 눌렀을 때
	
	// 베드 조회

}
