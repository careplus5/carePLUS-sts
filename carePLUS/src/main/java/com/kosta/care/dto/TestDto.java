package com.kosta.care.dto;

import java.sql.Date;
import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestDto {
    private Long testNum;
    private Long docDiagnosisNum;
    private Long metNum;
    private Long patNum;
    private Long testRequestNum;
    private Long docNum;
    private Long testFileNum;
    private String testName;
    private String testPart;
    private String testResult;
    private Date testDate;
    private String testNotice;
    private String testStatus;
    private Date testAppointmentDate;
    private String testOutInspectRecord;
    private Time testAppointmentTime;
    private String patName;
    private String patGender;
    private String patJumin;
    private String patBloodType;
}

