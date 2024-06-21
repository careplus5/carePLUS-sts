package com.kosta.care.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.kosta.care.entity.DocDiagnosis;
import com.kosta.care.entity.Medicine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocDiagnosisDto {
	private Long docDiagnosisNum;
	private Long docNum;
	private Long patNum;
	private Long deptNum;
	private Long diseaseNum;
    private String diagContent;
    private boolean testChecked;
    private String testType;
    private String testRequest;
    private boolean admChecked;
    private String admReason;
    private Long admPeriod;
    private boolean surChecked;
    private String surReason;
    private Date surDate;
    private String surPeriod;
    private String toNurse;
    private List<PrescriptionDto> selectMedicine;
    
    public DocDiagnosis toDocDiagnosis() {
    	String docDiagState = "end";
    	String docDiagKind = "diag";
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
