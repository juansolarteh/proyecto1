package com.practices.service;

import java.util.Date;
import java.util.List;

import com.practices.commons.GenericServiceAPI;
import com.practices.dto.PracticesDTO;
import com.practices.model.Practices;

public interface PracticesServiceAPI extends GenericServiceAPI<Practices, PracticesDTO>{
	List<PracticesDTO> getByStudent(String idStudent) throws Exception;
	List<String> getDatesByTopic(String idTopic) throws Exception;
}
