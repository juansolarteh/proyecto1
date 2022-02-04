package com.planta.model;

import java.util.Map;

public class Planta {

	private String id_topics;
	private String name;
	private Map<String, String> variables;
	
	
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
