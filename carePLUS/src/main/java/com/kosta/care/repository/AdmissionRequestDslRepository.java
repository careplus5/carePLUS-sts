package com.kosta.care.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.kosta.care.dto.AdmissionRequestDto;
import com.kosta.care.entity.AdmissionRequest;
import com.kosta.care.entity.QAdmissionRequest;
import com.kosta.care.entity.QDoctor;
import com.kosta.care.entity.QPatient;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class AdmissionRequestDslRepository {
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	@Autowired
	private EntityManager entityManager;
	
	public AdmissionRequestDto findLatestByPatNum(Long patNum) {
		
		QAdmissionRequest admissionRequest = QAdmissionRequest.admissionRequest;
		QPatient patient = QPatient.patient;
		QDoctor doctor = QDoctor.doctor;
		
		Tuple tuple = jpaQueryFactory.select(admissionRequest, patient.patName,
				patient.patJumin, patient.patGender, patient.patTel, patient.patAddress,
				doctor.departmentName, doctor.docName)
					.from(admissionRequest)
					.join(patient)
					.on(admissionRequest.patNum.eq(patient.patNum))
					.join(doctor)
					.on(admissionRequest.docNum.eq(doctor.docNum))
					.where(admissionRequest.patNum.eq(patNum))
					.orderBy(admissionRequest.admissionRequestNum.desc())
					.fetchFirst();
		if (tuple != null) {
			AdmissionRequest admissionRequestEntity = tuple.get(admissionRequest);
			String patName = tuple.get(patient.patName);
			String patJumin = tuple.get(patient.patJumin);
			String patGender = tuple.get(patient.patGender);
			String patTel = tuple.get(patient.patTel);
			String patAddress = tuple.get(patient.patAddress);
			String departmentName = tuple.get(doctor.departmentName);
			String docName = tuple.get(doctor.docName);
			
			AdmissionRequestDto admissionRequestDto = AdmissionRequestDto.builder()
					.admissionRequestNum(admissionRequestEntity.getAdmissionRequestNum())
					.patNum(admissionRequestEntity.getPatNum())
					.diagnosisNum(admissionRequestEntity.getDiagnosisNum())
					.docNum(admissionRequestEntity.getDocNum())
					.jobNum(admissionRequestEntity.getJobNum())
					.admissionRequestPeriod(admissionRequestEntity.getAdmissionRequestPeriod())
					.admissionRequestReason(admissionRequestEntity.getAdmissionRequestReason())
					.build();
			return null;
		}
		return null;
	}
	
}
