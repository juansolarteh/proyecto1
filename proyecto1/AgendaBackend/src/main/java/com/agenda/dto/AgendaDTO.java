package com.agenda.dto;

import java.util.Map;
public class AgendaDTO {
	
	private String id;
	private String codigoPractica;
	private String fechaHora;
	private String estado;
	private Map<String, String> usuarios;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCodigoPractica() {
		return codigoPractica;
	}
	public void setCodigoPractica(String codigoPractica) {
		this.codigoPractica = codigoPractica;
	}
	public String getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(String fechaHora) {
		this.fechaHora = fechaHora;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Map<String, String> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(Map<String, String> usuarios) {
		this.usuarios = usuarios;
	}
}
