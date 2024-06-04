package com.kosta.care.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.care.dto.EmployeeDto;
import com.kosta.care.entity.AdminHospital;
import com.kosta.care.entity.Doctor;
import com.kosta.care.entity.Employee;
import com.kosta.care.entity.MedicalTechnician;
import com.kosta.care.entity.Nurse;
import com.kosta.care.entity.Profile;
import com.kosta.care.repository.AdminHospitalRepository;
import com.kosta.care.repository.DoctorRepository;
import com.kosta.care.repository.EmployeeRepository;
import com.kosta.care.repository.MedicalTechnicianRepository;
import com.kosta.care.repository.NurseRepository;
import com.kosta.care.repository.ProfileRepository;
import com.kosta.care.util.EmployeeUtil;
import com.kosta.care.util.PageInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeSerivceImpl implements EmployeeSerivce {
	
	@Value("${upload.path}")
	private String uploadPath;
	
	private final NurseRepository nurseRepository;
	private final DoctorRepository doctorRepository;
	private final MedicalTechnicianRepository medicalTechnicianRepository;
	private final AdminHospitalRepository adminHospitalRepository;
	private final ProfileRepository profileRepository;
	private final EmployeeRepository empRepository;
	@Autowired
	EmployeeUtil employeeUtil;
//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Override
	public Long Join(EmployeeDto employeeDto, MultipartFile file) throws Exception {
		Long profNum = null;
		if(file != null && !file.isEmpty()) {
			Profile pfile = Profile.builder().profileType(file.getContentType()).profileDirectory(uploadPath)
					.profileSize(file.getSize()).build();
			profileRepository.save(pfile);
			File upFile = new File(uploadPath, pfile.getProfileNum()+"");
			file.transferTo(upFile);
			profNum = pfile.getProfileNum();
			employeeDto.setProfNum(profNum);
		}

		Long job = employeeDto.getJobNum();
		System.out.println("servicejob:"+job);
		String jobString = job.toString();
		String findJob = jobString.substring(0,2);
		
		//넣어 줄 Entity 선택해서 정보 호출
		Employee emp = empRepository.identifyJob(Long.toString(employeeDto.getEmpNum()));
		
		//중복체크를 위한 Optional생성
		Optional<?> oemp = Optional.empty();
		System.out.println("Join:"+emp);
			
		//아이디 중복체크
		if(findJob.equals("11")) {
		    oemp = doctorRepository.findById(employeeDto.getEmpNum());
		}else if(findJob.equals("12")) {
		    oemp = nurseRepository.findById(employeeDto.getEmpNum());
		}else if(findJob.equals("13")) {
		    oemp = adminHospitalRepository.findById(employeeDto.getEmpNum());
		}else if(findJob.equals("14")) {
		    oemp = medicalTechnicianRepository.findById(employeeDto.getEmpNum());
		}
		//중복시 오류메시지
		if(oemp.isPresent()) throw new Exception("중복된 직원번호 입니다");
		
//		//비밀번호 암호화
//		String rawPassword = employeeDto.getEmpPassword();
//		String encodePassword = bCryptPasswordEncoder.encode(rawPassword);
//		employeeDto.setEmpPassword(encodePassword);
		

		//맞는 위치에 save
		switch (findJob) {
	    case "11": doctorRepository.save((Doctor)emp); break;
	    case "12": nurseRepository.save((Nurse)emp); break;
	    case "13": adminHospitalRepository.save((AdminHospital)emp); break;
	    case "14": medicalTechnicianRepository.save((MedicalTechnician)emp); break;   
		}
		
		return employeeDto.getEmpNum();
	}

	@Override
	public Long Delete(Long empNum) throws Exception {
		String StringEmpNum = empNum.toString();
		String findJob = StringEmpNum.substring(0,2);
		
		if(findJob.equals("11")) {
		    doctorRepository.deleteById(empNum);
		}else if(findJob.equals("12")) {
		    nurseRepository.deleteById(empNum);
		}else if(findJob.equals("13")) {
		    adminHospitalRepository.deleteById(empNum);
		}else if(findJob.equals("14")) {
			medicalTechnicianRepository.deleteById(empNum);
		}
		return empNum;
	}

	@Override
	public EmployeeDto Detail(Long empNum) throws Exception {
		String StringEmpNum = empNum.toString();
		String findJob = StringEmpNum.substring(0,2);
		
		if(findJob.equals("11")) {
		    Optional<Doctor>oemp = doctorRepository.findById(empNum);
		    if (oemp.isEmpty())
				throw new Exception("직원번호 오류");
		    return oemp.get().DocToEmployeeDto();
		}else if(findJob.equals("12")) {
			Optional<Nurse>oemp = nurseRepository.findById(empNum);
		    if (oemp.isEmpty())
				throw new Exception("직원번호 오류");
		    return oemp.get().NurToEmployeeDto();
		}else if(findJob.equals("13")) {
			Optional<AdminHospital>oemp = adminHospitalRepository.findById(empNum);
		    if (oemp.isEmpty())
				throw new Exception("직원번호 오류");
		    return oemp.get().AdmToEmployeeDto();
		}else if(findJob.equals("14")) {
			Optional<MedicalTechnician>oemp = medicalTechnicianRepository.findById(empNum);
		    if (oemp.isEmpty())
				throw new Exception("직원번호 오류");
		    return oemp.get().MetToEmployeeDto();
		}
		return null;
		
	}

	@Override
	public EmployeeDto Modify(Long empNum, Long departmentNum, Long jobNum, String department2Name, String empPosition,
			String empName, String empTel, String empEmail, String empPassword, MultipartFile file) throws Exception {
		//이전 employee정보 가져오기
		EmployeeDto beforeEmployeeDto = Detail(empNum);
		
		//새로 받아온 employee정보 저장
		EmployeeDto employeeDto = EmployeeDto.builder()
				 .jobNum(jobNum)
				 .profNum(null)
				 .departmentNum(departmentNum)
				 .department2Name(department2Name)
				 .empPosition(empPosition)
				 .empName(empName)
				 .empTel(empTel)
				 .empEmail(empEmail)
				 .empNum(empNum)
				 .empPassword(empPassword)
				 .build();
		//프로필 저장
		if(file!=null && !file.isEmpty()) {
			Profile pFile = Profile.builder()
					.profileType(file.getContentType()).profileDirectory(uploadPath)
					.profileSize(file.getSize()).build();
			profileRepository.save(pFile); //file table에 파일정보 삽입
			File upFile = new File(uploadPath,pFile.getProfileNum()+"");
			file.transferTo(upFile); // file upload
			employeeDto.setProfNum(pFile.getProfileNum());;
		}else {
			employeeDto.setProfNum(beforeEmployeeDto.getProfNum());
		}
		
		//새로 가져온 emp Entity로 만들어주기
		Employee emp = empRepository.identifyJob(Long.toString(employeeDto.getEmpNum()));
		
		//맞는 테이블에 저장
		switch (jobNum+"") {
	    case "11": doctorRepository.save((Doctor)emp); break;
	    case "12": nurseRepository.save((Nurse)emp); break;
	    case "13": adminHospitalRepository.save((AdminHospital)emp); break;
	    case "14": medicalTechnicianRepository.save((MedicalTechnician)emp); break;   
		}
		
		//기존 프로필 사진 삭제
		if(file!=null && !file.isEmpty() &&
				beforeEmployeeDto.getProfNum()!=null) {// 기존에 파일이 있는 조건
			profileRepository.deleteById(beforeEmployeeDto.getProfNum());
			File beforeFile = new File(uploadPath, beforeEmployeeDto.getProfNum()+"");
			beforeFile.delete();
		}
		return employeeDto;
	}
	
//	public List<EmployeeDto> employeeListByPage(PageInfo pageInfo, String type, String word) throws Exception {
//		PageRequest pageRequest = PageRequest.of(pageInfo.getCurPage() - 1, 10, Sort.by(Sort.Direction.DESC, "num"));
//		Page<?> pages = null;
//		// 목록 조회
//		if (word == null || word.trim().equals("")) {
//			pages = doctorRepository.findAll(pageRequest);
//		} else {
//			if (type.equals("empNum")) {
//				pages = boardRepository.findBySubjectContains(word, pageRequest);
//			} else if (type.equals("jobName")) {
//				pages = boardRepository.findByContentContains(word, pageRequest);
//			} else if (type.equals("departmentName")) {
//				pages = boardRepository.findByMember_Id(word, pageRequest);
//			}
//		}
//		pageInfo.setAllPage(pages.getTotalPages());
//
//		Integer startPage = (pageInfo.getCurPage() - 1) / 10 * 10 + 1;
//		Integer endPage = Math.min(startPage + 10 - 1, pageInfo.getAllPage());
//		pageInfo.setStartPage(startPage);
//		pageInfo.setEndPage(endPage);
//
//		List<BoardDto> boardDtoList = new ArrayList<>();
//		for (Board board : pages.getContent()) {
//			boardDtoList.add(board.toBoardDto());
//		}
//		return boardDtoList;
//	}
}
