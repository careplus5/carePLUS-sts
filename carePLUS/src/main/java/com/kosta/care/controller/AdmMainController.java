package com.kosta.care.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.care.dto.AdmissionRequestDto;
import com.kosta.care.dto.DiagnosisDueDto;
import com.kosta.care.dto.SurgeryRequestDto;
import com.kosta.care.dto.TestRequestDto;
import com.kosta.care.entity.Admission;
import com.kosta.care.entity.Beds;
import com.kosta.care.entity.Department;
import com.kosta.care.entity.DiagnosisDue;
import com.kosta.care.entity.Prescription;
import com.kosta.care.entity.Surgery;
import com.kosta.care.entity.Test;
import com.kosta.care.service.AdmService;
import com.kosta.care.service.AdmissionService;
import com.kosta.care.service.DepartmentService;
import com.kosta.care.service.DiagnosisDueService;
import com.kosta.care.service.DoctorService;
import com.kosta.care.service.PrescriptionService;
import com.kosta.care.service.TestRequestService;
import com.querydsl.core.Tuple;

@RestController
public class AdmMainController {

	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private TestRequestService testRequestService;
	
	@Autowired
	private DiagnosisDueService diagnosisDueService;
	
	@Autowired
	private AdmService admService;
	
	@Autowired
	private AdmissionService admissionService;
	
	@Autowired
	private PrescriptionService prescriptionService;
	
	// 진료 부서 조회 (리액트에서 select option에 쓰임)
	@GetMapping("/departments")
	public ResponseEntity<List<Department>> departments() {
		
		try {
			List<Department> departmentList = departmentService.departmentList();
			System.out.println(departmentList);
			return new ResponseEntity<List<Department>> (departmentList ,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Department>> (HttpStatus.BAD_REQUEST);
		}
	}
	
//	@GetMapping("/searchPatientPrescription")
//	public ResponseEntity<List<Map<String, Object>>> searchPatientPrescription(){
//		
//	}
//	
	// 환자 조회 검사 (검사예약)
	@PostMapping("/testRequestPatientLatest")
	public ResponseEntity<TestRequestDto> getLatestTestRequestByPatient(@RequestBody Map<String,Long> param) {
		System.out.println(param);
		try {
			TestRequestDto testRequestdto = testRequestService.getLatestTestRequestByPatNum(param.get("patNum"));
			if (testRequestdto != null) {
				return new ResponseEntity<TestRequestDto> (testRequestdto,HttpStatus.OK);
			} else {
				return new ResponseEntity<TestRequestDto> (HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<TestRequestDto> (HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/doctorDueList")
	public ResponseEntity<Map<String, Object>> doctorDueList(@RequestParam("departmentNum") Long departmentNum,
			@RequestParam("diagnosisDueDate") String date) {
		try {
			Map<String, Object> res = diagnosisDueService.doctorDiagnosisDueList(departmentNum, Date.valueOf(date));
			System.out.println(res);
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
	}

	
	@GetMapping("/diagSearchAll")
	public ResponseEntity<List<DiagnosisDue>> diagSearchAll() {
		try {
			List<DiagnosisDue> diagnosisList = diagnosisDueService.diagSearchAll();
			return new ResponseEntity<List<DiagnosisDue>>(diagnosisList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<DiagnosisDue>>( HttpStatus.BAD_REQUEST);
		}
	}
	
	// 진료 예약 등록
		@PostMapping("/patientDiagnosisDueRegist")
		public ResponseEntity<String> patientDiagnosisDueRegist (@RequestBody DiagnosisDueDto diagnosisDueDto) {
		    try {
		        diagnosisDueService.diagnosisRegister(diagnosisDueDto);
		        return new ResponseEntity<String>("진료예약 성공", HttpStatus.OK);
		    } catch (Exception e) {
		        e.printStackTrace();
		        return new ResponseEntity<String>("진료예약 실패", HttpStatus.BAD_REQUEST);
		    }
		}

		// 퇴원 관련해서 해당 환자 조회
		@GetMapping("/patientAdmissionState")
		public ResponseEntity<Tuple> patientAdmissionState(@RequestParam("patNum") Long patNum) {
			
			try {
				
				Tuple tuple = admissionService.patientDischargeRegist(patNum);
				if (tuple != null) {
					return new ResponseEntity<Tuple>(tuple, HttpStatus.OK);
				} else {
					return new ResponseEntity<Tuple>(HttpStatus.BAD_REQUEST);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Tuple>(HttpStatus.BAD_REQUEST);
			}
		}
		
		// 처방천 뽑기전 처방 테이블에 해당 환자 조회
		@PostMapping("/patNumPrescriptionList")
		public ResponseEntity<List<Prescription>> patNumPrescriptionList(@RequestBody Map<String,Long> param) {
			try {
				List<Prescription> prescriptionList = prescriptionService.patientPrescriptionList(param.get("patNum"));
				return new ResponseEntity<List<Prescription>>(prescriptionList, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<List<Prescription>>(HttpStatus.BAD_REQUEST);
				
			}
		}
		
		@PostMapping("/testRequestList")
		public ResponseEntity<List<TestRequestDto>> getTestRequestList(@RequestBody Map<String,Long> param) {
			try {
				List<TestRequestDto> testRequestdto = admService.getTestRequestListByPatNum(param.get("patNum"));
				if (testRequestdto != null) {
					return new ResponseEntity<List<TestRequestDto>> (testRequestdto,HttpStatus.OK);
				} else {
					return new ResponseEntity<List<TestRequestDto>> (HttpStatus.BAD_REQUEST);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<List<TestRequestDto>> (HttpStatus.BAD_REQUEST);
			}
		}	
		
		
		@GetMapping("/testTimeList")
		public ResponseEntity<List<Time>> testList(@RequestParam("testName") String testName,
				@RequestParam("testDate") String date) {
			try {
				List<Time> timeList = admService.getTestList(testName,  Date.valueOf(date));
				return new ResponseEntity<List<Time>>(timeList, HttpStatus.OK);
			} catch(Exception e) {
				e.printStackTrace();
				return new ResponseEntity<List<Time>>(HttpStatus.BAD_REQUEST);
			}
		}
		
		@PostMapping("/testReserve")
		public ResponseEntity<Boolean> testReserve(@ModelAttribute Test test, 
				@RequestParam(required = false) MultipartFile testFile) {
			try {
				Boolean reserve = admService.testReserve(test, testFile);
				return new ResponseEntity<Boolean>(reserve, HttpStatus.OK);
			} catch(Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
			}
		}
		
		@PostMapping("/surgeryRequest")
		public ResponseEntity<SurgeryRequestDto> surgeryRequest(@RequestBody Map<String,Long> param) {
			try {
				SurgeryRequestDto surgeryRequestDto = admService.getSurgeryRequest(param.get("patNum"));
				return new ResponseEntity<SurgeryRequestDto>(surgeryRequestDto, HttpStatus.OK);
			} catch(Exception e) {
				e.printStackTrace();
				return new ResponseEntity<SurgeryRequestDto>(HttpStatus.BAD_REQUEST);			
			}
		}
		
		@PostMapping("/opRoomUseCheck")
		public ResponseEntity<Map<String,Object>> operationRoomUseCheck(@RequestBody Map<String,Date> param) {
			try {
				Map<String, Object> res = admService.operationRoomUse(param.get("date"));
				return new ResponseEntity<Map<String,Object>>(res, HttpStatus.OK);
			} catch(Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
			}
		}
		
		@PostMapping("/surNurseList")
		public ResponseEntity<Map<String,Object>> surNurseList(@RequestBody Map<String,Object> param) {
			try {
				Map<String,Object> res = admService.sureryNurList(
						Long.valueOf(String.valueOf(param.get("departmentNum"))), 
						Date.valueOf(String.valueOf(param.get("surDate"))));
				return new ResponseEntity<Map<String,Object>>(res, HttpStatus.OK);
			} catch(Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
			}
		}
		
		@PostMapping("/reserveSurgery") 
		public ResponseEntity<Boolean> reserveSurgery(@RequestBody Surgery surgery) {
			try {
				System.out.println(surgery);
				admService.reserveSurgery(surgery);
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} catch(Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
			}
		}
		
	@PostMapping("/patDiagCheckList")
	public ResponseEntity<List<Map<String, Object>>> patDiagCheckList(@RequestBody Map<String, Long> param) {
		try {
			List<Map<String, Object>> docDiagnosisList = admService.patDiagCheckListByPatNum(param.get("patNum"));
			return new ResponseEntity<List<Map<String, Object>>>(docDiagnosisList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String, Object>>>( HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/patAdmCheckList")
	public ResponseEntity<List<Map<String, Object>>> patAdmCheckList(@RequestBody Map<String, Long> param) {
		try {
			List<Map<String, Object>> admCheckList = admService.patAdmCheckListByPatNum(param.get("patNum"));
			return new ResponseEntity<List<Map<String, Object>>>(admCheckList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String, Object>>>( HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/patDiagCheckInfo")
	public ResponseEntity<Map<String, Object>> patDiagCheckInfo(@RequestParam("docDiagNum") Long docDiagNum) {
		try {
			Map<String, Object> diagCheckInfo = admService.patDiagCheckInfoByDocDiagNum(docDiagNum);
			return new ResponseEntity<Map<String,Object>>(diagCheckInfo, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/patAdmCheckInfo")
	public ResponseEntity<Map<String, Object>> patAdmCheckInfo(@RequestParam("admNum") Long admNum) {
		try {
			Map<String, Object> admCheckInfo = admService.patAdmCheckInfoByAdmNum(admNum);
			return new ResponseEntity<Map<String,Object>>(admCheckInfo, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/patAdmDiagList")
	public ResponseEntity<List<Map<String, Object>>> patAdmDiagList(@RequestParam("admNum") Long amdNum) {
		try {
			List<Map<String, Object>> admDiagList = admService.patAdmDiagListByAdmNum(amdNum);
			return new ResponseEntity<List<Map<String,Object>>>(admDiagList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String,Object>>>(HttpStatus.BAD_REQUEST);
		}
	}	

	@PostMapping("/admissionRequestbypatientinfo")
	public ResponseEntity<AdmissionRequestDto> admissionRequestbypatientinfo(@RequestBody Map<String, Long> param) {
		try {
//			System.out.println("컨트롤러확인 : " + param);
			AdmissionRequestDto admissionDto = admService.getSearchAdmissionRequestPatient(param.get("patNum"));
			return new ResponseEntity<AdmissionRequestDto>(admissionDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<AdmissionRequestDto>(HttpStatus.BAD_REQUEST);
		}
	}


	// 현재 작업 중 
	@PostMapping("/patientAdmissionRegist")
	public ResponseEntity<String> patientAdmissionRegist(@RequestBody AdmissionRequestDto admissionRequestDto) {
		try {
			System.out.println(admissionRequestDto);
			admService.getPatientAdmissionRegist(admissionRequestDto);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/admissionRequest")
	public ResponseEntity<Map<String,Object>> admissionRequest(@RequestBody Map<String, Long> param) {
		try {
			System.out.println(param);
			Map<String,Object> res = new HashMap<>();
			AdmissionRequestDto admissionRequestDto = admService.admissionRequest(param.get("patNum"));
			if(admissionRequestDto==null) {
				throw new Exception();
			}
			res.put("admissionRequest", admissionRequestDto);
			List<Beds> bedsList = admService.findByBedsListByDeptnum(admissionRequestDto.getDepartmentNum());
			res.put("bedsList", bedsList);
			return new ResponseEntity<Map<String,Object>>(res, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/admission")
	public ResponseEntity<Boolean> procAdmission(@RequestBody Admission admission) {
		try {
			System.out.println(admission);
			admService.procAdmission(admission);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
		}
	}


	
	
}
