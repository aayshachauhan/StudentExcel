package com.students.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student_master_table")
public class StudentMastertableEntity {
   
	@Id
	@Column(name = "studentRollno")
	private String studentRollno;
    
	@Column(name = "studentName")
	private String studentName;
	
	@Column(name = "className")
    private String className;
	
	
	public String getStudentRollno() {
		return studentRollno;
	}
	public void setStudentRollno(String studentRollno) {
		this.studentRollno = studentRollno;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		studentName = studentName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		className = className;
	}

}
