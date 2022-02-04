package com.resultados.service;

import com.resultados.commons.GenericServiceAPI;
import com.resultados.dto.PracticesDTO;
import com.resultados.model.Practices;

public interface PracticesAPI extends GenericServiceAPI<Practices, PracticesDTO>{
	public boolean reportarAnomalia(String idResultado,String anomalia) throws Exception;
	public boolean addStudents(String idResultado, String idStudent) throws Exception;
	public boolean addAttendees(String idResultado, String idStudent) throws Exception;
	public boolean addData(String idResultado, String variable, String value) throws Exception;
}
