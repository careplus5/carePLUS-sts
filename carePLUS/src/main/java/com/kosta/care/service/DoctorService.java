package com.kosta.care.service;

import java.util.List;

import com.kosta.care.entity.Doctor;

public interface DoctorService {
	
	List<Doctor> doctorList(Long departmentNum) throws Exception;
	
}
