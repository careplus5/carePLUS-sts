package com.kosta.care.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kosta.care.dto.TestRequestDto;
import com.kosta.care.entity.QDoctor;
import com.kosta.care.entity.QPatient;
import com.kosta.care.entity.QTestRequest;
import com.kosta.care.entity.TestRequest;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class TestRequestDslRepository {
	
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	@Autowired
	private EntityManager entityManager;
	
	public TestRequestDto findLatestByPatNum (Long patNum)  {
		
		QTestRequest testRequest = QTestRequest.testRequest;
		QPatient patient = QPatient.patient;
		QDoctor doctor = QDoctor.doctor;
		
		Tuple tuple = jpaQueryFactory.select(testRequest,patient.patName, patient.patJumin, patient.patGender, doctor.docName )
                	.from(testRequest)
                	.join(patient)
                	.on(testRequest.patNum.eq(patient.patNum))
                	.join(doctor)
                	.on(testRequest.docNum.eq(doctor.docNum))
					.where(testRequest.patNum.eq(patNum))
                	.orderBy(testRequest.testRequestNum.desc())
                	.fetchFirst();
		
		if (tuple != null) {
	        TestRequest testRequestEntity = tuple.get(testRequest);
	        String patName = tuple.get(patient.patName);
	        String patJumin = tuple.get(patient.patJumin);
	        String patGender = tuple.get(patient.patGender);
	        String docName = tuple.get(doctor.docName);

	        TestRequestDto testRequestDto = TestRequestDto.builder()
	                .testRequestNum(testRequestEntity.getTestRequestNum())
	                .patNum(testRequestEntity.getPatNum())
	                .patName(patName)
	                .patJumin(patJumin)
	                .patGender(patGender)
	                .docNum(testRequestEntity.getDocNum())
	                .docName(docName)
	                .testName(testRequestEntity.getTestName())
	                .testRequestAcpt(testRequestEntity.getTestRequestAcpt())
	                .testPart(testRequestEntity.getTestPart())
	                .build();

	        return testRequestDto;
	    } else {
	        return null;
	    }
	}
	
	public List<TestRequestDto> findTestRequestList(Long patNum) {
		QTestRequest testRequest = QTestRequest.testRequest;
		QPatient patient = QPatient.patient;
		QDoctor doctor = QDoctor.doctor;
		List<TestRequestDto> testRequestList = jpaQueryFactory.select(
				Projections.bean(TestRequestDto.class, testRequest.testRequestNum,
										testRequest.patNum,
										patient.patName, 
										patient.patJumin, 
										patient.patGender, 
										testRequest.docNum,
										doctor.docName,
										testRequest.testName,
										testRequest.testRequestAcpt,
										testRequest.testPart,
										doctor.departmentName,
										testRequest.docDiagnosisNum))
            	.from(testRequest)
            	.join(patient)
            	.on(testRequest.patNum.eq(patient.patNum))
            	.join(doctor)
            	.on(testRequest.docNum.eq(doctor.docNum))
				.where(testRequest.patNum.eq(patNum).and(testRequest.testRequestAcpt.eq("accept")))
            	.orderBy(testRequest.testRequestNum.desc())
            	.fetch();
		return testRequestList;
	}

}
