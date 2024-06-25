package com.kosta.care.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.AdmissionRequest;

public interface AdmissionRequestRepository extends JpaRepository<AdmissionRequest, Long> {
	AdmissionRequest findByPatNumAndAdmissionRequestAcpt(Long patNum, String admissionRequestAcpt);
}
