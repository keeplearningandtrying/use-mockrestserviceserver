package com.wei.mock_server_test;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Employee {
	
	private String id;

	@JsonProperty("employee_name")
	private String name;

	@JsonProperty("employee_salary")
	private String salary;

	@JsonProperty("employee_age")
	private String age;

	@JsonProperty("profile_image")
	private String image;

}
