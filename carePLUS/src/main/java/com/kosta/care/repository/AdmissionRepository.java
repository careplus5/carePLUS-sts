package com.kosta.care.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.Admission;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {
	
	Admission findByBedsNum(Long bedsNum);
	
	List<Admission> findByPatNumOrderByAdmissionNumDesc(Long patNum);
		
}
