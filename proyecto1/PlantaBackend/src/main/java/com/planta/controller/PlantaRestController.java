package com.planta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planta.dto.PlantaDTO;
import com.planta.model.Planta;
import com.planta.service.PlantaServiceAPI;

@RestController
@RequestMapping(value = "/planta/api/v1/")
@CrossOrigin("*")
public class PlantaRestController {

	@Autowired
	private PlantaServiceAPI plantaServiceAPI;

	@GetMapping(value = "/all")
	public List<PlantaDTO> getAll() throws Exception {
		return plantaServiceAPI.getAll();
	}
	

	@GetMapping(value = "/find/{id}")
	public PlantaDTO find(@PathVariable String id) throws Exception {
		return plantaServiceAPI.get(id);
	}

	@PostMapping(value = "/save/{id}")
	public ResponseEntity<String> save(@RequestBody Planta planta, @PathVariable String id) throws Exception {
		if (id == null || id.length() == 0 || id.equals("null")) {
			id = plantaServiceAPI.save(planta);
		} else {
			plantaServiceAPI.save(planta, id);
		}
		return new ResponseEntity<String>(id, HttpStatus.OK);
	}

	@GetMapping(value = "/delete/{id}")
	public ResponseEntity<PlantaDTO> delete(@PathVariable String id) throws Exception {
		PlantaDTO planta = plantaServiceAPI.get(id);
		if (planta != null) {
			plantaServiceAPI.delete(id);
		} else {
			return new ResponseEntity<PlantaDTO>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<PlantaDTO>(planta, HttpStatus.OK);
	}
	@PostMapping(value = "/addVariable/{idPlanta}/{variable}/{value}")
	public ResponseEntity<String> addVariable(@PathVariable String idPlanta, @PathVariable String variable, @PathVariable String value) throws Exception {
		plantaServiceAPI.addVariable(idPlanta, variable, value);
		return new ResponseEntity<String>(idPlanta, HttpStatus.OK);

	}
}
