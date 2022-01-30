package com.materia.service;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.materia.commons.GenericServiceImpl;
import com.materia.dto.MateriaDTO;
import com.materia.model.Materia;

@Service
public class MateriaServiceImpl extends GenericServiceImpl<Materia, MateriaDTO> implements MateriaServiceAPI {

	@Autowired
	private Firestore firestore;

	@Override
	public CollectionReference getCollection() {
		return firestore.collection("Courses");
	}

//	Listar materias en las que está registrado un usuario con id = idUsuario
	public List<MateriaDTO> getMateriasByUsuario(String idUsuario) throws Exception {
		List<MateriaDTO> result = new ArrayList<MateriaDTO>();
		for (MateriaDTO materia : getAll()) {
			if (materia.getStudents() != null) {
				if (materia.getStudents().containsKey(idUsuario)) {
					result.add(materia);
				}
			}
		}
		return result;
	}

	// Matricula a un estudiante en una materia
	public boolean matricularEstudiante(String idUsuario, String codigoAutoMateria) throws Exception {
		for (MateriaDTO materia : getAll()) {
			//Valida el codigo de acceso autogenerado
			if (compararCodigo(materia, codigoAutoMateria)) {			
				Map<String, String> estudiantesMatriculados = materia.getStudents();
				if (estudiantesMatriculados.containsKey(idUsuario)) {
					return false;// Ya está matriculado
				} else {
					String estudiante = getNombreEstudiante(idUsuario);
					//Valida que el estudiante este en la base de datos
					if (estudiante != "") {
						estudiantesMatriculados.put(idUsuario, estudiante);
						// Update an existing document
						DocumentReference docRef = firestore.collection("Courses").document(materia.getId());
						// (async) Update one field
						ApiFuture<WriteResult> future = docRef.update("students", estudiantesMatriculados);
						future.get();
						return true;
					} else {
						return false;
					}
				}
			}
		}
		return false;
	}

	// Desmatricula a un estudiante en una materia
	public boolean desmatricularEstudiante(String idUsuario, String codigoMateria) throws Exception {
		MateriaDTO materia = get(codigoMateria);
		if(materia!=null) {
			Map<String, String> estudiantesMatriculados = materia.getStudents();
			if (estudiantesMatriculados.containsKey(idUsuario)) {
				estudiantesMatriculados.remove(idUsuario);
				DocumentReference docRef = firestore.collection("Courses").document(codigoMateria);
				ApiFuture<WriteResult> future = docRef.update("students", estudiantesMatriculados);
				future.get();
				return true;// Ya está desmatriculado
			} 
		}
		return false;
	}

	private String getNombreEstudiante(String idEstudiante) throws Exception {
		String result = "";
		DocumentReference docRef = firestore.collection("Students").document(idEstudiante);
		// asynchronously retrieve the document
		ApiFuture<DocumentSnapshot> future = docRef.get();
		// ...
		// future.get() blocks on response
		DocumentSnapshot document = future.get();
		if (document.exists()) {
			String nombre = document.getData().get("name").toString();
			String apellido = document.getData().get("surname").toString();
			result = nombre.concat(" ");
			result = result.concat(apellido);
		}
		return result;
	}
	private boolean compararCodigo(MateriaDTO materia,String codigo) {
		if(materia.getAccess_key()==codigo) {
			return true;
		}
		return false;
	}

}
