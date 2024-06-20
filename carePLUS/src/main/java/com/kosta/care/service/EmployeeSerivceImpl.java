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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Long join(EmployeeDto employeeDto, MultipartFile file) throws Exception {
		Long profNum = null;
		if (file != null && !file.isEmpty()) {
			Profile pfile = Profile.builder().profileType(file.getContentType()).profileDirectory(uploadPath)
					.profileSize(file.getSize()).build();
			profileRepository.save(pfile);
			File upFile = new File(uploadPath, pfile.getProfileNum() + "");
			file.transferTo(upFile);
			profNum = pfile.getProfileNum();
			employeeDto.setProfNum(profNum);
		}

		Long job = employeeDto.getJobNum();
		String jobString = job.toString();
		String findJob = jobString.substring(0, 2);

		// 중복체크를 위한 Optional생성
		Optional<?> oemp = Optional.empty();

		// 아이디 중복체크
		if (findJob.equals("11")) {
			oemp = doctorRepository.findById(employeeDto.getEmpNum());
		} else if (findJob.equals("12")) {
			oemp = nurseRepository.findById(employeeDto.getEmpNum());
		} else if (findJob.equals("13")) {
			oemp = adminHospitalRepository.findById(employeeDto.getEmpNum());
		} else if (findJob.equals("14")) {
			oemp = medicalTechnicianRepository.findById(employeeDto.getEmpNum());
		}
		// 중복시 오류메시지
		if (oemp.isPresent())
			throw new Exception("중복된 직원번호 입니다");

		// 비밀번호 암호화
		String rawPassword = employeeDto.getEmpPassword();
		String encodePassword = bCryptPasswordEncoder.encode(rawPassword);
		employeeDto.setEmpPassword(encodePassword);

		Object emp = employeeUtil.chooseEmpDto(employeeDto);

		// 맞는 위치에 save
		switch (findJob) {
		case "11":
			doctorRepository.save((Doctor) emp);
			break;
		case "12":
			nurseRepository.save((Nurse) emp);
			break;
		case "13":
			adminHospitalRepository.save((AdminHospital) emp);
			break;
		case "14":
			medicalTechnicianRepository.save((MedicalTechnician) emp);
			break;
		}

		return employeeDto.getEmpNum();
	}

	@Override
	public Long delete(Long empNum) throws Exception {
		String StringEmpNum = empNum.toString();
		String findJob = StringEmpNum.substring(0, 2);

		if (findJob.equals("11")) {
			doctorRepository.deleteById(empNum);
		} else if (findJob.equals("12")) {
			nurseRepository.deleteById(empNum);
		} else if (findJob.equals("13")) {
			adminHospitalRepository.deleteById(empNum);
		} else if (findJob.equals("14")) {
			medicalTechnicianRepository.deleteById(empNum);
		}
		return empNum;
	}

	@Override
	public EmployeeDto detail(Long empNum) throws Exception {
		String StringEmpNum = empNum.toString();
		String findJob = StringEmpNum.substring(0, 2);
		EmployeeDto empDto = null;

		if (findJob.equals("11")) {
			Optional<Doctor> oemp = doctorRepository.findById(empNum);
			if (oemp.isEmpty())
				throw new Exception("직원번호 오류");
			empDto = oemp.get().DocToEmployeeDto();
			empDto.setJobName("의사");
			return empDto;
		} else if (findJob.equals("12")) {
			Optional<Nurse> oemp = nurseRepository.findById(empNum);
			if (oemp.isEmpty())
				throw new Exception("직원번호 오류");
			empDto = oemp.get().NurToEmployeeDto();
			empDto.setJobName("간호사");
			return empDto;
		} else if (findJob.equals("13")) {
			Optional<AdminHospital> oemp = adminHospitalRepository.findById(empNum);
			if (oemp.isEmpty())
				throw new Exception("직원번호 오류");
			empDto = oemp.get().AdmToEmployeeDto();
			empDto.setJobName("원무과");
			return empDto;
		} else if (findJob.equals("14")) {
			Optional<MedicalTechnician> oemp = medicalTechnicianRepository.findById(empNum);
			if (oemp.isEmpty())
				throw new Exception("직원번호 오류");
			empDto = oemp.get().MetToEmployeeDto();
			empDto.setJobName("의료기사");
			return empDto;
		}
		return null;

	}

	@Override
	public EmployeeDto modify(EmployeeDto employeeDto, MultipartFile file) throws Exception {
		// 이전 employee정보 가져오기
		EmployeeDto beforeEmployeeDto = detail(employeeDto.getEmpNum());

		// 프로필 저장
		if (file != null && !file.isEmpty()) {
			Profile pFile = Profile.builder().profileType(file.getContentType()).profileDirectory(uploadPath)
					.profileSize(file.getSize()).build();
			profileRepository.save(pFile); // file table에 파일정보 삽입
			File upFile = new File(uploadPath, pFile.getProfileNum() + "");
			file.transferTo(upFile); // file upload
			employeeDto.setProfNum(pFile.getProfileNum());
		} else {
			employeeDto.setProfNum(beforeEmployeeDto.getProfNum());
		}

		// 새로 가져온 emp Entity로 만들어주기
		Employee emp = empRepository.identifyJob(Long.toString(employeeDto.getEmpNum()));

		String StringEmpNum = employeeDto.getEmpNum().toString();
		String findJob = StringEmpNum.substring(0, 2);

		// 맞는 테이블에 저장
		switch (findJob) {
		case "11":
			doctorRepository.save((Doctor) emp);
			break;
		case "12":
			nurseRepository.save((Nurse) emp);
			break;
		case "13":
			adminHospitalRepository.save((AdminHospital) emp);
			break;
		case "14":
			medicalTechnicianRepository.save((MedicalTechnician) emp);
			break;
		}

		// 기존 프로필 사진 삭제
		if (file != null && !file.isEmpty() && beforeEmployeeDto.getProfNum() != null) {// 기존에 파일이 있는 조건
			profileRepository.deleteById(beforeEmployeeDto.getProfNum());
			File beforeFile = new File(uploadPath, beforeEmployeeDto.getProfNum() + "");
			beforeFile.delete();
		}
		return employeeDto;
	}

	public List<EmployeeDto> employeeListByPage(String jobName, PageInfo pageInfo, String type, String word) throws Exception {

		Page<?> pages = null;
		List<EmployeeDto> employeeDtoList = new ArrayList<>();
		System.out.println("service:"+jobName);
		if (jobName.equals("11")) {
			PageRequest docPageRequest = PageRequest.of(pageInfo.getCurPage() - 1, 5,
					Sort.by(Sort.Direction.DESC, "docNum"));

			if (word == null || word.trim().equals("")) {
				pages = (doctorRepository.findAll(docPageRequest));
			} else {
				if (type.contains("departmentName")) {
					pages = (doctorRepository.findByDepartmentNameContains(word, docPageRequest));
				} else if (type.contains("empName")) {
					pages = (doctorRepository.findByDocNameContains(word, docPageRequest));
				}
			}

			pageInfo.setAllPage(pages.getTotalPages());

			Integer startPage = (pageInfo.getCurPage() - 1) / 5 * 10 + 1;
			Integer endPage = Math.min(startPage + 5 - 1, pageInfo.getAllPage());
			pageInfo.setStartPage(startPage);
			pageInfo.setEndPage(endPage);

			for (Object employee : pages.getContent()) {
				employeeDtoList.add(employeeUtil.DocToEmpDto((Doctor) employee));
			}
		} else if (jobName.equals("12")) {
			PageRequest nurPageRequest = PageRequest.of(pageInfo.getCurPage() - 1, 5,Sort.by(Sort.Direction.DESC, "nurNum"));
				System.out.println("serviceNurPageRequest:"+nurPageRequest);
			if (word == null || word.trim().equals("")) {
				pages = (nurseRepository.findAll(nurPageRequest));
			} else {
				if (type.contains("departmentName")) {
					pages = (nurseRepository.findByDepartmentNameContains(word, nurPageRequest));
				} else if (type.contains("empName")) {
					pages = (nurseRepository.findByNurNameContains(word, nurPageRequest));
				}
			}

			pageInfo.setAllPage(pages.getTotalPages());

			Integer startPage = (pageInfo.getCurPage() - 1) / 5 * 10 + 1;
			Integer endPage = Math.min(startPage + 5 - 1, pageInfo.getAllPage());
			pageInfo.setStartPage(startPage);
			pageInfo.setEndPage(endPage);

			for (Object employee : pages.getContent()) {
				employeeDtoList.add(employeeUtil.NurToEmpDto((Nurse) employee));
			}
		} else if (jobName.equals("13")) {

			PageRequest admPageRequest = PageRequest.of(pageInfo.getCurPage() - 1, 5,
					Sort.by(Sort.Direction.DESC, "admNum"));

			if (word == null || word.trim().equals("")) {
				pages = (adminHospitalRepository.findAll(admPageRequest));
			} else {
				if (type.contains("departmentName")) {
					pages = (adminHospitalRepository.findByDepartmentNameContains(word, admPageRequest));
				} else if (type.contains("empName")) {
					pages = (adminHospitalRepository.findByAdmNameContains(word, admPageRequest));
				}
			}

			pageInfo.setAllPage(pages.getTotalPages());

			Integer startPage = (pageInfo.getCurPage() - 1) / 5 * 10 + 1;
			Integer endPage = Math.min(startPage + 5 - 1, pageInfo.getAllPage());
			pageInfo.setStartPage(startPage);
			pageInfo.setEndPage(endPage);

			for (Object employee : pages.getContent()) {
				employeeDtoList.add(employeeUtil.AdmToEmpDto((AdminHospital) employee));
			}
		} else if (jobName.equals("14")) {

			PageRequest metPageRequest = PageRequest.of(pageInfo.getCurPage() - 1, 5,
					Sort.by(Sort.Direction.DESC, "metNum"));

			if (word == null || word.trim().equals("")) {
				pages = (medicalTechnicianRepository.findAll(metPageRequest));
			} else {
				if (type.contains("departmentName")) {
					pages = (medicalTechnicianRepository.findByDepartmentNameContains(word, metPageRequest));
				} else if (type.contains("empName")) {
					pages = (medicalTechnicianRepository.findByMetNameContains(word, metPageRequest));
				}
			}

			pageInfo.setAllPage(pages.getTotalPages());

			Integer startPage = (pageInfo.getCurPage() - 1) / 5 * 10 + 1;
			Integer endPage = Math.min(startPage + 5 - 1, pageInfo.getAllPage());
			pageInfo.setStartPage(startPage);
			pageInfo.setEndPage(endPage);

			for (Object employee : pages.getContent()) {
				employeeDtoList.add(employeeUtil.MetToEmpDto((MedicalTechnician) employee));
			}
		}
		System.out.println("serviceDto:"+employeeDtoList);
		return employeeDtoList;
	}
}
