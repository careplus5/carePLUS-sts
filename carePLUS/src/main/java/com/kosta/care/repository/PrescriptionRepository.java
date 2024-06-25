package com.kosta.care.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.care.dto.PrescriptionDto;
import com.kosta.care.entity.Prescription;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
	// 환자별 처방 리스트 확인
	List<Prescription> findByPatNum (Long patNum) throws Exception;
}
