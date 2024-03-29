package com.practices.controller;

import java.util.List;
import java.util.Map;

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

import com.practices.dto.PracticesDTO;
import com.practices.dto.SendPracticesDTO;
import com.practices.model.Practices;
import com.practices.service.PracticesServiceAPI;

@RestController
@RequestMapping(value = "/practices/api/v1/")
@CrossOrigin("*")
public class PracticesRestController {

	@Autowired
	private PracticesServiceAPI practicesServiceAPI;

	@GetMapping(value = "/all")
	public List<PracticesDTO> getAll() throws Exception {
		return practicesServiceAPI.getAll();
	}

	@GetMapping(value = "/find/{id}")
	public PracticesDTO find(@PathVariable String id) throws Exception {
		return practicesServiceAPI.get(id);
	}
	
	@GetMapping(value = "/find/student/{idStudent}")
	public List<SendPracticesDTO> findByStudentId(@PathVariable String idStudent) throws Exception {
		return practicesServiceAPI.getByStudent(idStudent);
	}
	
	@GetMapping(value = "/find/topic/{idTopic}")
	public List<String> findDatesByTopic(@PathVariable String idTopic) throws Exception {
		return practicesServiceAPI.getDatesByTopic(idTopic);
	}

	@PostMapping(value = "/save/{id}")
	public ResponseEntity<String> save(@RequestBody Practices practices, @PathVariable String id) throws Exception {
		if (id == null || id.length() == 0 || id.equals("null")) {
			id = practicesServiceAPI.save(practices);
		} else {
			practicesServiceAPI.save(practices, id);
		}
		return new ResponseEntity<String>(id, HttpStatus.OK);
	}

	@GetMapping(value = "/delete/{id}")
	public ResponseEntity<PracticesDTO> delete(@PathVariable String id) throws Exception {

		PracticesDTO agenda = practicesServiceAPI.get(id);
		if (agenda != null) {
			practicesServiceAPI.delete(id);
		} else {
			return new ResponseEntity<PracticesDTO>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<PracticesDTO>(agenda, HttpStatus.OK);
	}
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
	@GetMapping(value = "/getData/{id}")
	public ResponseEntity<Map<String,Map<String,Float>>> getData(@PathVariable String id) throws Exception {
		Map<String,Map<String,Float>> data = practicesServiceAPI.getDataPractice(id);
		
		if (data != null) {
			return new ResponseEntity<Map<String,Map<String,Float>>>(data, HttpStatus.OK);
		} else {
			return new ResponseEntity<Map<String,Map<String,Float>>>(data, HttpStatus.NO_CONTENT);
		}
	}
	@PostMapping(value = "/setInExecution/{planta}/{idPractice}")
	public ResponseEntity<String> setInExecution(@PathVariable String planta,@PathVariable String idPractice) throws Exception {
		practicesServiceAPI.practicaEnEjecucion(planta, idPractice);
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	@GetMapping(value = "/scheduledPractice/{idStudent}/{idWorkshop}")
	public SendPracticesDTO scheduledPractice(@PathVariable String idStudent,@PathVariable String idWorkshop) throws Exception {
		return practicesServiceAPI.scheduledPractice(idStudent, idWorkshop);
	}
	
	@PostMapping(value = "/setSelected/{planta}/{repeticion}")
	public ResponseEntity<String> setSelectedWeight(@PathVariable String planta,@PathVariable String repeticion,@RequestBody Map<String,Float> datos) throws Exception {
		practicesServiceAPI.datosSeleccionados(planta,repeticion, datos);
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	@GetMapping(value = "/getPractice/{idTopic}")
	public String getPractice(@PathVariable String idTopic) throws Exception {
		return practicesServiceAPI.getIdPractice(idTopic);
	}
}
