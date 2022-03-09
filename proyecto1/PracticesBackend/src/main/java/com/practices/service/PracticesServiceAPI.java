package com.practices.service;

import java.util.Date;
import java.util.List;

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
	//public boolean addData(String idResultado, String variable, String value) throws Exception;
	public String crearCSV(String idResultado) throws Exception;
}
