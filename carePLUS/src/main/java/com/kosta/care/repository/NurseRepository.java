package com.kosta.care.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.Nurse;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long> {
	
	Nurse findByNurNum(Long nurNum);
	List<Nurse> findByJobNum (Long jobNum);
	
	Page<Nurse> findByNurNumContains(Long nurNum, PageRequest pageRequest);
	Page<Nurse> findByJobNumContains(String jobNum, PageRequest pageRequest);
	Page<Nurse> findByDepartmentNameContains(String jobName, PageRequest pageRequest);
	Page<Nurse> findByNurNameContains(String jobName, PageRequest pageRequest);
}
