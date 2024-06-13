package com.kosta.care.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{
	
	Optional<Patient> findByPatJumin(String patJumin);
}
