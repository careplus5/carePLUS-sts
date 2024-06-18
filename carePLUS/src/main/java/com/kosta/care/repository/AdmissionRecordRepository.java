package com.kosta.care.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.AdmissionRecord;

public interface AdmissionRecordRepository extends JpaRepository<AdmissionRecord, Long> {

}
