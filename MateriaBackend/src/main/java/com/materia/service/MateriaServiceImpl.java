package com.materia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.materia.commons.GenericServiceImpl;
import com.materia.dto.MateriaDTO;
import com.materia.model.Materia;

@Service
public class MateriaServiceImpl  extends GenericServiceImpl<Materia, MateriaDTO> implements MateriaServiceAPI{
	
	@Autowired
	private Firestore firestore;

	@Override
	public CollectionReference getCollection() {
		return firestore.collection("Materia");
	}
	
//	Listar materias en las que está registrado un usuario con id = idUsuario
	public List<MateriaDTO> getMateriasByUsuario(String idUsuario) throws Exception {
		List<MateriaDTO> result = new ArrayList<MateriaDTO>();		
		for (MateriaDTO materia : getAll()) {
			if(materia.getUsuarios().containsKey(idUsuario)) {
				result.add(materia);
			}
		}
		return result;
	}
	
	//Matricula a un estudiante en una materia
	public boolean matricularEstudiante(String idUsuario,String nombreEst , String codigoMateria) throws Exception{
		MateriaDTO materia = get(codigoMateria);
		Materia materiaN= new Materia();
		Map<String, String> m = materia.getUsuarios();
		if(m.containsKey(idUsuario)) {
			return false;//Ya está matriculado
		}else {
			m.put(idUsuario, nombreEst);
			materiaN.setNombre(materia.getNombre());
			materiaN.setUsuarios(m);
			materiaN.setCodigoGenerado(materia.getCodigoGenerado());
			materiaN.setIdProfesor(materia.getIdProfesor());
			delete(codigoMateria);
			save(materiaN,materia.getId());
			return true;
		}
	}
	
	//Desmatricula a un estudiante en una materia
		public boolean desmatricularEstudiante(String idUsuario, String codigoMateria) throws Exception{
			MateriaDTO materia = get(codigoMateria);
			Materia materiaN= new Materia();
			Map<String, String> m = materia.getUsuarios();
			if(m.containsKey(idUsuario)) {
				m.remove(idUsuario);
				materiaN.setNombre(materia.getNombre());
				materiaN.setUsuarios(m);
				materiaN.setCodigoGenerado(materia.getCodigoGenerado());
				materiaN.setIdProfesor(materia.getIdProfesor());
				delete(codigoMateria);
				save(materiaN,materia.getId());
				return true;//Ya está desmatriculado
			}else {
				return false;
			}
		}
}
