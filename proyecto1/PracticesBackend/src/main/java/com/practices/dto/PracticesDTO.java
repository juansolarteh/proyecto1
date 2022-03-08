package com.practices.dto;

import java.util.Date;
import java.util.Map;

public class PracticesDTO {

	private String id;
	private String workshop_id;
	private String leader_id;
	private Map<String, String> students;
	private Map<String, String> attendees;
	private Map<String, String> data;
	private Map<String, String> anomalias;
	private String leaderName;
	private String practiceName;
	private int next_anomaly_id;
	private Date start;
	private Date end;
	
	public String getLeaderName() {
		return leaderName;
	}
	public void setLeaderName(String leaderName) {
		this.leaderName=leaderName;
	}
	public String getPracticeName() {
		return practiceName;
	}
	public void setPracticeName(String practiceName) {
		this.practiceName=practiceName;
	}
	public int getNextAnomalyId() {
		return next_anomaly_id;
	}
	public void setNextAnomalyId(int next_anomaly_id) {
		this.next_anomaly_id = next_anomaly_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWorkshop_id() {
		return workshop_id;
	}
	public void setWorkshop_id(String workshop_id) {
		this.workshop_id = workshop_id;
	}
	public String getLeader_id() {
		return leader_id;
	}
	public void setLeader_id(String leader_id) {
		this.leader_id = leader_id;
	}
	public Map<String, String> getStudents() {
		return students;
	}
	public void setStudents(Map<String, String> students) {
		this.students = students;
	}
	public Map<String, String> getAttendees() {
		return attendees;
	}
	public void setAttendees(Map<String, String> attendees) {
		this.attendees = attendees;
	}
	public Map<String, String> getData() {
		return data;
	}
	public void setData(Map<String, String> data) {
		this.data = data;
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
	public Map<String, String> getAnomalias(){
		return anomalias;
	}
	public void setAnomalias(Map<String, String> anomalias){
		this.anomalias=anomalias;
	}
	
}
