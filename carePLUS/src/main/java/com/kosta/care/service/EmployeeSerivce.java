package com.kosta.care.service;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.care.dto.EmployeeDto;

public interface EmployeeSerivce {

	Long Join(EmployeeDto employeeDto, MultipartFile file) throws Exception;
	Long Delete(Long empNum) throws Exception;
	EmployeeDto Detail(Long empNum) throws Exception;
	EmployeeDto Modify(Long empNum, Long departmentNum,Long jobNum,String department2Name,
			String empPosition, String empName,String empTel,String empEmail,String empPassword, MultipartFile file) throws Exception;
	
}
