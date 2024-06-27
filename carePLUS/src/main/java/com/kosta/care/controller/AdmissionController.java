package com.kosta.care.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.care.dto.AdmDiagnosisDto;
import com.kosta.care.entity.AdmissionRecord;
import com.kosta.care.entity.Patient;
import com.kosta.care.entity.Prescription;
import com.kosta.care.repository.NurseRepository;
import com.kosta.care.service.AdmissionService;
import com.kosta.care.service.PatientService;
import com.kosta.care.service.PrescriptionService;

@RestController
public class AdmissionController {
	
//	@Autowired
//	private DiagnosisDueService diagnosisDueService;
//	
	@Autowired
	private AdmissionService admService;
	@Autowired 
	private NurseRepository nurRepository;
	@Autowired
	private PrescriptionService prescService;
	@Autowired
	private PatientService patService;
	
	@GetMapping("/wardPatientList")
	public ResponseEntity<List<Map<String, Object>>> admPatientList(@RequestParam("nurNum") Long nurNum) {
		System.out.println("리스트 가져오기 준비");
		try {
			System.out.println("리스트 가져오기 시작");
			System.out.println(nurNum);
			List<Map<String, Object>> admission = admService.admPatientList(nurNum);
			System.out.println(admission.toString());
			return new ResponseEntity<List<Map<String, Object>>>(admission, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/wardDailyPresc")
	public ResponseEntity<List<Map<String, Object>>> wardDailyPrescList(@RequestParam("nurNum") Long nurNum) {
		System.out.println("리스트 가져오기 준비");
		try {
			System.out.println("리스트 가져오기 시작");
			System.out.println(nurNum);
			List<Map<String, Object>> admission = admService.admPatientList(nurNum);
			System.out.println(admission.toString());
			return new ResponseEntity<List<Map<String, Object>>>(admission, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.BAD_REQUEST);
		}
	}

    // 환자의 모든 처방전 리스트를 JSON 형태로 반환하는 API 엔드포인트
    @GetMapping("/prescList")
    public ResponseEntity<List<Map<String, Object>>> prescList(@RequestParam("patNum") Long patNum) {
        try {
            List<Map<String, Object>> dailyPrescriptionList = admService.dailyPrescriptionList(patNum);
            return new ResponseEntity<>(dailyPrescriptionList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

	@GetMapping("/nurPatientInfo")
	public ResponseEntity< Map<String, Object>> getPatientRecords(@RequestParam("admissionNum") Long admissionNum, @RequestParam("patNum") Long patNum) {
	    System.out.println("doctor and nurse records 리스트 가져오기 준비");
	    try {
	        System.out.println("리스트 가져오기 시작");
	        List<Map<String, Object>> doctorRecord = admService.admPatientDoctorRecordList(admissionNum);
	        System.out.println("doctorRecord 결과:" + doctorRecord.toString());
	        
	        List<Map<String, Object>> nurseRecord = admService.admPatientNurseRecordList(admissionNum);
	        System.out.println("nurseRecord 결과:" + nurseRecord.toString());
	        
	        Patient patient = patService.getPatientById(patNum).get();
	        System.out.println("patient 결과:" + patient.toString());

	        
	        
	        // 두 리스트를 하나의 맵에 담기
	        Map<String, Object> combinedRecords = new HashMap<>();
	        combinedRecords.put("doctorRecord", doctorRecord);
	        combinedRecords.put("nurseRecord", nurseRecord);
	        combinedRecords.put("patient", patient);

	        return new ResponseEntity<>(combinedRecords, HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
	}

@GetMapping("/admDiagPatientList")
	public ResponseEntity<List<Map<String, Object>>> admDiagPatientList(@RequestParam("docNum") Long docNum) {
		try {
			List<Map<String, Object>> admDiagList = admService.admDiagPatientList(docNum);
			return new ResponseEntity<List<Map<String,Object>>>(admDiagList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String,Object>>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/admDiagPatientInfo")
	public ResponseEntity<Map<String, Object>> admDiagPatientInfo(@RequestParam("admNum") Long admNum) {
		try {
			Map<String, Object> admPatInfo = admService.admDiagPatInfo(admNum);
			return new ResponseEntity<Map<String,Object>>(admPatInfo, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}
	@PostMapping("/nurseDailyRecord")
	public ResponseEntity<Boolean> nurseAddDailyRecord(@RequestBody Map<String, Object> param){
		try {
			 Long jobNum = Long.parseLong((String) param.get("jobNum"));
            String admissionNum =
            String.valueOf(param.get("admissionNum"));
            String admissionRecordContent = (String) param.get("admissionRecordContent");
            System.out.println(admissionRecordContent);
           System.out.println("jobNum은 "+jobNum);
           System.out.println("내용은 "+admissionRecordContent);
            AdmissionRecord savedRecord = new AdmissionRecord();
            savedRecord.setJobNum(jobNum);
            savedRecord.setAdmissionRecordContent(admissionRecordContent);
            savedRecord.setAdmissionNum(Long.parseLong(admissionNum));
            
            AdmissionRecord record = admService.saveNurseAdmissionRecord(savedRecord);
            return ResponseEntity.status(HttpStatus.OK).body(true);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
		}
	}
	@PostMapping("/admissionDischarge")
	public ResponseEntity<Boolean> updateAdmissionDischarge(@RequestBody Map<String, Object> param) {
		try {
			Long admissionNum = Long.parseLong(param.get("admissionNum").toString());
			System.out.println(admissionNum);
            String admissionDischargeOpinion = param.get("admissionDischargeOpinion").toString();
            String admissionDischargeDateStr = param.get("admissionDischargeDate").toString();
            
         // Convert string to date
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date admissionDischargeDate = formatter.parse(admissionDischargeDateStr);
            java.sql.Date dd = new java.sql.Date(admissionDischargeDate.getTime());
            
			Boolean updateDischarge = admService.updateAdmissionDischarge(admissionNum, admissionDischargeOpinion,dd);
			System.out.println(updateDischarge);
			return new ResponseEntity<Boolean>(updateDischarge, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	   @PostMapping("/updatePrescriptionStatus")
	   public ResponseEntity<Boolean> updatePrescriptionStatus(@RequestBody Map<String, Object> params) {
	      
	      try {
	         String patNum = params.get("patNum").toString();
	            String prescriptionNum = params.get("prescriptionNum").toString();
	            String buttonNum = params.get("buttonNum").toString();
	              String nurNum = (String) params.get("nurNum");
	              String diaryStatus = params.get("diaryStatus").toString();
	              String diaryTimeStr = params.get("diaryTime").toString();
	              
	              // Convert string to date
	               SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
	               java.util.Date diaryTime = formatter.parse(diaryTimeStr);
	               java.sql.Time dd = new java.sql.Time(diaryTime.getTime());
	               
	         System.out.println("controller start: updatePrescriptionStatus: "+nurNum);
	         System.out.println("controller start: updatePrescriptionStatus: "+diaryStatus);

	         Boolean updatePrescStatus = admService.updatePrescDiary(patNum,Long.parseLong(prescriptionNum), buttonNum, nurNum, diaryStatus, dd);
	         System.out.println(updatePrescStatus);
	         return new ResponseEntity<Boolean>(updatePrescStatus, HttpStatus.OK);
	      } catch (Exception e) {
	         e.printStackTrace();
	         return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
	      }
	   }
			
			
	@GetMapping("/firstDiagRecord")
	public ResponseEntity<Map<String, Object>> firstDiagRecord(@RequestParam("patNum") Long patNum) {
		try {
			Map<String, Object> firstDiagRecord = admService.firstDiagRecordInfo(patNum);
			return new ResponseEntity<Map<String,Object>>(firstDiagRecord, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/admDiagNurRecord")
	public ResponseEntity<List<Map<String, Object>>> admDiagNurRecord(@RequestParam("admNum") Long admNum) {
		try {
			List<Map<String, Object>> admNurRecordList = admService.admNurRecordList(admNum);
			return new ResponseEntity<List<Map<String, Object>>>(admNurRecordList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/admDiagRecord")
	public ResponseEntity<List<Map<String, Object>>> admDiagRecord(@RequestParam("admNum") Long admNum) {
		try {
			List<Map<String, Object>> admDiagRecordList = admService.admDiagRecordList(admNum);
			return new ResponseEntity<List<Map<String, Object>>>(admDiagRecordList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/admDiagnosisSubmit")
	public ResponseEntity<Boolean> admDiagnosisSubmit(@RequestBody AdmDiagnosisDto admDiagDto) {
		try {
			Boolean isSuccess = admService.submitAdmDiag(admDiagDto);
			List<Prescription> list = admDiagDto.toListPrescription();
			Long docNum = admDiagDto.getDocNum();
			Long patNum = admDiagDto.getPatNum();
			for(Prescription presc : list) {
				presc.setDocNum(docNum);
				presc.setPatNum(patNum);
				Boolean isSuccess2 = prescService.savePrescriptionDiary(presc);	
			}
			return new ResponseEntity<Boolean>(isSuccess, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
		}
	}
}
