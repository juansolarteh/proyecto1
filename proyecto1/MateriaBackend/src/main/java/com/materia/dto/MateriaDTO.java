package com.materia.dto;

import java.util.Date;
import java.util.Map;
public class MateriaDTO {
	private String id;
	private String subject_id;
	private String teacher_name;
	private Integer teacher_id;
	private String name;
	private Map<String, String> students;
	private String access_key;
	private Date start;
	private Date end;
	
	
	public Integer getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(Integer teacher_id) {
		this.teacher_id = teacher_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubject_id() {
		return subject_id;
	}
	public void setSubject_id(String subject_id) {
		this.subject_id = subject_id;
	}
	public String getTeacher() {
		return teacher_name;
	}
	public void setTeacher(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, String> getStudents() {
		return students;
	}
	public void setStudents(Map<String, String> students) {
		this.students = students;
	}
	public String getAccess_key() {
		return access_key;
	}
	public void setAccess_key(String access_key) {
		this.access_key = access_key;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	
}
