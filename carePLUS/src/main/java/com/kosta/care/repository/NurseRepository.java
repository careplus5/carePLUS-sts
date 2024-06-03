package com.kosta.care.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.Nurse;

public interface NurseRepository extends JpaRepository<Nurse, Long> {

}
