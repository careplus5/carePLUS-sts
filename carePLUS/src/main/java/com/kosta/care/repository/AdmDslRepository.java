package com.kosta.care.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.QAdmission;
import com.kosta.care.entity.QAdmissionRecord;
import com.kosta.care.entity.QAdmissionRequest;
import com.kosta.care.entity.QDepartment;
import com.kosta.care.entity.QDisease;
import com.kosta.care.entity.QDocDiagnosis;
import com.kosta.care.entity.QDoctor;
import com.kosta.care.entity.QMedicine;
import com.kosta.care.entity.QNurse;
import com.kosta.care.entity.QPatient;
import com.kosta.care.entity.QPrescription;
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
	
	
	//원무과-처방전 발급-처방전 목록 조회
		public List<Tuple> findPatPrescListByPatNum(Long patNum) {
			QDocDiagnosis docDiagnosis = QDocDiagnosis.docDiagnosis;
			QDoctor doctor = QDoctor.doctor;
			QDepartment department = QDepartment.department;
			QPatient patient = QPatient.patient;
			QPrescription prescription = QPrescription.prescription;
			QMedicine medicine = QMedicine.medicine;
			
			return jpaQueryFactory.select(prescription, patient.patName)
						.from(prescription)
						.join(patient).on(prescription.patNum.eq(patient.patNum))
						.where(prescription.patNum.eq(patNum))
						.orderBy(prescription.prescriptionDate.desc())
						.fetch();
		}

	//원무과-기타문서발급-진료확인서 정보 조회
	public Tuple findPatDiagCheckInfoByDocDiagNum(Long docDiagNum) {
		QDocDiagnosis docDiagnosis = QDocDiagnosis.docDiagnosis;
		QPatient patient = QPatient.patient;
		QDoctor doctor = QDoctor.doctor;
		QDepartment department = QDepartment.department;
		QDisease disease = QDisease.disease;
		QAdmissionRecord admissionRecord = QAdmissionRecord.admissionRecord;
		
		return jpaQueryFactory.select(docDiagnosis, patient, doctor.docName, department.departmentName, disease.diseaseName, admissionRecord.admissionRecordContent)
					.from(docDiagnosis)
					.join(patient).on(docDiagnosis.patNum.eq(patient.patNum))
					.join(doctor).on(docDiagnosis.docNum.eq(doctor.docNum))
					.join(department).on(doctor.departmentNum.eq(department.departmentNum))
					.join(disease).on(docDiagnosis.diseaseNum.eq(disease.diseaseNum))
					.leftJoin(admissionRecord).on(docDiagnosis.docDiagnosisNum.eq(admissionRecord.docDiagnosisNum))
					.where(docDiagnosis.docDiagnosisNum.eq(docDiagNum))
					.fetchOne();
	}
	
	//원무과-기타문서발급-환자 입퇴원확인서 목록 조회
		public List<Tuple> findPatAdmCheckListByPatNum(Long patNum) {
			QAdmission admission = QAdmission.admission;
			QDoctor doctor = QDoctor.doctor;
			QDepartment department = QDepartment.department;
			QPatient patient = QPatient.patient;
			QNurse nurse = QNurse.nurse;
			
			return jpaQueryFactory.select(admission, patient.patName, doctor.docName, department.departmentName, nurse.nurName)
						.from(admission)
						.join(patient).on(admission.patNum.eq(patient.patNum))
						.join(doctor).on(admission.docNum.eq(doctor.docNum))
						.join(department).on(doctor.departmentNum.eq(department.departmentNum))
						.join(nurse).on(admission.nurNum.eq(nurse.nurNum))
						.where(admission.patNum.eq(patNum))
						.orderBy(admission.admissionDate.desc())
						.fetch();
		}
		
		//원무과-기타문서발급-입퇴원확인서 정보 조회
		public Tuple findPatAdmCheckInfoByAdmNum(Long admNum) {
			QPatient patient = QPatient.patient;
			QDoctor doctor = QDoctor.doctor;
			QAdmission admission = QAdmission.admission;
			
			return jpaQueryFactory.select(admission, patient, doctor.docName)
						.from(admission)
						.join(patient).on(admission.patNum.eq(patient.patNum))
						.join(doctor).on(admission.docNum.eq(doctor.docNum))
						.where(admission.admissionNum.eq(admNum))
						.fetchOne();
		}
		
		//원무과-기타문서발급-입퇴원확인서 (진료내역) 정보 조회
		public List<Tuple> findPatAdmDiagInfoByAdmNum(Long admNum) {
			QAdmissionRecord admissionRecord = QAdmissionRecord.admissionRecord;
			QAdmission admission = QAdmission.admission;
			QDocDiagnosis docDiagnosis = QDocDiagnosis.docDiagnosis;
			QDoctor doctor = QDoctor.doctor;
			QDepartment department = QDepartment.department;
			QDisease disease = QDisease.disease;
			
			return jpaQueryFactory.select(admissionRecord.admissionRecordNum, admissionRecord.admissionRecordContent, docDiagnosis.docDiagnosisDate, department.departmentName, disease.diseaseName)
						.from(admissionRecord)
						.join(admission).on(admissionRecord.admissionNum.eq(admission.admissionNum))
						.join(docDiagnosis).on(admissionRecord.docDiagnosisNum.eq(docDiagnosis.docDiagnosisNum))
						.join(doctor).on(docDiagnosis.docNum.eq(doctor.docNum))
						.join(department).on(doctor.departmentNum.eq(department.departmentNum))
						.join(disease).on(docDiagnosis.diseaseNum.eq(disease.diseaseNum))
						.where(admissionRecord.admissionNum.eq(admNum))
						.fetch();
		}
		
		// 입원 내역 조회 (내역이 wait인 환자 조회)
		public Tuple findAdmissionRequestByPatNum(Long patNum) {
			System.out.println("Test");
			QAdmissionRequest admissionRequest = QAdmissionRequest.admissionRequest;
			QDocDiagnosis docDiagnosis = QDocDiagnosis.docDiagnosis;
			QDoctor doctor = QDoctor.doctor;

			return jpaQueryFactory.select(doctor.departmentName, doctor.docName, admissionRequest.admissionRequestReason,
							admissionRequest.admissionRequestPeriod, doctor.departmentNum)
									.from(admissionRequest)
									.join(docDiagnosis)
									.on(admissionRequest.diagnosisNum.eq(docDiagnosis.docDiagnosisNum))
									.join(doctor)
									.on(admissionRequest.docNum.eq(doctor.docNum))
									.where(admissionRequest.patNum.eq(patNum).and(admissionRequest.admissionRequestAcpt.eq("wait")))
									.fetchOne();
		}
		
}
