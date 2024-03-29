package com.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.student.commons.GenericServiceImpl;
import com.student.dto.StudentDTO;
import com.student.model.Student;


@Service
public class StudentServiceImpl  extends GenericServiceImpl<Student, StudentDTO> implements StudentServiceAPI{
	
	@Autowired
	private Firestore firestore;

	@Override
	public CollectionReference getCollection() {
		return firestore.collection("Students");
	}

	@Override
	public StudentDTO getByEmail(String email) throws Exception {
		return getByFieldPath("email", email);
	}

	@Override
	public String setCourse(String id_student, String id_course) throws Exception {
		StudentDTO std = get(id_student);
		Student auxStd = new Student(std.getName(), std.getSurname(), std.getEmail(), std.getCourse_id());
		auxStd.getCourse_id().add(id_course);
		return save(auxStd, id_student);
	}

	@Override
	public String deleteCourse(String id_student, String id_course) throws Exception {
		StudentDTO std = get(id_student);
		Student auxStd = new Student(std.getName(), std.getSurname(), std.getEmail(), std.getCourse_id());
		auxStd.getCourse_id().remove(id_course);
		return save(auxStd, id_student);
	}
}
