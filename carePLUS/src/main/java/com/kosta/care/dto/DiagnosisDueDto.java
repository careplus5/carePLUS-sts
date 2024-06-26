package com.kosta.care.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.kosta.care.entity.DocDiagnosis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiagnosisDueDto {
	private Long diagnosisDueNum;  // 진료예약 번호 
	private Long patNum;  // 환자 번호 
	private Long docNum;  // 의사 번호 
	private String diagnosisDueState;  // 증상 
	private String diagnosisDueEtc;  // 특이 사항 
	private Date diagnosisDueDate;  // 진료 예약일 
	private Time diagnosisDueTime;  // 진료 시간 
	
	//진료예약을 위해  필요추가 (김동현) (28~37)
	private String docName;

	 // 추가 (원무과) 
    private String patName;
    private String patJumin;
    private String patGender;
    private String patTel;
    private String patHeight;
    private String patWeight;
    private String patAddress;
    private String patHistory;
    private String patBloodType;
	
	private Long docDiagnosisNum;
	private Long diseaseNum;
    private String diagContent;
    private boolean testChecked;
    private String testType;
    private String testRequest;
    private boolean admChecked;
    private String admReason;
    private String admPeriod;
    private boolean surChecked;
    private String surReason;
    private Date surDate;
    private String surPeriod;
    private String toNurse;
    private List<PrescriptionDto> selectMedicine;
    
    public DocDiagnosis toDocDiagnosis() {
    	String docDiagState = "완료";
    	String docDiagKind = surChecked || admChecked ? "입원" : "외래";
    	Date docDiagDate = new Date(System.currentTimeMillis());
    	StringBuilder docDiagAddBuilder = new StringBuilder();
    	
    	if(testChecked) {
    		docDiagAddBuilder.append("검사,");
    	}
    	if(admChecked) {
    		docDiagAddBuilder.append("입원,");
    	}
    	if(surChecked) {
    		docDiagAddBuilder.append("수술,");
    	}
    	//마지막 콤마 제거
    	if(docDiagAddBuilder.length() > 0) { 
    		docDiagAddBuilder.deleteCharAt(docDiagAddBuilder.length() - 1);
    	}
    	
    	return DocDiagnosis.builder()
    					.docDiagnosisNum(docDiagnosisNum)
    					.docNum(docNum)
    					.patNum(patNum)
    					.diseaseNum(diseaseNum)
    					.docDiagnosisContent(diagContent)
    					.docDiagnosisState(docDiagState)
    					.docDiagnosisKind(docDiagKind)
    					.docDiagnosisDate(docDiagDate)
    					.docDiagnosisOrder(toNurse)
    					.docDiagnosisAdd(docDiagAddBuilder.toString())
    					.build();
    }
}
