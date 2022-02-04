package com.resultados.model;

import java.util.Map;
import java.util.Date;
public class Practices {

	private String workshop_id;
	private String leader_id;
	private Map<String, String> students;
	private Map<String, String> data;
	private Map<String, String> attendees;
	private Map<String, String> anomalias;
	private int next_anomaly_id;
	private Date start;
	private Date end;
	
	public int getNextAnomalyId() {
		return next_anomaly_id;
	}
	public void setNextAnomalyId(int next_anomaly_id) {
		this.next_anomaly_id = next_anomaly_id;
	}
	public String getLeader_id(){
		return leader_id;
	}
	public void setLeader_id(String leader_id){
		this.leader_id=leader_id;
	}
	public String getWorkshop_id(){
		return workshop_id;
	}
	public void setWorkshop_id(String workshop_id){
		this.workshop_id=workshop_id;
	}
	public Map<String, String> getStudents(){
		return students;
	}
	public void setStudents(Map<String, String> students){
		this.students=students;
	}
	public Map<String, String> getData(){
		return data;
	}
	public void setData(Map<String, String> data){
		this.data=data;
	}
	public Map<String, String> getAttendees(){
		return attendees;
	}
	public void setAttendees(Map<String, String> attendees){
		this.attendees=attendees;
	}
	public Map<String, String> getAnomalias(){
		return anomalias;
	}
	public void setAnomalias(Map<String, String> anomalias){
		this.anomalias=anomalias;
	}
	public Date getStart(){
		return start;
	}
	public void setStart(Date start){
		this.start=start;	
	}
	public Date getEnd(){
		return end;
	}
	public void setEnd(Date end){
		this.end=end;	
	}
	
}
