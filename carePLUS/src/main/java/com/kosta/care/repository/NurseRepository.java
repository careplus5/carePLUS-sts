package com.kosta.care.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.Nurse;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long> {
	
	Nurse findByNurNum(Long nurNum);
}
