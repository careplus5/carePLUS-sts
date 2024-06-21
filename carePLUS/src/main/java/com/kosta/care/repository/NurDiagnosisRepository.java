package com.kosta.care.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.Admission;
import com.kosta.care.entity.NurDiagnosis;
import com.kosta.care.entity.QDocDiagnosis;
import com.kosta.care.entity.QDoctor;
import com.kosta.care.entity.QNurDiagnosis;
import com.kosta.care.entity.QNurse;
import com.kosta.care.entity.QPatient;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class NurDiagnosisRepository{
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	@Autowired
	private EntityManager entityManager;
	
	@Transactional
    public NurDiagnosis save(NurDiagnosis diagnosis) {
        if (diagnosis.getNurDiagNum() == null) {
            entityManager.persist(diagnosis);
            return diagnosis;
        } else {
            return entityManager.merge(diagnosis);
        }
	}
	
	public NurDiagnosis findByNurDiagNum(Long nurDiagNum) {
		return entityManager.find(NurDiagnosis.class, nurDiagNum);
	}
	//입퇴원에 있는 환자 전체 조회
		public List<Tuple> findDiagPatientByNurNum(Long nurNum) {
			System.out.println("조회해보자");
			QNurse nurse = QNurse.nurse;
			QPatient patient = QPatient.patient;
			QDocDiagnosis docDiag = QDocDiagnosis.docDiagnosis;
			QDoctor doctor = QDoctor.doctor;
			QNurDiagnosis nurDiag = QNurDiagnosis.nurDiagnosis;
				System.out.println("해당 리스트는 "+nurNum+"의 리스트입니다.");
			
				  return jpaQueryFactory.select(nurDiag, patient.patName, doctor.departmentName, doctor.docName,docDiag, patient.patJumin)
				            .from(nurDiag)
				            .join(patient).on(nurDiag.patNum.eq(patient.patNum))
				            .join(docDiag).on(nurDiag.docDiagnosisNum.eq(docDiag.docDiagnosisNum))
				            .join(doctor).on(docDiag.docNum.eq(doctor.docNum))
				            .where(nurDiag.nurNum.eq(nurNum))
				            .fetch();
		}

}
