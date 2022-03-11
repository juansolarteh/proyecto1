package com.practices.model;

import java.util.Date;
import java.util.Map;

public class Practices {

	private String topic_id;
	private String workshop_id;
	private String leader_id;
	private Map<String, String> students;
	private Map<String, String> attendees;
	private Map<String, Map<String, Float>> data;
	private Map<String, String> variables;
	private Map<String, String> anomaly;
	private int next_anomaly_id;
	private String leaderName;
	private String practiceName;
	private Date start;
	private Date end;
	
	public Map<String, String> getAnomaly() {
		return anomaly;
	}
	public void setAnomaly(Map<String, String> anomaly) {
		this.anomaly = anomaly;
	}
	public Map<String, String> getVariables() {
		return variables;
	}
	public void setVariables(Map<String, String> variables) {
		this.variables = variables;
	}
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
	
	public int getNext_anomaly_id() {
		return next_anomaly_id;
	}
	public void setNext_anomaly_id(int next_anomaly_id) {
		this.next_anomaly_id = next_anomaly_id;
	}
	public int getNextAnomalyId() {
		return next_anomaly_id;
	}
	public void setNextAnomalyId(int next_anomaly_id) {
		this.next_anomaly_id = next_anomaly_id;
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
	public Map<String, Map<String, Float>> getData() {
		return data;
	}
	public void setData(Map<String, Map<String, Float>> data) {
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
	public String getTopic_id() {
		return topic_id;
	}
	public void setTopic_id(String topic_id) {
		this.topic_id = topic_id;
	}
}
