package com.practica.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.practica.commons.GenericServiceImpl;
import com.practica.dto.PracticaDTO;
import com.practica.model.Practica;

@Service
public class PracticaServiceImpl extends GenericServiceImpl<Practica, PracticaDTO> implements PracticaServiceAPI {

	@Autowired
	private Firestore firestore;

	@Override
	public CollectionReference getCollection() {
		return firestore.collection("Workshops");
	}

//	Listar las practicas de la materia idMateria que tiene asignadas un usuario con id = idUsuario y que estan disponibles o publicadas
	public List<PracticaDTO> getPracticas(String idMateria) throws Exception {
		List<PracticaDTO> result = new ArrayList<PracticaDTO>();
		for (PracticaDTO practica : getAll()) {
			if (practica.getCourse_id() != null) {
				if (practica.getCourse_id().compareTo(idMateria) == 0) {
					if (new Date().after(practica.getStart_available())
							|| new Date().equals(practica.getStart_available())) {
						if (new Date().before(practica.getEnd_available())
								|| new Date().equals(practica.getEnd_available())) {
							practica.setCourse_name(getCourse(practica.getCourse_id()));
							practica.setTopic_name(getTopic(practica.getTopic_id()));
							result.add(practica);
						}
					}
				}
			}
		}
		return result;
	}

	// Metodo para obtener el nombre del curso
	private String getCourse(String idCourse) throws Exception {
		String result = "";
		DocumentReference docRef = firestore.collection("Courses").document(idCourse);
		// asynchronously retrieve the document
		ApiFuture<DocumentSnapshot> future = docRef.get();
		// ...
		// future.get() blocks on response
		DocumentSnapshot document = future.get();
		if (document.exists()) {
			result = document.getData().get("name").toString();
		}
		return result;
	}

	// Metodo para obtener el nombre de un tema de practica
	private String getTopic(String id) throws Exception {
		String result = "";
		DocumentReference docRef = firestore.collection("Topics").document(id);
		// asynchronously retrieve the document
		ApiFuture<DocumentSnapshot> future = docRef.get();
		// ...
		// future.get() blocks on response
		DocumentSnapshot document = future.get();
		if (document.exists()) {
			result = document.getData().get("name").toString();
		}
		return result;
	}
}
