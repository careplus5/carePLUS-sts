package com.kosta.care.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.Surgery;

public interface SurgeryRepository extends JpaRepository<Surgery, Long> {

}
