package com.kosta.care.service;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.util.Value;
import com.kosta.care.dto.AdmissionRequestDto;
import com.google.auth.oauth2.IdTokenProvider.Option;
import com.kosta.care.dto.DiagnosisDueDto;
import com.kosta.care.dto.SurgeryRequestDto;
import com.kosta.care.dto.TestDto;
import com.kosta.care.dto.TestRequestDto;
import com.kosta.care.entity.Admission;
import com.kosta.care.entity.AdmissionRequest;
import com.kosta.care.entity.Beds;
import com.kosta.care.entity.DiagnosisDue;
import com.kosta.care.entity.DocDiagnosis;
import com.kosta.care.entity.Nurse;
import com.kosta.care.entity.OperationUseCheck;
import com.kosta.care.entity.Patient;
import com.kosta.care.entity.Surgery;
import com.kosta.care.entity.SurgeryRequest;
import com.kosta.care.entity.Test;
import com.kosta.care.entity.TestFile;
import com.kosta.care.entity.TestRequest;
import com.kosta.care.repository.AdmDslRepository;
import com.kosta.care.repository.AdmissionDslRepository;
import com.kosta.care.repository.AdmissionRepository;
import com.kosta.care.repository.AdmissionRequestDslRepository;
import com.kosta.care.repository.AdmissionRequestRepository;
import com.kosta.care.repository.BedsRepository;
import com.kosta.care.repository.DiagnosisDueRepository;
import com.kosta.care.repository.DocDiagnosisRepository;
import com.kosta.care.repository.NurseRepository;
import com.kosta.care.repository.OperationRoomRepository;
import com.kosta.care.repository.OperationUseCheckRepository;
import com.kosta.care.repository.PatientRepository;
import com.kosta.care.repository.SurgeryDslRespository;
import com.kosta.care.repository.SurgeryRepository;
import com.kosta.care.repository.SurgeryRequestRepository;
import com.kosta.care.repository.TestDslRepository;
import com.kosta.care.repository.TestFileRepository;
import com.kosta.care.repository.TestRepository;
import com.kosta.care.repository.TestRequestDslRepository;
import com.kosta.care.repository.TestRequestRepository;
import com.querydsl.core.Tuple;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdmServiceImpl implements AdmService {

	private final DocDiagnosisRepository docDiagnosisRepository;
	private final PatientRepository patientRepository;
	private final DiagnosisDueRepository diagnosisDueRepository;
	private final AdmissionRepository admissionRepository;
	private final AdmDslRepository admDslRepository;
	private final TestRequestDslRepository testRequestDslRepository;
	private final TestRepository testRepository;
	private final TestRequestRepository testRequestRepository;
	private final TestDslRepository testDslRepository;
	private final TestFileRepository testFileRepository;
	private final SurgeryRepository surgeryRespository;
	private final SurgeryRequestRepository surgeryRequestRepository;
	private final SurgeryDslRespository surgeryDslRespository; 
	private final OperationRoomRepository operationRoomRepository; 
	private final OperationUseCheckRepository operationUseCheckRepository;
	private final NurseRepository nurseRepository; 
	private final BedsRepository bedsRepository;
	private final AdmissionDslRepository admissionDslRepository;
	private final AdmissionRequestRepository admissionRequestRepository;
	private final AdmissionRequestDslRepository admissionRequestDslRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Value("${upload.path}")
	private String uploadPath;

	@Override
	public List<Map<String, Object>> patDiagCheckListByPatNum(Long patNum) {
		List<Tuple> tuples = admDslRepository.findPatDiagCheckListByPatNum(patNum);
		List<Map<String, Object>> patDiagCheckList = new ArrayList<>();
		Map<Long, Date> firstDiagDateMap = new HashMap<>();

		for(Tuple tuple : tuples) {
			DocDiagnosis docDiagnosis = tuple.get(0, DocDiagnosis.class);
			String patName = tuple.get(1, String.class);
			String docName = tuple.get(2, String.class);
			String departmentName = tuple.get(3, String.class);
			String testName = tuple.get(4, String.class);

			Date thisDate = docDiagnosis.getDocDiagnosisDate();
			//map에 존재하는 날짜를 thisDate와 비교해 더 작은 값을 반환 (초기 진단 날짜)
			firstDiagDateMap.merge(patNum, thisDate, (existingDate, newDate) -> 
			existingDate.before(newDate) ? existingDate : newDate);

			Map<String, Object> map = objectMapper.convertValue(docDiagnosis, Map.class);
			map.put("patName", patName);
			map.put("docName", docName);
			map.put("deptName", departmentName);
			map.put("testName", testName);
			patDiagCheckList.add(map);
		}
		for (Map<String, Object> map : patDiagCheckList) {
			map.put("firstDiagDate", firstDiagDateMap.get(patNum));
		}

		if(patDiagCheckList.isEmpty()) return null;

		return patDiagCheckList;
	}

	@Override
	public List<Map<String, Object>> patAdmCheckListByPatNum(Long patNum) {
		List<Tuple> tuples = admDslRepository.findPatAdmCheckListByPatNum(patNum);
		List<Map<String, Object>> patAdmCheckList = new ArrayList<>();

		for(Tuple tuple : tuples) {
			Admission admission = tuple.get(0, Admission.class);
			String patName = tuple.get(1, String.class);
			String docName = tuple.get(2, String.class);
			String departmentName = tuple.get(3, String.class);
			String nurName = tuple.get(4, String.class);

			Map<String, Object> map = objectMapper.convertValue(admission, Map.class);
			map.put("patName", patName);
			map.put("docName", docName);
			map.put("deptName", departmentName);
			map.put("nurName", nurName);
			patAdmCheckList.add(map);
		}

		if(patAdmCheckList.isEmpty()) return null;

		return patAdmCheckList;
	}

	// 진료예약 및 환자등록 및 의사테이블 저장
	@Override
	@Transactional
	public void diagnosisRegister(DiagnosisDueDto diagnosisDueDto) throws Exception {
		// 환자 등록
		if(diagnosisDueDto.getPatNum()==null) {
			Patient patient = Patient.builder()
					.patName(diagnosisDueDto.getPatName())
					.patJumin(diagnosisDueDto.getPatJumin())
					.patGender(diagnosisDueDto.getPatGender())
					.patTel(diagnosisDueDto.getPatTel())
					.patHeight(diagnosisDueDto.getPatHeight())
					.patWeight(diagnosisDueDto.getPatWeight())
					.patAddress(diagnosisDueDto.getPatAddress())
					.patHistory(diagnosisDueDto.getPatHistory())
					.patBloodType(diagnosisDueDto.getPatBloodType())
					.build();
			patientRepository.save(patient);
			diagnosisDueDto.setPatNum(patient.getPatNum());
		}

		// 진료예약 정보 설정
		DiagnosisDue diagnosisDue = DiagnosisDue.builder()
				.patNum(diagnosisDueDto.getPatNum())
				.docNum(diagnosisDueDto.getDocNum())
				.diagnosisDueDate(diagnosisDueDto.getDiagnosisDueDate())
				.diagnosisDueTime(diagnosisDueDto.getDiagnosisDueTime())
				.diagnosisDueState(diagnosisDueDto.getDiagnosisDueState())
				.diagnosisDueEtc(diagnosisDueDto.getDiagnosisDueEtc())
				.build();
		diagnosisDueRepository.save(diagnosisDue);

		// DocDiagnosis 정보 설정
		DocDiagnosis docDiagnosis = diagnosisDueDto.toDocDiagnosis();
		docDiagnosis.setDocDiagnosisDate(diagnosisDue.getDiagnosisDueDate());
		docDiagnosis.setPatNum(diagnosisDueDto.getPatNum());
		docDiagnosis.setDocNum(diagnosisDueDto.getDocNum());
		docDiagnosis.setDocDiagnosisState("wait");  // 진료상태
		docDiagnosis.setDiagnosisDueNum(diagnosisDue.getDiagnosisDueNum());

		docDiagnosisRepository.save(docDiagnosis);
	}

	@Override
	public List<Map<String, Object>> getPrescriptionList(Long patNum) throws Exception {

		return null;}

	public Map<String, Object> patDiagCheckInfoByDocDiagNum(Long docDiagNum) {
		Tuple tuple = admDslRepository.findPatDiagCheckInfoByDocDiagNum(docDiagNum);

		DocDiagnosis docDiag = tuple.get(0, DocDiagnosis.class);
		Patient patient = tuple.get(1, Patient.class);
		String docName = tuple.get(2, String.class);
		String deptName = tuple.get(3, String.class);
		String diseaseName = tuple.get(4, String.class);
		String admDiagContent = tuple.get(5, String.class);

		Map<String, Object> map = objectMapper.convertValue(docDiag, Map.class);
		map.put("patName", patient.getPatName());
		map.put("patGender", patient.getPatGender());
		map.put("patJumin", patient.getPatJumin());
		map.put("patTel", patient.getPatTel());
		map.put("patHistory", patient.getPatHistory());
		map.put("patAddress", patient.getPatAddress());
		map.put("docName", docName);
		map.put("deptName", deptName);
		map.put("diseaseName", diseaseName);
		if(docDiag.getDocDiagnosisContent() == null) {
			map.put("docDiagnosisContent", admDiagContent);
		}

		return map;
	}

	@Override
	public Map<String, Object> patAdmCheckInfoByAdmNum(Long admNum) {
		Tuple tuple = admDslRepository.findPatAdmCheckInfoByAdmNum(admNum);

		Admission admission = tuple.get(0, Admission.class);
		Patient patient = tuple.get(1, Patient.class);
		String docName = tuple.get(2, String.class);

		//입원 기간 계산
		Date admissionDate = admission.getAdmissionDate();
		Date dischargeDate = admission.getAdmissionDischargeDate();
		long diffMillies = dischargeDate.getTime() - admissionDate.getTime();
		long admPeriod = diffMillies / (1000 * 60 * 60 * 24);

		Map<String, Object> map = objectMapper.convertValue(admission, Map.class);
		map.put("patName", patient.getPatName());
		map.put("patGender", patient.getPatGender());
		map.put("patJumin", patient.getPatJumin());
		map.put("patTel", patient.getPatTel());
		map.put("patAddress", patient.getPatAddress());
		map.put("docName", docName);
		map.put("admPeriod", admPeriod);

		return map;
	}

	@Override
	public List<Map<String, Object>> patAdmDiagListByAdmNum(Long admNum) {
		List<Tuple> tupes = admDslRepository.findPatAdmDiagInfoByAdmNum(admNum);
		List<Map<String, Object>> admDiagList = new ArrayList<>();

		for(Tuple tuple : tupes) {
			Long admRecordNum = tuple.get(0, Long.class);
			String admRecord = tuple.get(1, String.class);
			Date diagDate = tuple.get(2, Date.class);
			String deptName = tuple.get(3, String.class);
			String diseaseName = tuple.get(4, String.class);

			Map<String, Object> map = new HashMap<>();
			map.put("admRecordNum", admRecordNum);
			map.put("admRecord", admRecord);
			map.put("diagDate", diagDate);
			map.put("deptName", deptName);
			map.put("diseaseName", diseaseName);
			admDiagList.add(map);
		}

		if(admDiagList.isEmpty()) return null;

		return admDiagList;
	}

	@Override
	public List<TestRequestDto> getTestRequestListByPatNum(Long patNum) throws Exception {
		return testRequestDslRepository.findTestRequestList(patNum);
	}

	@Override
	public AdmissionRequestDto getSearchAdmissionRequestPatient(Long patNum) throws Exception {
		Tuple tuple = admDslRepository.findAdmissionRequestByPatNum(patNum);
		System.out.println("확인" + tuple);
		if (tuple != null ) {
			String departmentName = tuple.get(0, String.class);
			String docName = tuple.get(1, String.class);
			String admissionRequestReason = tuple.get(2, String.class);
			Long admissionRequestPeriod = tuple.get(3, Long.class);
			Long department = tuple.get(4,Long.class);
			AdmissionRequestDto admissionRequestDto =new AdmissionRequestDto(departmentName,docName,admissionRequestReason,admissionRequestPeriod, department);
			return admissionRequestDto;
		} else {
			throw new Exception("조회오류");
		}

	}

	@Override
	public List<DocDiagnosis> getConfirmDianosis(Long patNum) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Admission> getConfirmAdmission(Long patNum) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void getPatientAdmissionRegist(AdmissionRequestDto admissionRequestDto) throws Exception {

		// 1. 예약된 침대의 사용유무 업데이트
		Optional<Beds> oBeds = bedsRepository.findById(admissionRequestDto.getBedsNum());
		if (oBeds.isPresent()) {
			Beds beds = oBeds.get();
			beds.setBedsIsUse(true); // 사용중으로 설정
			bedsRepository.save(beds); // 변경 사항 저장
		} else {
			throw new Exception("해당 침대를 찾을 수 없습니다: " + admissionRequestDto.getBedsNum());
		}

		// 3. 입원 테이블에 데이터 추가
		Admission admission = Admission.builder()
				.patNum(admissionRequestDto.getPatNum())
				.docNum(admissionRequestDto.getDocNum())
				.bedsNum(admissionRequestDto.getBedsNum())
				.admissionRequestNum(admissionRequestDto.getAdmissionRequestNum())
				.admissionReason(admissionRequestDto.getAdmissionRequestReason())
				.admissionDate(admissionRequestDto.getAdmissionDate())
				.admissionDueDate(admissionRequestDto.getAdmissionDueDate())
				.admissionDischargeDueDate(admissionRequestDto.getAdmissionDischargeDueDate())
				// 다른 필드들도 필요에 따라 설정
				.build();

		admissionRepository.save(admission); // 입원 정보 저장
		
		// 입원요청 환자 처리 
		Optional<AdmissionRequest> oAdmissionRequest = admissionRequestRepository.findById(admissionRequestDto.getAdmissionRequestNum());
		if (oAdmissionRequest.isPresent()) {
			AdmissionRequest admissionRequest = oAdmissionRequest.get();
			admissionRequest.setAdmissionRequestAcpt("end");
			admissionRequestRepository.save(admissionRequest);
		}
	}
		
		@Override
		public List<Time> getTestList(String testName, Date testDate) throws Exception {

			return testDslRepository.findByTestTimeByTestNameAndTestDate(testName, testDate);
		}

		@Override
		public Boolean testReserve(Test test, MultipartFile testFile) throws Exception {
			if(testFile!=null && !testFile.isEmpty()) {
				TestFile tFile = TestFile.builder()
									.testFileName(testFile.getOriginalFilename())
									.testFileSize(testFile.getSize())
									.testFileType(testFile.getContentType())
									.build();
				testFileRepository.save(tFile);
				
				File upFile = new File(uploadPath,tFile.getTestFileNum()+"");
				testFile.transferTo(upFile); //file upload
				
				test.setTestFileNum(tFile.getTestFileNum());
				test.setTestOutInspectRecord(testFile.getOriginalFilename());
				
			}
			testRepository.save(test);
			Optional<TestRequest> otestRequest = testRequestRepository.findById(test.getTestRequestNum());
			if(otestRequest.isPresent()) {
				TestRequest testRequest = otestRequest.get();
				testRequest.setTestRequestAcpt("reserve");
				testRequestRepository.save(testRequest);
			}
			return true;
		}

		
		@Override
		public SurgeryRequestDto getSurgeryRequest(Long patNum) throws Exception {
			return surgeryDslRespository.findSurgeryRequest(patNum);
		}
		
		@Override
		public Map<String, Object> operationRoomUse(Date useDate) throws Exception {
			Map<String,Object> res = new HashMap<>();
			List<Long> opRoomList = operationRoomRepository.findAll().stream()
					.map((or)->or.getOperationRoomNum())
					.collect(Collectors.toList());
			res.put("opRoomList", opRoomList);
			
			List<OperationUseCheck> opUseCheckList = operationUseCheckRepository.findByUseDate(useDate);
			res.put("opUseCheckList", opUseCheckList);
			return res;
		}

		@Override
		public Map<String, Object> sureryNurList(Long departmentNum, Date surDate) throws Exception {
			System.out.println(departmentNum);
			System.out.println(surDate);
			Map<String, Object> res = new HashMap<>();
			List<Nurse> nurseList =  nurseRepository.findByNurPositionAndDepartmentNum("3", departmentNum);
			res.put("nurseList", nurseList);
			List<Map<String,Object>> surNurList = surgeryDslRespository.findBySurNurseByOpDate(departmentNum, surDate);
			res.put("surNurList", surNurList);
			return res;
		}

		@Override
		public Boolean reserveSurgery(Surgery surgery) throws Exception {
			surgeryRespository.save(surgery);
			//수술요청 테이블 상태 변경 : wait->reserved
			SurgeryRequest surgeryRequest = surgeryRequestRepository.findById(surgery.getSurgeryRequestNum()).get();
			surgeryRequest.setSurgeryRequestAcpt("reserved");
			surgeryRequestRepository.save(surgeryRequest);
			OperationUseCheck operationUseCheck = OperationUseCheck.builder().surgeryNum(surgery.getOperationRoomNum())
				.useDate(surgery.getSurgeryDueDate())
				.time(surgery.getSurgeryDueStartTime()).build();
			operationUseCheckRepository.save(operationUseCheck);
			return true;
		}
		
		@Override
		public AdmissionRequestDto admissionRequest(Long patNum) throws Exception {
			return admissionRequestDslRepository.findAdmissionRequestByPatNum(patNum);	
		}

		@Override
		public List<Beds> findByBedsListByDeptnum(Long departmentNum) throws Exception {
			return bedsRepository.findByBedsDeptOrderByBedsWardAscBedsRoomAscBedsNum(departmentNum);
		}

		@Override
		public Boolean procAdmission(Admission admission) throws Exception {
			admission.setAdmissionDiagState("wait");
			Optional<Beds> obeds = bedsRepository.findById(admission.getBedsNum());
			if(obeds.isPresent()) {
				Beds beds = obeds.get();
				beds.setBedsIsUse(true);
				bedsRepository.save(beds);
			}
			admissionRepository.save(admission);
			AdmissionRequest admissionRequest = admissionRequestRepository.findById(admission.getAdmissionRequestNum()).get();
			admissionRequest.setAdmissionRequestAcpt("reserved");
			admissionRequestRepository.save(admissionRequest);
			return true;
		}	


}
