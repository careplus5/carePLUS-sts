package com.kosta.care.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
public class Beds {
	@Id
	private Long bedsNum;
	@Column
	private Long bedsDept;
	@Column
	private String bedsWard; //병동
	@Column
	private Integer bedsRoom; //병실
	@Column
	private Integer bedsBed; //침대번호
	@Column
	private Boolean bedsIsUse;
	@Column
	private String bedsCode;
}
