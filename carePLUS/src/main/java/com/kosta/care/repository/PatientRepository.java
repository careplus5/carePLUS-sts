package com.kosta.care.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{
	
}
