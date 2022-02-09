package com.practica.controller;

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

import com.practica.dto.PracticaDTO;
import com.practica.model.Practica;
import com.practica.service.PracticaServiceAPI;

@RestController
@RequestMapping(value = "/practica/api/v1/")
@CrossOrigin("*")
public class PracticaRestController {

	@Autowired
	private PracticaServiceAPI practicaServiceAPI;

	@GetMapping(value = "/all")
	public List<PracticaDTO> getAll() throws Exception {
		return practicaServiceAPI.getAll();
	}

	@GetMapping(value = "/find/{id}")
	public PracticaDTO find(@PathVariable String id) throws Exception {
		return practicaServiceAPI.get(id);
	}

	@PostMapping(value = "/save/{id}")
	public ResponseEntity<String> save(@RequestBody Practica practica, @PathVariable String id) throws Exception {
		if (id == null || id.length() == 0 || id.equals("null")) {
			id = practicaServiceAPI.save(practica);
		} else {
			practicaServiceAPI.save(practica, id);
		}
		return new ResponseEntity<String>(id, HttpStatus.OK);
	}

	@GetMapping(value = "/delete/{id}")
	public ResponseEntity<PracticaDTO> delete(@PathVariable String id) throws Exception {
		PracticaDTO practica = practicaServiceAPI.get(id);
		if (practica != null) {
			practicaServiceAPI.delete(id);
		} else {
			return new ResponseEntity<PracticaDTO>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<PracticaDTO>(practica, HttpStatus.OK);
	}
	
	@GetMapping(value = "/listar/{id}")
	public List<PracticaDTO> listarPracticas(@PathVariable String id) throws Exception {
		return practicaServiceAPI.getPracticas(id);
	}
	
}
