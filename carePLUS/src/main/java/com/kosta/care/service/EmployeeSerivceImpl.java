package com.kosta.care.service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import com.kosta.care.entity.MedicalTechnician;
import com.kosta.care.entity.Nurse;
import com.kosta.care.entity.Profile;
import com.kosta.care.repository.AdminHospitalRepository;
import com.kosta.care.repository.DoctorRepository;
import com.kosta.care.repository.MedicalTechnicianRepository;
import com.kosta.care.repository.NurseRepository;
import com.kosta.care.repository.ProfileRepository;
import com.kosta.care.util.EmployeeUtil;
import com.kosta.care.util.PageInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeSerivceImpl implements EmployeeSerivce {

	@Value("${upload.profile}")
	private String uploadProfile;

	private final NurseRepository nurseRepository;
	private final DoctorRepository doctorRepository;
	private final MedicalTechnicianRepository medicalTechnicianRepository;
	private final AdminHospitalRepository adminHospitalRepository;
	private final ProfileRepository profileRepository;
	private final SequenceService sequenceService;
	@Autowired
	EmployeeUtil employeeUtil;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Long join(EmployeeDto employeeDto, MultipartFile file) throws Exception {
		Long profNum = null;
		if (file != null && !file.isEmpty()) {
			Profile pfile = Profile.builder().profileType(file.getContentType()).profileDirectory(uploadProfile)
					.profileSize(file.getSize()).build();
			profileRepository.save(pfile);
			File upFile = new File(uploadProfile, pfile.getProfileNum() + "");
			file.transferTo(upFile);
			profNum = pfile.getProfileNum();
			employeeDto.setProfNum(profNum);
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy");
		String year = LocalDate.now().format(formatter);

		System.out.println("serviceJobNum: " + employeeDto.getJobNum());
		Long job = employeeDto.getJobNum();
		String findJob = job.toString();

		// 직원번호 생성
		String empNumStr;
		Long empNum;
		if (findJob.equals("11")) {
			empNumStr = employeeDto.getJobNum() + "" + employeeDto.getDepartmentNum() + "" + year + ""
					+ sequenceService.getNextSequence("sq_doc");
		} else if (findJob.equals("12")) {
			empNumStr = employeeDto.getJobNum() + "" + employeeDto.getDepartmentNum() + "" + year + ""
					+ sequenceService.getNextSequence("sq_nur");
		} else if (findJob.equals("13")) {
			employeeDto.setDepartmentNum(99L);
			empNumStr = employeeDto.getJobNum() + "" + employeeDto.getDepartmentNum() + "" + year + ""
					+ sequenceService.getNextSequence("sq_adm");
			employeeDto.setDepartmentName("원무과");
		} else if (findJob.equals("14")) {
			empNumStr = employeeDto.getJobNum() + "" + employeeDto.getDepartmentNum() + "" + year + ""
					+ sequenceService.getNextSequence("sq_met");
		} else {
			throw new IllegalArgumentException("Invalid job number");
		}

		System.out.println("serviceEmpStr: " + empNumStr);

		// 생성한 empNum을 적용
		empNum = Long.parseLong(empNumStr);
		employeeDto.setEmpNum(empNum);

		// 비밀번호 암호화
		String rawPassword = employeeDto.getEmpPassword();
		String encodePassword = bCryptPasswordEncoder.encode(rawPassword);
		employeeDto.setEmpPassword(encodePassword);

		Object emp = null;
		// 맞는 위치에 저장
		switch (findJob) {
		case "11":
			emp = employeeUtil.DtoToDoc(employeeDto);
			doctorRepository.save((Doctor) emp);
			break;
		case "12":
			emp = employeeUtil.DtoToNur(employeeDto);
			nurseRepository.save((Nurse) emp);
			break;
		case "13":
			emp = employeeUtil.DtoToAdm(employeeDto);
			adminHospitalRepository.save((AdminHospital) emp);
			break;
		case "14":
			emp = employeeUtil.DtoToMet(employeeDto);
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
			Profile pFile = Profile.builder().profileType(file.getContentType()).profileDirectory(uploadProfile)
					.profileSize(file.getSize()).build();
			profileRepository.save(pFile); // file table에 파일정보 삽입
			File upFile = new File(uploadProfile, pFile.getProfileNum() + "");
			file.transferTo(upFile); // file upload
			employeeDto.setProfNum(pFile.getProfileNum());
		} else {
			employeeDto.setProfNum(beforeEmployeeDto.getProfNum());
		}

		String StringEmpNum = employeeDto.getEmpNum().toString();
		String findJob = StringEmpNum.substring(0, 2);
		
		if (!employeeDto.getEmpPassword().isEmpty() && employeeDto.getEmpPassword() != null) {
			String rawPassword = employeeDto.getEmpPassword();
			String encodePassword = bCryptPasswordEncoder.encode(rawPassword);
			employeeDto.setEmpPassword(encodePassword);
		}
		
		System.out.println("modifydName: " + employeeDto.getDepartmentName());
		Object emp = null;
		// 맞는 위치에 저장
		switch (findJob) {
		case "11":
			emp = employeeUtil.modifyDtoToDoc(employeeDto,beforeEmployeeDto);
			if (employeeDto.getEmpPassword() == null && employeeDto.getEmpPassword().trim().equals("")) {
				employeeDto.setEmpPassword(beforeEmployeeDto.getEmpPassword());
			}
			doctorRepository.save((Doctor) emp);
			break;
		case "12":
			emp = employeeUtil.modifyDtoToNur(employeeDto,beforeEmployeeDto);
			if (employeeDto.getEmpPassword() == null && employeeDto.getEmpPassword().trim().equals("")) {
				employeeDto.setEmpPassword(beforeEmployeeDto.getEmpPassword());
			}
			nurseRepository.save((Nurse) emp);
			break;
		case "13":
			emp = employeeUtil.modifyDtoToAdm(employeeDto,beforeEmployeeDto);
			if (employeeDto.getEmpPassword() == null && employeeDto.getEmpPassword().trim().equals("")) {
				employeeDto.setEmpPassword(beforeEmployeeDto.getEmpPassword());
			}
			adminHospitalRepository.save((AdminHospital) emp);
			break;
		case "14":
			emp = employeeUtil.modifyDtoToMet(employeeDto,beforeEmployeeDto);
			if (employeeDto.getEmpPassword() == null && employeeDto.getEmpPassword().trim().equals("")) {
				employeeDto.setEmpPassword(beforeEmployeeDto.getEmpPassword());
			}
			medicalTechnicianRepository.save((MedicalTechnician) emp);
			break;
		}

		// 기존 프로필 사진 삭제
		if (file != null && !file.isEmpty() && beforeEmployeeDto.getProfNum() != null) {// 기존에 파일이 있는 조건
			profileRepository.deleteById(beforeEmployeeDto.getProfNum());
			File beforeFile = new File(uploadProfile, beforeEmployeeDto.getProfNum() + "");
			beforeFile.delete();
		}
		return employeeDto;
	}

	public List<EmployeeDto> employeeListByPage(String jobName, PageInfo pageInfo, String type, String word)
			throws Exception {

		Page<?> pages = null;
		List<EmployeeDto> employeeDtoList = new ArrayList<>();
		System.out.println("service:" + jobName);
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
			PageRequest nurPageRequest = PageRequest.of(pageInfo.getCurPage() - 1, 5,
					Sort.by(Sort.Direction.DESC, "nurNum"));
			System.out.println("serviceNurPageRequest:" + nurPageRequest);
			if (word == null || word.trim().equals("")) {
				pages = (nurseRepository.findAll(nurPageRequest));
			} else {
				if (type.contains("departmentName")) {
					pages = (nurseRepository.findByDepartmentNameContains(word, nurPageRequest));
				} else if (type.contains("empPosition")) {
					String searchWord = null;
					List<String> positionList = new ArrayList<>();
					positionList.add("진료");
					positionList.add("입원");
					positionList.add("수술");
					for (String position : positionList) {
						if (position.contains(word)) {
							if (position.equals("진료")) {
								searchWord = "1";
							} else if (position.equals("입원")) {
								searchWord = "2";
							} else if (position.equals("수술")) {
								searchWord = "3";
							}
						}
					}
					pages = (nurseRepository.findByNurPositionContains(searchWord, nurPageRequest));
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
				if (type.contains("empName")) {
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
				if (type.contains("department2Name")) {
					pages = (medicalTechnicianRepository.findByMetDepartment2NameContains(word, metPageRequest));
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
		} else {
			System.out.println("정상입니다 안심하세요");
		}
		System.out.println("serviceDto:" + employeeDtoList);
		return employeeDtoList;
	}
}
