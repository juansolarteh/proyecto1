package com.practices.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.practices.commons.GenericServiceImpl;
import com.practices.dto.PracticesDTO;
import com.practices.model.Practices;

@Service
public class PracticesServiceImpl  extends GenericServiceImpl<Practices, PracticesDTO> implements PracticesServiceAPI{
	
	@Autowired
	private Firestore firestore;

	@Override
	public CollectionReference getCollection() {
		return firestore.collection("Practices");
	}
	
	@Override
	public List<PracticesDTO> getByStudent(String idStudent) throws Exception {
		return getFromMapByKey("students", idStudent);
	}
	
	@Override
	public List<String> getDatesByTopic(String idTopic) throws Exception {
		List<String> dates = new ArrayList<String>();
		SimpleDateFormat formato1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formato2 = new SimpleDateFormat("HH:mm:ss");
		List<PracticesDTO> practices = getListByFieldPath("topic_id", idTopic);
		for (PracticesDTO practice : practices) {
			String fechaCompleta = formato1.format(practice.getStart())+"T"+formato2.format(practice.getStart());
			dates.add(fechaCompleta);
			System.out.println(practice.getStart());
		}
		return dates;
	}
}
