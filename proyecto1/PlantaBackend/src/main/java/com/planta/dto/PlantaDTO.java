package com.planta.dto;

import java.util.Map;
public class PlantaDTO {
	
	private String id;
	private String id_topics;
	private String name;
	private Map<String, String> variables;
	//camaras
	//
	//
	//
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String id_topics() {
		return id_topics;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public Map<String, String> getvariables() {
		return variables;
	}
	public void setvariables(Map<String, String> variables) {
		this.variables = variables;
	}
	
}
