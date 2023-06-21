package com.students.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.students.entity.StudentEntity;
import com.students.helper.StudentHelper;
import com.students.service.StudentService;

@RestController
@CrossOrigin("*")
public class StudentController {
	@Autowired
    private StudentService studentservice;
	
	  @PostMapping("/saveStudent")
	    public void upload(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
	        
	    	 response.setContentType("application/octet-stream");
	         response.setHeader("Content-Disposition", "attachment; filename=price_update_report.xlsx");
	         
	    	if (StudentHelper.checkExcelFormat(file)) {
	            //true
	            this.studentservice.save(file, response);

	        }
	    }
	    @GetMapping("/getStudent")
	    public List<StudentEntity> getAllProduct() {
	        return this.studentservice.getAllProducts();
	    }
	    
	    @DeleteMapping("/deleteStudent/{studentId}")
	    public void deleteStudent(@PathVariable("studentId") int studentId)
	    {
	    	this.studentservice.deleteStudent(studentId);
	    }

	    
	  @PutMapping("/changeStudent/{studentId}")  
	  public void updateStudent(@RequestBody StudentService student, @PathVariable("studentId") int studentId) {
		  
		  this.studentservice.updateStudent(student, studentId);
	  }
	    
}
