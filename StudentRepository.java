package com.students.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.students.entity.StudentEntity;

public interface StudentRepository extends JpaRepository<StudentEntity,Integer>{
	 @Modifying
	    @Transactional
	    @Query(value = "update student s SET s.course=:course where s.studentId =:studentId", nativeQuery = true)

		int updateStudentCourse(String course, String studentId);

}
