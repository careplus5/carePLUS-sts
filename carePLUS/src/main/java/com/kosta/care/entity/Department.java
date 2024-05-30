package com.kosta.care.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {
	@Id
	private Integer departmentNum;
	@Column
	private String departmentName;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "jobNum")
	private Integer jobNum;
	
	
}
