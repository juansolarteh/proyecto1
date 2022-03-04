package com.resultados.controller;

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

import com.resultados.dto.PracticesDTO;
import com.resultados.model.Practices;
import com.resultados.service.PracticesAPI;

@RestController
@RequestMapping(value = "/practices/api/v1/")
@CrossOrigin("*")
public class PracticesRestController {

	@Autowired
	private PracticesAPI practicesServiceAPI;

	@GetMapping(value = "/all")
	public List<PracticesDTO> getAll() throws Exception {
		return practicesServiceAPI.getAll();
	}

	@GetMapping(value = "/find/{id}")
	public PracticesDTO find(@PathVariable String id) throws Exception {
		return practicesServiceAPI.get(id);
	}

	@PostMapping(value = "/save/{id}")
	public ResponseEntity<String> save(@RequestBody Practices planta, @PathVariable String id) throws Exception {
		if (id == null || id.length() == 0 || id.equals("null")) {
			id = practicesServiceAPI.save(planta);
		} else {
			practicesServiceAPI.save(planta, id);
		}
		return new ResponseEntity<String>(id, HttpStatus.OK);
	}

	@GetMapping(value = "/delete/{id}")
	public ResponseEntity<PracticesDTO> delete(@PathVariable String id) throws Exception {
		PracticesDTO planta = practicesServiceAPI.get(id);
		if (planta != null) {
			practicesServiceAPI.delete(id);
		} else {
			return new ResponseEntity<PracticesDTO>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<PracticesDTO>(planta, HttpStatus.OK);
	}
	@PostMapping(value = "/addAnomalia/{idResultado}")
	public ResponseEntity<String> addAnomalia(@PathVariable String idResultado,@RequestBody String anomalia) throws Exception {
		practicesServiceAPI.reportarAnomalia(idResultado, anomalia);
		return new ResponseEntity<String>(idResultado, HttpStatus.OK);
	}
	@PostMapping(value = "/addStudent/{idResultado}/{idStudent}")
	public ResponseEntity<String> addStudent(@PathVariable String idResultado,@PathVariable String idStudent) throws Exception {
		practicesServiceAPI.addStudents(idResultado, idStudent);
		return new ResponseEntity<String>(idResultado, HttpStatus.OK);
	}
	@PostMapping(value = "/addAttende/{idResultado}/{idStudent}")
	public ResponseEntity<String> addAttende(@PathVariable String idResultado,@PathVariable String idStudent) throws Exception {
		practicesServiceAPI.addAttendees(idResultado, idStudent);
		return new ResponseEntity<String>(idResultado, HttpStatus.OK);
	}
	@PostMapping(value = "/addData/{idResultado}/{variable}/{value}")
	public ResponseEntity<String> addData(@PathVariable String idResultado,@PathVariable String variable,@PathVariable String value) throws Exception {
		practicesServiceAPI.addData(idResultado, variable, value);
		return new ResponseEntity<String>(idResultado, HttpStatus.OK);
	}
	@GetMapping(value = "/crearCSV/{id}")
	public ResponseEntity<String> crearCSV(@PathVariable String id) throws Exception {
		PracticesDTO planta = practicesServiceAPI.get(id);
		String rta="";
		if (planta != null) {
			rta=practicesServiceAPI.crearCSV(id);
		} else {
			new ResponseEntity<String>(rta, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<String>(rta, HttpStatus.OK);
	}
	
}
