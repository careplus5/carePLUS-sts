package com.kosta.care.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.care.dto.PatientDto;
import com.kosta.care.entity.Admission;
import com.kosta.care.entity.DocDiagnosis;
import com.kosta.care.entity.Patient;
import com.kosta.care.service.AdmService;
import com.kosta.care.service.PatientService;

@RestController
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private AdmService admService;
	

	
	// 환자 조회
		@PostMapping("/patientSearch")
		public ResponseEntity<List<PatientDto>> allPatient(@RequestBody Map<String,String> param) {
			String type = param.get("type");
			String keyword = param.get("keyword");
			try {
				List<PatientDto> patientDtoList = patientService.getAllPatientSearch(type, keyword);
				
				
				if(!patientDtoList.isEmpty()) {
					return new ResponseEntity<List<PatientDto>>(patientDtoList, HttpStatus.OK);
				} else {
					return new ResponseEntity<List<PatientDto>>(HttpStatus.BAD_REQUEST);
				}
			} catch (Exception e) {
				return new ResponseEntity<List<PatientDto>>(HttpStatus.BAD_REQUEST);
			}
		}
	


	@PostMapping("/patNumCheck")
	public ResponseEntity<Patient> patNumCheck(@RequestBody Map<String,Long> param)  {
		System.out.println(param);
		try {
            Optional<Patient> oPatient = patientService.getPatientById(param.get("patNum"));

            if (oPatient.isPresent()) {
                return new ResponseEntity<Patient>(oPatient.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<Patient>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Patient>(HttpStatus.BAD_REQUEST);
        }
	}
	
	// 환자 진료확인서  조회 
	@PostMapping("/confirmDiagnosis")
	public ResponseEntity<List<DocDiagnosis>> confirmDiagnosis (@RequestBody Map<String,Long> param) {
		try {
			List<DocDiagnosis> diagnosisList = admService.getConfirmDianosis(param.get("patNum"));
			return new ResponseEntity<List<DocDiagnosis>>(diagnosisList,HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<DocDiagnosis>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	// 환자 입원 내역 조회
	@PostMapping("/confirmAdmission")
	public ResponseEntity<List<Admission>> confirmAdmission(@RequestBody Map<String, Long> param) {
		try {
			List<Admission> admissionList = admService.getConfirmAdmission(param.get("patNum"));
			return new ResponseEntity<List<Admission>>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Admission>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	// 해당 환자의 진료확인서, 처방전  확인
	@GetMapping("/patientStorageList")
	public ResponseEntity<PatientDto> patientStorageList (@RequestParam("patNum") Long patNum) {
		
		try {
			PatientDto patientDto = patientService.getPatientStorage(patNum);
			return new ResponseEntity<PatientDto>(patientDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<PatientDto>(HttpStatus.BAD_REQUEST);
		}
		// 해당 환자의 번호를 통해서 진료를 받은 기록과 입원을 한 기록을 모두 조회
		// 각각 입원과 진료를 레포지토리로 List 모 만들고 dto에 변수로 넣어줌
		// 서비스를 통해서 합침
		// 합치는 과정에서 Optional<Patient>로 받아와 해당 정보를 dto에 빌드로 만들어서 넣어주었다
		
	}

}
