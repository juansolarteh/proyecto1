package com.practices.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.practices.commons.GenericServiceAPI;
import com.practices.dto.PracticesDTO;
import com.practices.dto.SendPracticesDTO;
import com.practices.model.Practices;

public interface PracticesServiceAPI extends GenericServiceAPI<Practices, PracticesDTO>{
	List<SendPracticesDTO> getByStudent(String idStudent) throws Exception;
	List<String> getDatesByTopic(String idTopic) throws Exception;
	public boolean reportarAnomalia(String idResultado,String anomalia) throws Exception;
	public boolean addStudents(String idResultado, String idStudent) throws Exception;
	public boolean addAttendees(String idResultado, String idStudent) throws Exception;
	public String crearCSV(String idResultado) throws Exception;
	public void practicaEnEjecucion(String planta, String idPractice) throws InterruptedException, ExecutionException;
	public Map<String, Map<String, Float>> getDataPractice(String idPractice) throws Exception;
	public SendPracticesDTO scheduledPractice(String idStudent, String idWorkshop) throws Exception;
}
