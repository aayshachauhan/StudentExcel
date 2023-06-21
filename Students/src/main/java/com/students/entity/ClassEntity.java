package com.students.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "class")
public class ClassEntity {
	@Id
	@Column(name = "classCount")
    private String classCount;
	
	@Column(name = "className")
    private String className;
	
	
	public String getClassCount() {
		return classCount;
	}
	public void setClassCount(String classCount) {
		this.classCount = classCount;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}

}
