package com.kosta.care.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class Administor implements Employee {
	@Id
	private Long manNum;
	@Column
	private String manPassword;
	@Column
	private String manEmail;
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return manNum;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return manPassword;
	}
	@Override
	public void setPassword(String encodePassword) {
		this.manPassword=encodePassword;
		
	}
}
