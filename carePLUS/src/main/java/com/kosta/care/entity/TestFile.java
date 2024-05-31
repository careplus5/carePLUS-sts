package com.kosta.care.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	private Integer testFileNum;
	@Column
	private String testFileType;
	@Column
	private String testFileName;
	@Column
	private String testFileSize;
	@Column
	@CreationTimestamp
	private Date testFileUploadDate; 
	
}
