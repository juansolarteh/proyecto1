package com.practica.service;


import java.util.List;

import com.practica.commons.GenericServiceAPI;
import com.practica.dto.PracticaDTO;
import com.practica.model.Practica;

public interface PracticaServiceAPI extends GenericServiceAPI<Practica, PracticaDTO>{
	List<PracticaDTO> getPracticas(String id) throws Exception;
	public String getCourse(String idCourse) throws Exception;
	public String getTopic(String id) throws Exception;
}
