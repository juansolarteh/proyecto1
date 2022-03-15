package com.student.dto;

import java.util.List;

public class StudentDTO {
	
	private String id;
	private String name;
	private String surname;
	private String email;
	private List<String> course_id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public List<String> getCourse_id() {
		return course_id;
	}
	public void setCourse_id(List<String> course_id) {
		this.course_id = course_id;
	}
	
	
	
	
}
