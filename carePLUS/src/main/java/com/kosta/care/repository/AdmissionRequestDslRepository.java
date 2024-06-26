package com.kosta.care.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kosta.care.dto.AdmissionRequestDto;
import com.kosta.care.entity.QAdmissionRequest;
import com.kosta.care.entity.QDoctor;
import com.kosta.care.entity.QPatient;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class AdmissionRequestDslRepository {
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	public AdmissionRequestDto findAdmissionRequestByPatNum(Long patNum) {	
		QAdmissionRequest admissionRequest = QAdmissionRequest.admissionRequest;
		QPatient patient = QPatient.patient;
		QDoctor doctor = QDoctor.doctor;
		
		return jpaQueryFactory.select(
				Projections.bean(AdmissionRequestDto.class, 
						admissionRequest.admissionRequestNum,
						admissionRequest.patNum,
						admissionRequest.diagnosisNum,
						admissionRequest.docNum,
						doctor.docName,
						admissionRequest.jobNum,
						admissionRequest.departmentNum,
						doctor.departmentName,
						admissionRequest.admissionRequestPeriod,
						admissionRequest.admissionRequestReason))
					.from(admissionRequest)
					.join(patient)
					.on(admissionRequest.patNum.eq(patient.patNum))
					.join(doctor)
					.on(admissionRequest.docNum.eq(doctor.docNum))
					.where(admissionRequest.patNum.eq(patNum).and(
							admissionRequest.admissionRequestAcpt.eq("wait")))
					.orderBy(admissionRequest.admissionRequestNum.desc())
					.fetchFirst();
	}	
}
