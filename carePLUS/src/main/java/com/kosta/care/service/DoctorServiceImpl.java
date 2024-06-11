package com.kosta.care.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.care.entity.Doctor;
import com.kosta.care.repository.DoctorRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	private DoctorRepository doctorRepository;
	
	@Override
	public List<Doctor> doctorList(Long departmentNum) throws Exception {
		return doctorRepository.findByDepartmentNum(departmentNum);
	}

}
