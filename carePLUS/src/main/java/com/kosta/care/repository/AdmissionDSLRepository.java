package com.kosta.care.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.Admission;
import com.kosta.care.entity.QAdmission;
import com.kosta.care.entity.QAdmissionRecord;
import com.kosta.care.entity.QDoctor;
import com.kosta.care.entity.QNurse;
import com.kosta.care.entity.QPatient;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class AdmissionDSLRepository {
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	@Autowired
	private EntityManager entityManager;
	

    @Transactional
    public Admission save(Admission admission) {
        if (admission.getAdmissionNum() == null) {
            entityManager.persist(admission);
            return admission;
        } else {
            return entityManager.merge(admission);
        }
    }
	//입퇴원에 있는 환자 전체 조회
	public List<Tuple> findAdmPatientByNurNum(Long nurNum) {
		System.out.println("조회해보자");
//		QDoctor doctor = QDoctor.doctor;
		QNurse nurse = QNurse.nurse;
		QPatient patient = QPatient.patient;
		QDoctor doctor = QDoctor.doctor;
//		QDiagnosisDue diagnosisDue = QDiagnosisDue.diagnosisDue;
//		QDocDiagnosis docDiagnosis = QDocDiagnosis.docDiagnosis;
		QAdmission admission = QAdmission.admission;
			System.out.println("해당 리스트는 "+nurNum+"의 리스트입니다.");
		// 입원 번호, 환자 번호, 환자 이름(성별/나이), 입원 예정일, 입원일, 담당과, 담당의, 병실 일련 번호, 퇴원 예정일
		// patient : 환자 번호, 환자 이름, 환자 성별, 환자 나이
		//admission.admissionDate,admission.patNum,patient.patName,admission.admissionDueDate,
		//admission.admissionDate, doctor.departmentName, doctor.docName, admission.bedsNum, admission.admissionDischargeDueDate,
		//admission.admissionDischargeDate,admission.admissionStatus
		
		// 담당 간호사 nurse(nurNum) = admission(nurNum)이 일치하면 다 갖고 오기
		
		
		return jpaQueryFactory.select(admission, doctor.departmentName, doctor.docName, patient.patName)
				.from(admission)
				.join(nurse).on(admission.nurNum.eq(nurse.nurNum))
				.join(patient).on(admission.patNum.eq(patient.patNum))
				.join(doctor).on(admission.docNum.eq(doctor.docNum))
				.where(nurse.nurNum.eq(nurNum))
				.fetch();
		
	}
	
	public Admission findByAdmissionNum(Long admissionNum) {
		return entityManager.find(Admission.class, admissionNum);
	}
	
	
	// 의사 입퇴원 일지
		public List<Tuple> findAdmPatientDoctorRecordByAdmissionNum(Long admissionNum) {
			System.out.println("조회해보자");
			QDoctor doctor = QDoctor.doctor;
			QAdmissionRecord record = QAdmissionRecord.admissionRecord;
			QAdmission admission = QAdmission.admission;
			
			
			return jpaQueryFactory.select(record,doctor.docName)
					.from(record)
					.join(doctor).on(record.jobNum.eq(doctor.docNum))
					.join(admission).on(record.admissionNum.eq(admission.admissionNum))
					.where(record.admissionNum.eq(admission.admissionNum))
					.fetch();
			
		}

		
		//간호 입원 일지
		public List<Tuple> findAdmPatientNurseRecordByAdmissionNum(Long admissionNum) {
			System.out.println("조회해보자");
			QNurse nurse = QNurse.nurse;
			QAdmissionRecord record = QAdmissionRecord.admissionRecord;
			QAdmission admission = QAdmission.admission;
				
			return jpaQueryFactory.select(record,nurse.nurName)
					.from(record)
					.join(nurse).on(record.jobNum.eq(nurse.departmentNum))
					.join(admission).on(record.admissionNum.eq(admission.admissionNum))
					.where(record.admissionNum.eq(admission.admissionNum))
					.fetch();
			
		}
}
