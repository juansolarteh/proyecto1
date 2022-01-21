package com.agenda.controller;

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

import com.agenda.dto.AgendaDTO;
import com.agenda.model.Agenda;
import com.agenda.service.AgendaServiceAPI;

@RestController
@RequestMapping(value = "/agenda/api/v1/")
@CrossOrigin("*")
public class AgendaRestController {

	@Autowired
	private AgendaServiceAPI agendaServiceAPI;

	@GetMapping(value = "/all")
	public List<AgendaDTO> getAll() throws Exception {
		return agendaServiceAPI.getAll();
	}

	@GetMapping(value = "/find/{id}")
	public AgendaDTO find(@PathVariable String id) throws Exception {
		return agendaServiceAPI.get(id);
	}

	@PostMapping(value = "/save/{id}")
	public ResponseEntity<String> save(@RequestBody Agenda agenda, @PathVariable String id) throws Exception {
		if (id == null || id.length() == 0 || id.equals("null")) {
			id = agendaServiceAPI.save(agenda);
		} else {
			agendaServiceAPI.save(agenda, id);
		}
		return new ResponseEntity<String>(id, HttpStatus.OK);
	}

	@GetMapping(value = "/delete/{id}")
	public ResponseEntity<AgendaDTO> delete(@PathVariable String id) throws Exception {
		AgendaDTO agenda = agendaServiceAPI.get(id);
		if (agenda != null) {
			agendaServiceAPI.delete(id);
		} else {
			return new ResponseEntity<AgendaDTO>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<AgendaDTO>(agenda, HttpStatus.OK);
	}
}
