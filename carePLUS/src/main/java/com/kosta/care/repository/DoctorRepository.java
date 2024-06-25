package com.kosta.care.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.Doctor;
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	
	Doctor findByDocNum(Long docNum);
	 Page<Doctor> findByDocNumContains(String docNum, PageRequest pageRequest);
	 Page<Doctor> findByJobNumContains(String jobNum, PageRequest pageRequest);
	 Page<Doctor> findByDepartmentNameContains(String departmentName, PageRequest pageRequest);
	 Page<Doctor> findByDocNameContains(String docName, PageRequest pageRequest);
//	 
	List<Doctor> findByDepartmentNum (Long departmentNum );
	List<Doctor> findByJobNum (Long jobNum);
	
}
