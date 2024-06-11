package com.kosta.care.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.QAdmission;
import com.kosta.care.entity.QBeds;
import com.kosta.care.entity.QDoctor;
import com.kosta.care.entity.QNurse;
import com.kosta.care.entity.QPatient;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class AdmissionRepository {
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	@Autowired
	private EntityManager entityManager;
	
	//입퇴원에 있는 환자 전체 조회
	public List<Tuple> findAdmPatientByNurNum(Long nurNum) {
		System.out.println("조회해보자");
//		QDoctor doctor = QDoctor.doctor;
		QNurse nurse = QNurse.nurse;
		QPatient patient = QPatient.patient;
		QBeds beds = QBeds.beds;
		QDoctor doctor = QDoctor.doctor;
//		QDiagnosisDue diagnosisDue = QDiagnosisDue.diagnosisDue;
//		QDocDiagnosis docDiagnosis = QDocDiagnosis.docDiagnosis;
		QAdmission admission = QAdmission.admission;
			
		// 입원 번호, 환자 번호, 환자 이름(성별/나이), 입원 예정일, 입원일, 담당과, 담당의, 병실 일련 번호, 퇴원 예정일
		// patient : 환자 번호, 환자 이름, 환자 성별, 환자 나이
		//admission.admissionDate,admission.patNum,patient.patName,admission.admissionDueDate,
		//admission.admissionDate, doctor.departmentName, doctor.docName, admission.bedsNum, admission.admissionDischargeDueDate,
		//admission.admissionDischargeDate,admission.admissionStatus
		
		// 담당 간호사 nurse(nurNum) = admission(nurNum)이 일치하면 다 갖고 오기
		
		return jpaQueryFactory.select(admission, doctor.departmentName, doctor.docName)
				.from(admission)
				.join(nurse).on(admission.jobNum.eq(nurse.nurNum))
				.join(patient).on(admission.patNum.eq(patient.patNum))
				.join(doctor).on(admission.docNum.eq(doctor.docNum))
				.where(admission.jobNum.eq(nurse.nurNum))
				.fetch();
		
	}

}
