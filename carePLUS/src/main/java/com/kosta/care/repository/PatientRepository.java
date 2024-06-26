package com.kosta.care.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{
	
	Optional<Patient> findByPatJumin(String patJumin);
	
	// 환자 검색 관련 ( 환자번호, 환자이름, 환자 주민 )
	List<Patient> findByPatNum(Long patNum);

	List<Patient> findByPatNumContains(Long patNum);

	List<Patient> findByPatNameContains(String patName);
	List<Patient> findByPatJuminContains(String patJumin);
	
	Optional<Patient> findByPatNumOrPatJumin(Long patNum, String patJumin) throws Exception;

}
