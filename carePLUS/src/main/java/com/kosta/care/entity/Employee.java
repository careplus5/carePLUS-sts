package com.kosta.care.entity;


public interface Employee {

	Long getId();
	String getName();
	String getPassword();
	int getJobTitle();
	void setPassword(String encodePassword);

}
