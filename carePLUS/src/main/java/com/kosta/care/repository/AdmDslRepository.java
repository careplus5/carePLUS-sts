package com.kosta.care.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.QDepartment;
import com.kosta.care.entity.QDocDiagnosis;
import com.kosta.care.entity.QDoctor;
import com.kosta.care.entity.QPatient;
import com.kosta.care.entity.QTestRequest;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class AdmDslRepository {
	
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	//원무과-기타문서발급-환자 진료확인서 목록 조회
	public List<Tuple> findPatDiagCheckListByPatNum(Long patNum) {
		QDocDiagnosis docDiagnosis = QDocDiagnosis.docDiagnosis;
		QDoctor doctor = QDoctor.doctor;
		QDepartment department = QDepartment.department;
		QPatient patient = QPatient.patient;
		QTestRequest testRequest = QTestRequest.testRequest;
		
		return jpaQueryFactory.select(docDiagnosis, patient.patName, doctor.docName, department.departmentName, testRequest.testName)
					.from(docDiagnosis)
					.join(patient).on(docDiagnosis.patNum.eq(patient.patNum))
					.join(doctor).on(docDiagnosis.docNum.eq(doctor.docNum))
					.join(department).on(doctor.departmentNum.eq(department.departmentNum))
					.leftJoin(testRequest).on(docDiagnosis.testRequestNum.eq(testRequest.testRequestNum))
					.where(docDiagnosis.patNum.eq(patNum)
							.and(docDiagnosis.docDiagnosisState.eq("end")))
					.orderBy(docDiagnosis.docDiagnosisDate.desc())
					.fetch();
	}
	

}
