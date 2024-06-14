package com.kosta.care.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.Doctor;
import com.kosta.care.entity.Nurse;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long> {
	
	Nurse findByNurNum(Long nurNum);
	List<Nurse> findByJobNum (Long jobNum);
}
