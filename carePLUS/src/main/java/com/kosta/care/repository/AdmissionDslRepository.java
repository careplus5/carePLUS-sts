package com.kosta.care.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kosta.care.dto.AdmissionRequestDto;
import com.kosta.care.entity.Admission;
import com.kosta.care.entity.QAdmission;
import com.kosta.care.entity.QAdmissionRecord;
import com.kosta.care.entity.QAdmissionRequest;
import com.kosta.care.entity.QBeds;
import com.kosta.care.entity.QDepartment;
import com.kosta.care.entity.QDisease;
import com.kosta.care.entity.QDocDiagnosis;
import com.kosta.care.entity.QDoctor;
import com.kosta.care.entity.QNurse;
import com.kosta.care.entity.QPatient;
import com.kosta.care.entity.QPrescription;
import com.kosta.care.entity.QPrescriptionDiary;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class AdmissionDslRepository {
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
		QNurse nurse = QNurse.nurse;
		QPatient patient = QPatient.patient;
		QDoctor doctor = QDoctor.doctor;
		QAdmission admission = QAdmission.admission;
			System.out.println("해당 리스트는 "+nurNum+"의 리스트입니다.");
		
	Long nurDept = Long.parseLong(nurNum.toString().substring(2, 4));
//		System.out.println(nurDept);
			
			 String nurNumSubstring = nurNum.toString().substring(2, 4);
			    System.out.println(nurNumSubstring);
			    
			    String docNum =admission.docNum.toString();
			    System.out.println(docNum);

			    // Substring 표현식
			    StringExpression docNumSubstring = Expressions.stringTemplate("substring({0}, {1}, {2})", doctor.docNum, 3, 2); // 1-based index for SQL substring
			    
		return jpaQueryFactory.select(admission, doctor.departmentName, doctor.docName, patient.patName, patient.patJumin)
				.from(admission)
                .join(patient).on(admission.patNum.eq(patient.patNum))
                .join(doctor).on(admission.docNum.eq(doctor.docNum))
                .where(admission.jobNum.eq(nurDept))
                .orderBy(
                    new CaseBuilder()
                        .when(admission.admissionStatus.eq("ing")).then(0)
                        .otherwise(1).asc(),
                        admission.admissionDate.asc()
                 )
                .fetch();
		
	}
	
	public Admission findByAdmissionNum(Long admissionNum) {
		return entityManager.find(Admission.class, admissionNum);
	}
	
	public List<Tuple> findByBedsNum(Long bedsNum){
		QAdmission admission = QAdmission.admission;
		QBeds beds = QBeds.beds;
		
		return jpaQueryFactory.select(admission,beds)
				.from(beds)
				.join(admission).on(beds.bedsNum.eq(admission.bedsNum))
				.where(beds.bedsNum.eq(bedsNum))
				.fetch();
	}
	
	
	
	// 의사 입퇴원 일지
		public List<Tuple> findAdmPatientDoctorRecordByAdmissionNum(Long admissionNum) {
			System.out.println("조회해보자");
			QDoctor doctor = QDoctor.doctor;
			QAdmissionRecord record = QAdmissionRecord.admissionRecord;
			QAdmission admission = QAdmission.admission;
			QDocDiagnosis docDiagnosis = QDocDiagnosis.docDiagnosis;
			
			
			return jpaQueryFactory.select(record,docDiagnosis.docNum,doctor.docName)
					.from(record)
					.join(docDiagnosis).on(record.docDiagnosisNum.eq(docDiagnosis.docDiagnosisNum))
					.join(admission).on(record.admissionNum.eq(admission.admissionNum))
					.join(doctor).on(admission.docNum.eq(doctor.docNum))
					.where(record.admissionNum.eq(admissionNum))
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
							.join(nurse).on(record.jobNum.eq(nurse.nurNum))
							.join(admission).on(record.admissionNum.eq(admission.admissionNum))
							.where(record.admissionNum.eq(admissionNum))
							.fetch();
					
				}
	
	//입원진료-입원환자목록
	public List<Tuple> findAdmPatListByDocNum(Long docNum) {
		QAdmission admission = QAdmission.admission;
		QPatient patient = QPatient.patient;
		QDoctor doctor = QDoctor.doctor;
		
		 return jpaQueryFactory.select(admission, patient, doctor, admission.admissionDate.min())
			 	.from(admission)
			 	.join(patient).on(admission.patNum.eq(patient.patNum))
			 	.join(doctor).on(admission.docNum.eq(doctor.docNum))
			 	.where(admission.docNum.eq(docNum)
			 			.and(admission.admissionStatus.eq("ing")))
			 	.groupBy(patient.patNum)
			 	.orderBy(
			 			new CaseBuilder()
			 				.when(admission.admissionDiagState.eq("ing")).then(1)
			 				.otherwise(2).asc()
			 				
			 	)
			 	.fetch();
	}
		
	//입원진료-입원환자정보 조회
	public Tuple findAdmDiagPatInfoByAdmNum(Long admNum) {
		QPatient patient = QPatient.patient;
		QAdmission admission = QAdmission.admission;
		QAdmissionRequest admissionRequest = QAdmissionRequest.admissionRequest;
		QDocDiagnosis docDiagnosis = QDocDiagnosis.docDiagnosis;
		return jpaQueryFactory.select(admission, patient, admissionRequest,patient.patJumin)
					.from(admission)
					.join(patient).on(admission.patNum.eq(patient.patNum))
					.join(admissionRequest).on(admission.admissionRequestNum.eq(admissionRequest.admissionRequestNum))
					.where(admission.admissionNum.eq(admNum))
					.fetchOne();
	}
	

	// 처방전, 처방 일지
		public List<Tuple> findDailyPrescriptionListByPatNum(Long patNum) {
			System.out.println("조회해보자");
			
		     QPrescription prescription = QPrescription.prescription;
		        QPrescriptionDiary diary = QPrescriptionDiary.prescriptionDiary;

		       List<Tuple> tuple = jpaQueryFactory.select(prescription, diary)
		                .from(prescription)
		                .join(diary).on(prescription.prescriptionNum.eq(diary.prescriptionNum))
		                .where(prescription.patNum.eq(patNum))
		                .fetch();
		       System.out.println("search tuple: "+tuple.toString());
		        return jpaQueryFactory.select(prescription, diary)
		                .from(prescription)
		                .join(diary).on(prescription.prescriptionNum.eq(diary.prescriptionNum))
		                .where(prescription.patNum.eq(patNum))
		                .fetch();
		    }
		
	//입원진료-초기진단기록 조회
	public Tuple findFirstDiagRecordByPatNum(Long patNum) {
		QDocDiagnosis docDiagnosis = QDocDiagnosis.docDiagnosis;
		QAdmission admission = QAdmission.admission;
		QDisease disease = QDisease.disease;
		
		return jpaQueryFactory.select(disease.diseaseName, docDiagnosis.docDiagnosisContent, disease.diseaseNum)
					.from(docDiagnosis)
					.join(admission).on(docDiagnosis.patNum.eq(admission.patNum))
					.join(disease).on(docDiagnosis.diseaseNum.eq(disease.diseaseNum))
					.where(docDiagnosis.patNum.eq(patNum))
					.orderBy(docDiagnosis.docDiagnosisDate.asc())
		            .limit(1)
		            .fetchOne();
	}
	
	//입원진료-간호사 입원일지 조회
	public List<Tuple> findAdmNurRecordByAdmNum(Long admNum) {
		QAdmissionRecord admissionRecord = QAdmissionRecord.admissionRecord;
		QAdmission admission = QAdmission.admission;
		QNurse nurse = QNurse.nurse;
		
		return jpaQueryFactory.select(admissionRecord, nurse)
					.from(admissionRecord)
					.join(admission).on(admissionRecord.admissionNum.eq(admission.admissionNum))
					.join(nurse).on(admissionRecord.jobNum.eq(nurse.nurNum))
					.where(admissionRecord.admissionNum.eq(admNum)
							.and(admissionRecord.jobNum.isNotNull()))
					.orderBy(admissionRecord.admissionRecordDate.desc())
					.fetch();
	}

	//입원진료-의사 입원진단기록 조회
	public List<Tuple> findAdmDiagRecordByAdmNum(Long admNum) {
		QAdmissionRecord admissionRecord = QAdmissionRecord.admissionRecord;
		QAdmission admission = QAdmission.admission;
		QDocDiagnosis docDiagnosis = QDocDiagnosis.docDiagnosis;
		QDoctor doctor = QDoctor.doctor;
		
		return jpaQueryFactory.select(admissionRecord, doctor)
				.from(admissionRecord)
				.join(admission).on(admissionRecord.admissionNum.eq(admission.admissionNum))
				.join(docDiagnosis).on(admissionRecord.docDiagnosisNum.eq(docDiagnosis.docDiagnosisNum))
				.join(doctor).on(docDiagnosis.docNum.eq(doctor.docNum))
				.where(admissionRecord.admissionNum.eq(admNum)
						.and(admissionRecord.docDiagnosisNum.isNotNull()))
				.orderBy(admissionRecord.admissionRecordDate.desc())
				.fetch();
	}
	
	// 환자 퇴원시 정보 보기 (기타 정보를 가지고 오기)
	public Tuple findByPatNum(Long patNum) {
		QPatient patient = QPatient.patient;
		QAdmission admission = QAdmission.admission;
		QDoctor doctor = QDoctor.doctor;
		QDepartment department = QDepartment.department;
		
		return jpaQueryFactory.select(admission, patient, doctor, department)
					.from(admission)
					.join(patient)
					.on(admission.patNum.eq(patient.patNum))
					.join(doctor)
					.on(admission.docNum.eq(doctor.docNum))
					.join(department)
					.on(doctor.departmentNum.eq(department.departmentNum))
					.where(admission.patNum.eq(patNum))
					.orderBy(admission.admissionNum.desc())
					.fetchOne();
	}

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
						doctor.departmentNum,
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
