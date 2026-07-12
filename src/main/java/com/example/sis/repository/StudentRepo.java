package com.example.sis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sis.entity.Student;
import java.util.Optional;


@Repository
public interface StudentRepo extends JpaRepository<Student, Integer>{
	
	Optional<Student> findByStudentNumber(String studentNumber);

}
