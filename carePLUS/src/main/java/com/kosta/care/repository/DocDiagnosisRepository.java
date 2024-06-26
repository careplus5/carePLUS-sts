package com.kosta.care.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.DocDiagnosis;

public interface DocDiagnosisRepository extends JpaRepository<DocDiagnosis, Long> {

	
	// 처방전 발급 (최신순으로 정렬) 
	List<DocDiagnosis> findByPatNumOrderByDocDiagnosisNumDesc(Long patNum);

	List<DocDiagnosis> findByPatNum(Long patNum) throws Exception;	

}
