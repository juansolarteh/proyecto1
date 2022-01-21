package com.agenda.service;

import com.agenda.dto.AgendaDTO;
import com.agenda.model.Agenda;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agenda.commons.GenericServiceImpl;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

@Service
public class AgendaServiceImpl  extends GenericServiceImpl<Agenda, AgendaDTO> implements AgendaServiceAPI{
	
	@Autowired
	private Firestore firestore;

	@Override
	public CollectionReference getCollection() {
		return firestore.collection("Agenda");
	}
	
	//Toca modificar la logica para obtener agendas referentes al usuario con id = idUsuario
	public List<AgendaDTO> getByUsuario(String idUsuario) throws Exception {
		List<AgendaDTO> result = new ArrayList<AgendaDTO>();
		ApiFuture<QuerySnapshot> query = getCollection().get();
		List<QueryDocumentSnapshot> documents = query.get().getDocuments();
		for (QueryDocumentSnapshot doc : documents) {
			AgendaDTO object = doc.toObject(clazz);
			PropertyUtils.setProperty(object, "id", doc.getId());
			result.add(object);
		}
		return result;
	}
}
