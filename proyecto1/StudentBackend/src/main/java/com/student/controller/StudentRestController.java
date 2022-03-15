package com.student.controller;

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

import com.student.dto.StudentDTO;
import com.student.model.Student;
import com.student.service.StudentServiceAPI;

@RestController
@RequestMapping(value = "/student/api/v1/")
@CrossOrigin("*")
public class StudentRestController {

	@Autowired
	private StudentServiceAPI studentServiceAPI;

	@GetMapping(value = "/all")
	public List<StudentDTO> getAll() throws Exception {
		return studentServiceAPI.getAll();
	}

	@GetMapping(value = "/find/{id}")
	public StudentDTO find(@PathVariable String id) throws Exception {
		return studentServiceAPI.get(id);
	}

	@PostMapping(value = "/save/{id}")
	public ResponseEntity<String> save(@RequestBody Student student, @PathVariable String id) throws Exception {
		if (id == null || id.length() == 0 || id.equals("null")) {
			id = studentServiceAPI.save(student);
		} else {
			studentServiceAPI.save(student, id);
		}
		return new ResponseEntity<String>(id, HttpStatus.OK);
	}

	@GetMapping(value = "/delete/{id}")
	public ResponseEntity<StudentDTO> delete(@PathVariable String id) throws Exception {
		StudentDTO student = studentServiceAPI.get(id);
		if (student != null) {
			studentServiceAPI.delete(id);
		} else {
			return new ResponseEntity<StudentDTO>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<StudentDTO>(student, HttpStatus.OK);
	}
	
	@GetMapping(value = "/find/email/{email}")
	public StudentDTO findByEmail(@PathVariable String email) throws Exception {
		return studentServiceAPI.getByEmail(email);
	}
	
	@GetMapping(value = "/login/{email}")
	public ResponseEntity<String> login(@PathVariable String email) throws Exception {
		StudentDTO student = studentServiceAPI.getByEmail(email);
		if (student != null)
			return new ResponseEntity<String>(student.getId(), HttpStatus.OK);
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(value = "/addCourse/{id_student}/{id_course}")
	public ResponseEntity<String> setCourseId(@PathVariable String id_student,@PathVariable String id_course) throws Exception {
		id_student = studentServiceAPI.setCourse(id_student,id_course);
		return new ResponseEntity<String>(id_student, HttpStatus.OK);
	}
	
	@PostMapping(value = "/deleteCourse/{id_student}/{id_course}")
	public ResponseEntity<String> deleteCourseId(@PathVariable String id_student,@PathVariable String id_course) throws Exception {
		id_student = studentServiceAPI.deleteCourse(id_student,id_course);
		return new ResponseEntity<String>(id_student, HttpStatus.OK);
	}
}
