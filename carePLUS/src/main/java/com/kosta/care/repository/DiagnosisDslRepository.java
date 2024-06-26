package com.kosta.care.repository;


import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kosta.care.entity.FavoriteMedicines;
import com.kosta.care.entity.QAdmission;
import com.kosta.care.entity.QDepartment;
import com.kosta.care.entity.QDiagnosisDue;
import com.kosta.care.entity.QDisease;
import com.kosta.care.entity.QDocDiagnosis;
import com.kosta.care.entity.QDoctor;
import com.kosta.care.entity.QFavoriteMedicines;
import com.kosta.care.entity.QMedicine;
import com.kosta.care.entity.QPatient;
import com.kosta.care.entity.QPrescription;
import com.kosta.care.entity.QSurgery;
import com.kosta.care.entity.QTestRequest;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;

@Repository
public class DiagnosisDslRepository {
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
		
		return jpaQueryFactory.select(diagnosisDue, patient.patName, docDiagnosis.docDiagnosisNum, docDiagnosis.docDiagnosisState, doctor.departmentNum)
					.from(diagnosisDue)
					.join(patient).on(diagnosisDue.patNum.eq(patient.patNum))
					.join(doctor).on(diagnosisDue.docNum.eq(doctor.docNum))
					.join(docDiagnosis).on(diagnosisDue.patNum.eq(docDiagnosis.patNum))
					.where(docDiagnosis.docNum.eq(docNum)
							.and(diagnosisDue.docNum.eq(docNum))
							.and(docDiagnosis.docDiagnosisDate.eq(Expressions.dateTemplate(Date.class, "CURDATE()")).or(docDiagnosis.docDiagnosisDate.isNull()))
							.and(diagnosisDue.diagnosisDueDate.eq(Expressions.dateTemplate(Date.class, "CURDATE()")))
							.and(docDiagnosis.docDiagnosisKind.eq("diag").or(docDiagnosis.docDiagnosisKind.isNull())))
					.orderBy(
							new CaseBuilder()
								.when(docDiagnosis.docDiagnosisState.eq("ing")).then(1)
								.when(docDiagnosis.docDiagnosisState.eq("end")).then(3)
								.otherwise(2).asc(),
								diagnosisDue.diagnosisDueTime.asc()
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
				.join(patient).on(diagnosisDue.patNum.eq(patient.patNum))
				.join(docDiagnosis).on(diagnosisDue.diagnosisDueNum.eq(docDiagnosis.diagnosisDueNum))
				.where(docDiagnosis.docDiagnosisNum.eq(docDiagNum))
				.fetchOne();
	}
	
	//외래진료-병명리스트 조회(부서별)
	public List<Tuple> findDiseaseListByDeptNum(Long deptNum) {
		QDoctor doctor = QDoctor.doctor;
		QDisease disease = QDisease.disease;
		QDepartment department = QDepartment.department;
		
		return jpaQueryFactory.select(disease, department.departmentName)
				.from(disease)
				.join(department).on(disease.departmentNum.eq(department.departmentNum))
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
					.join(medicine).on(favoriteMedicines.medicineNum.eq(medicine.medicineNum))
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
	
	
	//담당환자
	//담당환자- 담당환자 조회
	public List<Tuple> findDocDiagPatListByDocNum(Long docNum, String searchType, String searchKeyword) {
		QDocDiagnosis docDiagnosis = QDocDiagnosis.docDiagnosis;
		QPatient patient = QPatient.patient;
		QDisease disease = QDisease.disease;
		QAdmission admission = QAdmission.admission;
		QSurgery surgery = QSurgery.surgery;
		
		JPAQuery<Tuple> query = jpaQueryFactory.select(docDiagnosis, patient, disease.diseaseName, admission.admissionStatus, surgery.surgeryState, docDiagnosis.docDiagnosisDate.max())
					.from(docDiagnosis)
					.join(patient).on(docDiagnosis.patNum.eq(patient.patNum))
					.join(disease).on(docDiagnosis.diseaseNum.eq(disease.diseaseNum))
					.leftJoin(admission).on(docDiagnosis.patNum.eq(admission.patNum))
					.leftJoin(surgery).on(docDiagnosis.patNum.eq(surgery.patNum))
					.where(docDiagnosis.docNum.eq(docNum))
					.groupBy(patient.patNum);
		
		if("patNum".equals(searchType) && searchKeyword != null && !searchKeyword.isEmpty()) {
			query.where(patient.patNum.like("%" + searchKeyword + "%"));
		}
		if("patName".equals(searchType) && searchKeyword != null && !searchKeyword.isEmpty()) {
			query.where(patient.patName.like("%" + searchKeyword + "%"));
		}
		if("patJumin".equals(searchType) && searchKeyword != null && !searchKeyword.isEmpty()) {
			query.where(patient.patJumin.like("%" + searchKeyword + "%"));
		}
		if("diseaseName".equals(searchType) && searchKeyword != null && !searchKeyword.isEmpty()) {
			query.where(disease.diseaseName.like("%" + searchKeyword + "%"));
		}
		if("admState".equals(searchType) && searchKeyword != null && !searchKeyword.isEmpty()) {
			query.where(admission.admissionStatus.like("%" + searchKeyword + "%"));
		}
		if("surState".equals(searchType) && searchKeyword != null && !searchKeyword.isEmpty()) {
			query.where(surgery.surgeryState.like("%" + searchKeyword + "%"));
		}
		
		return query.fetch();
	}
	
	//담당환자,외래진료-환자 이전진료내역 조회, 검색
		public List<Tuple> findPatDiagListByPatNum(Long patNum, String searchType, String searchKeyword) {
			QDoctor doctor = QDoctor.doctor;
			QPrescription prescription = QPrescription.prescription;
			QDocDiagnosis docDiagnosis = QDocDiagnosis.docDiagnosis;
			QMedicine medicine = QMedicine.medicine;
			QTestRequest testRequest = QTestRequest.testRequest;
			QDisease disease = QDisease.disease;
			JPAQuery<Tuple> query =  jpaQueryFactory.select(docDiagnosis, prescription, doctor.docNum, doctor.docName, medicine.medicineKorName, testRequest.testPart, disease.diseaseNum, disease.diseaseName)
						.from(docDiagnosis)
						.leftJoin(prescription).on(docDiagnosis.prescriptionNum.eq(prescription.prescriptionNum))
						.join(doctor).on(docDiagnosis.docNum.eq(doctor.docNum))
						.leftJoin(medicine).on(medicine.medicineNum.eq(prescription.medicineNum))
						.leftJoin(testRequest).on(testRequest.testRequestNum.eq(docDiagnosis.testRequestNum))
						.join(disease).on(disease.diseaseNum.eq(docDiagnosis.diseaseNum))
						.where(docDiagnosis.patNum.eq(patNum)
								.and(docDiagnosis.docDiagnosisDate.loe(Expressions.dateTemplate(Date.class, "CURDATE()"))))
						.orderBy(docDiagnosis.docDiagnosisDate.desc());

			if("docNum".equals(searchType) && searchKeyword != null && !searchKeyword.isEmpty()) {
				query.where(docDiagnosis.docNum.like("%" + searchKeyword + "%"));
			}
			if("docName".equals(searchType) && searchKeyword != null && !searchKeyword.isEmpty()) {
				query.where(doctor.docName.like("%" + searchKeyword + "%"));
			}
			if("disName".equals(searchType) && searchKeyword != null && !searchKeyword.isEmpty()) {
				query.where(disease.diseaseName.like("%" + searchKeyword + "%"));
			}
			if("preMed".equals(searchType) && searchKeyword != null && !searchKeyword.isEmpty()) {
				query.where(medicine.medicineKorName.like("%" + searchKeyword + "%"));
			}
			if("testPart".equals(searchType) && searchKeyword != null && !searchKeyword.isEmpty()) {
				query.where(testRequest.testPart.like("%" + searchKeyword + "%"));
			}
			if("diagKind".equals(searchType) && searchKeyword != null && !searchKeyword.isEmpty()) {
				query.where(docDiagnosis.docDiagnosisKind.like("%" + searchKeyword + "%"));
			}
			
			return query.fetch();
		}
	
}
