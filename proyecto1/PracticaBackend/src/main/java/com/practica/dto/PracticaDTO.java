package com.practica.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class PracticaDTO {
	private Map<String,String> cameras;
	private String id;
	private String topic_id;
	private String course_id;
	private Map<String, String> data;
	private Date start_available;
	private Date end_available;
	private String course_name;
	private String topic_name;
	private Map<String,Map<String,String>> constants;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Map<String, String> getData() {
		return data;
	}
	public void setData(Map<String, String> archivos) {
		this.data = archivos;
	}
	public String getTopic_id() {
		return topic_id;
	}
	public void setTopic_id(String topic_id) {
		this.topic_id = topic_id;
	}
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	public Date getStart_available() {
		return start_available;
	}
	public void setStart_available(Date start_available) {
		this.start_available = start_available;
	}
	public Date getEnd_available() {
		return end_available;
	}
	public void setEnd_available(Date end_available) {
		this.end_available = end_available;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public String getTopic_name() {
		return topic_name;
	}
	public void setTopic_name(String topic_name) {
		this.topic_name = topic_name;
	}
	public Map<String, String> getCameras() {
		return cameras;
	}
	public void setCameras(Map<String, String> cameras) {
		this.cameras = cameras;
	}
	public Map<String, Map<String, String>> getConstants() {
		return constants;
	}
	public void setConstants(Map<String, Map<String, String>> constants) {
		this.constants = constants;
	}
	
}
