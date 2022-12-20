package com.students.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.students.entity.StudentEntity;
import com.students.helper.StudentHelper;
import com.students.repository.StudentRepository;

@Service
public class StudentService {


	@Autowired
	private StudentRepository studentrepository;
	
	@Autowired
	private StudentHelper studenthelper;
	
	  public void save(MultipartFile file, HttpServletResponse response) {

	        try {
	        //	Helper helper = new Helper();
	        	
	            List<StudentEntity> products = studenthelper.convertExcelToListOfProduct(file.getInputStream(), response);
	            this.studentrepository.saveAll(products);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	    }
	  
	  public List<StudentEntity> getAllProducts() {
	        return this.studentrepository.findAll();
	    }

	public void deleteStudent(int studentId) {
		// TODO Auto-generated method stub
		
	}

	public void updateStudent(StudentService student, int studentId) {
		// TODO Auto-generated method stub
		
		
	}
	
}
