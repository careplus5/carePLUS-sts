package com.kosta.care.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kosta.care.entity.TestRequest;

public interface TestRequestRepository extends JpaRepository<TestRequest, Long>{
	
	 @Query("SELECT tr FROM TestRequest tr JOIN FETCH tr.patient WHERE tr.testRequestAcpt IN ('검사요청', '보류')")
	    List<TestRequest> findPendingTestRequests();

}
