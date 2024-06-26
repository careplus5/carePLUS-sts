package com.kosta.care.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.Admission;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {

	Admission findByBedsNum(Long bedsNum);

	// 환자 번호로 리스트 조회
	List<Admission> findByPatNum(Long patNum) throws Exception;
	
	// 입원 환자 조회 최신순으로
	List<Admission> findByPatNumOrderByAdmissionNumDesc(Long patNum) throws Exception;

}
