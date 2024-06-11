package com.kosta.care.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.care.entity.TestRequest;

public interface TestRequestRepository extends JpaRepository<TestRequest, Long>{

}
