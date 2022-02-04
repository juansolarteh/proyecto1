package com.materia.controller;

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

import com.materia.dto.MateriaDTO;
import com.materia.model.Materia;
import com.materia.service.MateriaServiceAPI;

@RestController
@RequestMapping(value = "/materia/api/v1/")
@CrossOrigin("*")
public class MateriaRestController {

	@Autowired
	private MateriaServiceAPI materiaServiceAPI;

	@GetMapping(value = "/all")
	public List<MateriaDTO> getAll() throws Exception {
		return materiaServiceAPI.getAll();
	}

	@GetMapping(value = "/find/{id}")
	public MateriaDTO find(@PathVariable String id) throws Exception {
		return materiaServiceAPI.get(id);
	}

	@PostMapping(value = "/save/{id}")
	public ResponseEntity<String> save(@RequestBody Materia materia, @PathVariable String id) throws Exception {
		if (id == null || id.length() == 0 || id.equals("null")) {
			id = materiaServiceAPI.save(materia);
		} else {
			materiaServiceAPI.save(materia, id);
		}
		return new ResponseEntity<String>(id, HttpStatus.OK);
	}

	@GetMapping(value = "/delete/{id}")
	public ResponseEntity<MateriaDTO> delete(@PathVariable String id) throws Exception {
		MateriaDTO materia = materiaServiceAPI.get(id);
		if (materia != null) {
			materiaServiceAPI.delete(id);
		} else {
			return new ResponseEntity<MateriaDTO>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<MateriaDTO>(materia, HttpStatus.OK);
	}
	
	@GetMapping(value = "/listar/{id}")
	public List<MateriaDTO> listMateriaByUsuario(@PathVariable String id) throws Exception {
		
		return materiaServiceAPI.getMateriasByUsuario(id);
		
	}
	
	@PostMapping(value = "/matricular/{id}/{codigo}")
	public ResponseEntity<Integer> matricularUsuario(@PathVariable String id,@PathVariable String codigo) throws Exception {
		
		return new ResponseEntity<Integer>(materiaServiceAPI.matricularEstudiante(id, codigo), HttpStatus.OK) ;
		
	}
	@PostMapping(value = "/desmatricular/{id}/{codigo}")
	public ResponseEntity<Boolean> desmatricularUsuario(@PathVariable String id,@PathVariable String codigo) throws Exception {
		
		return new ResponseEntity<Boolean>(materiaServiceAPI.desmatricularEstudiante(id,codigo), HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/profesor/{id}")
	public ResponseEntity<String> getNombreProfesor(@PathVariable String id) throws Exception {
		
		return new ResponseEntity<String>(materiaServiceAPI.getTeacher(id), HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/asignatura/{id}")
	public ResponseEntity<String> getNombreAsignatura(@PathVariable String id) throws Exception {
		
		return new ResponseEntity<String>(materiaServiceAPI.getSubject(id), HttpStatus.OK);
		
	}
}
