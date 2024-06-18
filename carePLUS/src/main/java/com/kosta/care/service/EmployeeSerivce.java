package com.kosta.care.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.care.dto.EmployeeDto;
import com.kosta.care.util.PageInfo;

public interface EmployeeSerivce {

	Long join(EmployeeDto employeeDto, MultipartFile file) throws Exception;
	Long delete(Long empNum) throws Exception;
	EmployeeDto detail(Long empNum) throws Exception;
	EmployeeDto modify(EmployeeDto employeeDto, MultipartFile file) throws Exception;
	List<EmployeeDto> employeeListByPage(String jobName, PageInfo pageInfo, String type, String word) throws Exception;
}
