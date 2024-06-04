package com.kosta.care.entity;

import javax.persistence.Id;

public interface Employee {
	// id
	@Id
	Long getId();
	String getName();
	String getPassword();
	void setPassword(String encodePassword);

}
