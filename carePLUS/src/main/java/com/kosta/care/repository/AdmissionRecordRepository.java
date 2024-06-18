package com.kosta.care.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.AdmissionRecord;

@Repository
public interface AdmissionRecordRepository extends JpaRepository<AdmissionRecord, Long> {

}
