package com.kosta.care.repository;


import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.FavoriteMedicines;
import com.kosta.care.entity.QDepartment;
import com.kosta.care.entity.QDiagnosisDue;
import com.kosta.care.entity.QDisease;
import com.kosta.care.entity.QDocDiagnosis;
import com.kosta.care.entity.QDoctor;
import com.kosta.care.entity.QFavoriteMedicines;
import com.kosta.care.entity.QMedicine;
import com.kosta.care.entity.QPatient;
import com.kosta.care.entity.QPrescription;
import com.kosta.care.entity.QTestRequest;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;

@Repository
public class DiagnosisRepository {
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	@Autowired
	private EntityManager entityManager;
	
	//외래진료-대기환자목록
	public List<Tuple> findDiagDueListByDocNumAndDiagDueDate(Long docNum) {
		QDoctor doctor = QDoctor.doctor;
		QPatient patient = QPatient.patient;
		QDiagnosisDue diagnosisDue = QDiagnosisDue.diagnosisDue;
		QDocDiagnosis docDiagnosis = QDocDiagnosis.docDiagnosis;
		
		return jpaQueryFactory.select(diagnosisDue, patient.patName, docDiagnosis.docDiagnosisNum, docDiagnosis.docDiagnosisState)
					.from(diagnosisDue)
					.join(patient)
					.on(diagnosisDue.patNum.eq(patient.patNum))
					.join(doctor)
					.on(diagnosisDue.docNum.eq(doctor.docNum))
					.join(docDiagnosis)
					.on(diagnosisDue.patNum.eq(docDiagnosis.patNum))
					.where(docDiagnosis.docNum.eq(docNum)
							.and(diagnosisDue.docNum.eq(docNum))
							.and(docDiagnosis.docDiagnosisDate.eq(Expressions.dateTemplate(Date.class, "CURDATE()")).or(docDiagnosis.docDiagnosisDate.isNull()))
							.and(diagnosisDue.diagnosisDueDate.eq(Expressions.dateTemplate(Date.class, "CURDATE()"))))
					.orderBy(
							new CaseBuilder()
								.when(docDiagnosis.docDiagnosisState.eq("진료중")).then(1)
								.when(docDiagnosis.docDiagnosisState.eq("완료")).then(3)
								.otherwise(2).asc()
					)
					.fetch();
	}

	//외래진료-환자접수정보
	public Tuple findDiagDueInfoByDocDiagNum(Long docDiagNum) {
		QPatient patient = QPatient.patient;
		QDiagnosisDue diagnosisDue = QDiagnosisDue.diagnosisDue;
		QDocDiagnosis docDiagnosis = QDocDiagnosis.docDiagnosis;
		
		return jpaQueryFactory.select(diagnosisDue, patient, docDiagnosis)
				.from(diagnosisDue)
				.join(patient)
				.on(diagnosisDue.patNum.eq(patient.patNum))
				.join(docDiagnosis)
				.on(diagnosisDue.patNum.eq(docDiagnosis.patNum))
				.where(docDiagnosis.docDiagnosisNum.eq(docDiagNum))
				.orderBy(diagnosisDue.diagnosisDueDate.desc())
	            .limit(1)
				.fetchOne();
	}
	
	//외래진료-이전진료내역 조회
	public List<Tuple> findPrevDiagRecord(Long patNum) {
		QDoctor doctor = QDoctor.doctor;
		QPrescription prescription = QPrescription.prescription;
		QDocDiagnosis docDiagnosis = QDocDiagnosis.docDiagnosis;
		QMedicine medicine = QMedicine.medicine;
		QTestRequest testRequest = QTestRequest.testRequest1;
		QDisease disease = QDisease.disease;
		
		return jpaQueryFactory.select(docDiagnosis, prescription, doctor.docNum, doctor.docName, medicine.medicineKorName, testRequest.testPart, disease.diseaseName)
					.from(docDiagnosis)
					.join(prescription)
					.on(docDiagnosis.prescriptionNum.eq(prescription.prescriptionNum))
					.join(doctor)
					.on(docDiagnosis.docNum.eq(doctor.docNum))
					.join(medicine)
					.on(medicine.medicineNum.eq(prescription.medicineNum))
					.leftJoin(testRequest)
					.on(testRequest.testRequestNum.eq(docDiagnosis.testRequestNum))
					.join(disease)
					.on(disease.diseaseNum.eq(docDiagnosis.diseaseNum))
					.where(docDiagnosis.patNum.eq(patNum)
							.and(docDiagnosis.docDiagnosisDate.before(Expressions.dateTemplate(Date.class, "CURDATE()"))))
					.orderBy(docDiagnosis.docDiagnosisDate.desc())
					.fetch();
	}
	
	//외래진료-병명리스트 조회(부서별)
	public List<Tuple> findDiseaseListByDeptNum(Long deptNum) {
		QDoctor doctor = QDoctor.doctor;
		QDisease disease = QDisease.disease;
		QDepartment department = QDepartment.department;
		
		return jpaQueryFactory.select(disease, department.departmentName)
				.from(disease)
				.join(department)
				.on(disease.departmentNum.eq(department.departmentNum))
				.where(disease.departmentNum.eq(deptNum))
				.fetch();
	}
	
	//외래진료-환자상태 변경
	@Transactional
	public void modifyDocDiagnosisState(Long docDiagNum, String newState) {
		QDocDiagnosis docDiagnosis = QDocDiagnosis.docDiagnosis;
		
		JPAUpdateClause updateClause = new JPAUpdateClause(entityManager, docDiagnosis);
		updateClause.where(docDiagnosis.docDiagnosisNum.eq(docDiagNum))
					.set(docDiagnosis.docDiagnosisState, newState)
					.execute();
				
	}
	
	//외래진료-즐겨찾기 약품조회
	public List<Tuple> findFavMedicineListByDocNum(Long docNum) {
		QMedicine medicine = QMedicine.medicine;
		QFavoriteMedicines favoriteMedicines = QFavoriteMedicines.favoriteMedicines;
		
		return jpaQueryFactory.select(favoriteMedicines, medicine.medicineKorName)
					.from(favoriteMedicines)
					.join(medicine)
					.on(favoriteMedicines.medicineNum.eq(medicine.medicineNum))
					.where(favoriteMedicines.docNum.eq(docNum))
					.fetch();
	}
	
	//외래진료-즐겨찾기 약품등록
	public FavoriteMedicines findFavoriteMedicines(Long docNum, String medicineNum) {
		QFavoriteMedicines favoriteMedicines = QFavoriteMedicines.favoriteMedicines;
		
		return jpaQueryFactory.selectFrom(favoriteMedicines)
						.where(favoriteMedicines.docNum.eq(docNum).and(favoriteMedicines.medicineNum.eq(medicineNum)))
						.fetchOne();
	}
	
}
