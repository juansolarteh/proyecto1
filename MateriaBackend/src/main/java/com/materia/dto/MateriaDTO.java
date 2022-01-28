package com.materia.dto;

import java.util.Map;
public class MateriaDTO {
	private String id;
	private String nombre;
	private Map<String, String> usuarios;
	private String codigoGenerado;
	private String idProfesor;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Map<String, String> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(Map<String, String> usuarios) {
		this.usuarios = usuarios;
	}
	public String getCodigoGenerado() {
		return codigoGenerado;
	}
	public void setCodigoGenerado(String codigoGenerado) {
		this.codigoGenerado = codigoGenerado;
	}
	public String getIdProfesor() {
		return idProfesor;
	}
	public void setIdProfesor(String idProfesor) {
		this.idProfesor = idProfesor;
	}
}
