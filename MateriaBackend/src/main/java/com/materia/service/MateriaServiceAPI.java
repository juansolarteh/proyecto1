package com.materia.service;

import java.util.List;

import com.materia.commons.GenericServiceAPI;
import com.materia.dto.MateriaDTO;
import com.materia.model.Materia;

public interface MateriaServiceAPI extends GenericServiceAPI<Materia, MateriaDTO>{
	List<MateriaDTO> getMateriasByUsuario(String id) throws Exception;
	boolean matricularEstudiante(String id,String nombre, String codigo) throws Exception;
	boolean desmatricularEstudiante(String idUsuario, String codigoMateria) throws Exception;
}
