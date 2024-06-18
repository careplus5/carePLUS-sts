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
	private Long departmentNum;
	@Column
	private String departmentName;
	@Column
	private Long jobNum;
	@Column
	private Long department1Num;
	@Column
	private Long department2Num;
	
	
}
