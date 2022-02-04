package com.planta.service;

import java.util.List;

import com.planta.commons.GenericServiceAPI;
import com.planta.dto.PlantaDTO;
import com.planta.model.Planta;

public interface PlantaServiceAPI extends GenericServiceAPI<Planta, PlantaDTO>{
	public boolean addVariable(String idPlanta, String variable, String value) throws Exception;
}
