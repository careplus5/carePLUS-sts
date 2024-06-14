package com.kosta.care.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.Administor;

@Repository
public interface AdministorRepository extends JpaRepository<Administor, Long> {

	Administor findByManNum(Long manNum);
}
