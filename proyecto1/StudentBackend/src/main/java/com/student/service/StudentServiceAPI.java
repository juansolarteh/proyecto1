package com.student.service;


import com.student.commons.GenericServiceAPI;
import com.student.dto.StudentDTO;
import com.student.model.Student;


public interface StudentServiceAPI extends GenericServiceAPI<Student, StudentDTO>{
	StudentDTO getByEmail(String email) throws Exception;
}
