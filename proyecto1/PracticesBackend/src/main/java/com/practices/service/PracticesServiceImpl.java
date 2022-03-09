package com.practices.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csvreader.CsvWriter;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
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

	@Override
	public boolean reportarAnomalia(String idResultado, String anomalia) throws Exception {
		for(PracticesDTO practice : getAll()) {
			if(practice!=null) {
				if(practice.getId().compareTo(idResultado)==0) {
					Map<String, String> anomalias=practice.getAnomalias();
					anomalias.put(String.valueOf(practice.getNext_anomaly_id()), anomalia);
						//Actualización en la base de datos
						DocumentReference docRef=firestore.collection("Practices").document(practice.getId());
						ApiFuture<WriteResult> future=docRef.update("anomalias",anomalias);
						future.get();
						int aux=practice.getNext_anomaly_id();
						aux=aux+1;
						ApiFuture<WriteResult> futur=docRef.update("nextAnomalyId",aux);
						futur.get();
						return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean addStudents(String idResultado, String idStudent) throws Exception {
		for(PracticesDTO practice : getAll()) {
			if(practice!=null) {
				if(practice.getId().compareTo(idResultado)==0) {
					Map<String, String> students=practice.getStudents();
					if(!students.containsKey(idStudent)) {
						students.put(idStudent, getNombreEstudiante(idStudent));
						//Actualización en la base de datos
						DocumentReference docRef=firestore.collection("Practices").document(practice.getId());
						ApiFuture<WriteResult> future=docRef.update("students",students);
						future.get();;
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean addAttendees(String idResultado, String idStudent) throws Exception {
		for(PracticesDTO practice : getAll()) {
			if(practice!=null) {
				if(practice.getId().compareTo(idResultado)==0) {
					Map<String, String> attendees=practice.getAttendees();
					if(!attendees.containsKey(idStudent)) {
						attendees.put(idStudent, getNombreEstudiante(idStudent));
						//Actualización en la base de datos
						DocumentReference docRef=firestore.collection("Practices").document(practice.getId());
						ApiFuture<WriteResult> future=docRef.update("attendees",attendees);
						future.get();;
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean addData(String idResultado, String variable, String value) throws Exception {
		for(PracticesDTO practice : getAll()) {
			if(practice!=null) {
				if(practice.getId().compareTo(idResultado)==0) {
					Map<String, String> data=practice.getData();
						data.put(variable, value);
						//Actualización en la base de datos
						DocumentReference docRef=firestore.collection("Practices").document(practice.getId());
						ApiFuture<WriteResult> future=docRef.update("data",data);
						future.get();;
						return true;
				}
			}
		}
		return false;
	}

	@Override
	public String crearCSV(String idResultado) throws Exception {
		String nombreArchivo="./datos"+idResultado+".csv";
		try	{
			boolean existe=new File(nombreArchivo).exists();
			if(existe) {
				File archivoDatos=new  File(nombreArchivo);
				archivoDatos.delete();
			}
			//Crerar el archivo
			CsvWriter salidaCSV=new CsvWriter(new FileWriter(nombreArchivo, true), ';');
			
			//
			salidaCSV.write("Estudiantes: ");
			
			for(PracticesDTO practice : getAll()) {
				if(practice!=null) {
					if(practice.getId().compareTo(idResultado)==0) {
						for(String student: practice.getStudents().values()) {
							salidaCSV.write(student);
						}
						salidaCSV.endRecord();
						salidaCSV.write("Asistentes: ");
						
						for(String student: practice.getAttendees().values()) {
							salidaCSV.write(student);
						}
						salidaCSV.endRecord();
						break;
					}
				}
			}
			salidaCSV.close();
			
			return nombreArchivo;
			
		}catch (IOException e) {
			e.printStackTrace();
			return "Ocurrio un error";
		}
	}
	private String getNombreEstudiante(String idEstudiante) throws Exception {
		String result="";
		DocumentReference docRef = firestore.collection("Students").document(idEstudiante);
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document=future.get();
		if(document.exists()) {
			String nombre=document.getData().get("name").toString();
			String apellido=document.getData().get("surname").toString();
			result=nombre.concat(" ");
			result=result.concat(apellido);
		}
		return result;
	}
}
