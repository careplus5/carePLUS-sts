package com.kosta.care.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.SurgeryRequest;

public interface SurgeryRequestRepository extends JpaRepository<SurgeryRequest, Long> {

	List<SurgeryRequest> findByDocNum(Long userId);

}