package com.kosta.care.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

}
