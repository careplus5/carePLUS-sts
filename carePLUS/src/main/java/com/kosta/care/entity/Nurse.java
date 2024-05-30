package com.kosta.care.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Nurse {
	@Id
	private Integer nurId;
	@Column
	private Integer profNum;
	@Column
	private Integer departmentNum;
	
}
