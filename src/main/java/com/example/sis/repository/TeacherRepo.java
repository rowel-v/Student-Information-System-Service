package com.example.sis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sis.entity.Teacher;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, Integer>{

}
