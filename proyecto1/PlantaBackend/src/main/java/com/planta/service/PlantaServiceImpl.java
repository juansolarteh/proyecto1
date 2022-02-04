package com.planta.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.planta.commons.GenericServiceImpl;
import com.planta.dto.PlantaDTO;
import com.planta.model.Planta;

@Service
public class PlantaServiceImpl  extends GenericServiceImpl<Planta, PlantaDTO> implements PlantaServiceAPI{
	
	@Autowired
	private Firestore firestore;

	@Override
	public CollectionReference getCollection() {
		return firestore.collection("Plant");
	}
	
	public boolean addVariable(String idPlanta, String variable, String value) throws Exception {
		for(PlantaDTO planta : getAll()) {
			if(planta!=null) {
				if(planta.getId().compareTo(idPlanta)==0) {
					Map<String, String> variables=planta.getvariables();
					variables.put(variable, value);
						//Actualización en la base de datos
						DocumentReference docRef=firestore.collection("Plant").document(planta.getId());
						ApiFuture<WriteResult> future=docRef.update("variables",variables);
						future.get();;
						return true;
				}
			}
		}
		return false;
	}
}
