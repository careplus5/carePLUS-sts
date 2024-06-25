package com.kosta.care.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestFile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long testFileNum;
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "testNum")
	private Test test;
	@Column
	private String testFileType;
	@Column
	private String testFileName;
	@Column
	private String testFilePath;
	@Column
	private Long testFileSize;
	@Column
	private Long testMetNum;
	@Column
	@CreationTimestamp
	private Date testFileUploadDate; 
	
	//testNum이랑...
	//filePath = fileName인건가? 파일이름을 왜... 왜 저장해지..?
	//왜냐면 테스트넘으로 여러개 파일을 저장해야 한 테스트에 여러개의 검사파일을 넣을 수 있지 않나 싶어서
	//테스트 테이블의 테스트파일넘 컬럼에 여러개 스택해서 저장하고 스플릿 해서 가져올 수 있다 하며는 안넣어도 될듯?
	
	
}
