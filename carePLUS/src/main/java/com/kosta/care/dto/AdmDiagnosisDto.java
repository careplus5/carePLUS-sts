package com.kosta.care.dto;

import java.sql.Date;
import java.util.List;

import com.kosta.care.entity.AdmissionRecord;
import com.kosta.care.entity.Prescription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdmDiagnosisDto {
	private Long admissionRecordNum;
	private Long docDiagnosisNum;
	private Long jobNum;
	private Date admRecordDate;
	private String admRecordContent;
	private Long admissionNum;
	
	private Long docNum;
	private Long patNum;
	private Long deptNum;
	private boolean testChecked;
    private String testType;
    private String testRequest;
    private boolean surChecked;
    private String surReason;
    private Date surDate;
    private String surPeriod;
    private List<PrescriptionDto> selectMedicine;

   
    public AdmissionRecord toAdmDiagRecord() {
    	StringBuilder docDiagAddBuilder = new StringBuilder();
    	
    	if(testChecked) {
    		docDiagAddBuilder.append("검사,");
    	}
    	if(surChecked) {
    		docDiagAddBuilder.append("수술,");
    	}
    	//마지막 콤마 제거
    	if(docDiagAddBuilder.length() > 0) { 
    		docDiagAddBuilder.deleteCharAt(docDiagAddBuilder.length() - 1);
    	}
    	
    	return AdmissionRecord.builder()
    				.admissionRecordNum(admissionRecordNum)
    				.docDiagnosisNum(docDiagnosisNum)
    				.admissionRecordDate(admRecordDate)
    				.admissionRecordContent(admRecordContent)
    				.admissionNum(admissionNum)
    				.build();
    }
    
    public List<Prescription> toListPrescription() {
    	PrescriptionListDto listDto = new PrescriptionListDto();

        for (PrescriptionDto prescriptionDto : selectMedicine) {
        	listDto.addPrescriptionDto(prescriptionDto);
        }

        return listDto.build();
    }
    
    
}
