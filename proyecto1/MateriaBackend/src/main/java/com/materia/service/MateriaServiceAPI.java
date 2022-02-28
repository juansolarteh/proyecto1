package com.materia.service;

import java.util.List;

import com.materia.commons.GenericServiceAPI;
import com.materia.dto.MateriaDTO;
import com.materia.model.Materia;

public interface MateriaServiceAPI extends GenericServiceAPI<Materia, MateriaDTO>{
	List<MateriaDTO> getMateriasByUsuario(String id) throws Exception;
	String matricularEstudiante(String id, String codigo) throws Exception;
	boolean desmatricularEstudiante(String id, String codigo) throws Exception;
	String getSubject(String id) throws Exception;
	String getTeacher(Integer id) throws Exception;
	boolean estudiantePerteneceMateria(String idEstudiante, String idMateria) throws Exception;
}
