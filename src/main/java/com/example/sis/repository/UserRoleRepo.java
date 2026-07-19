package com.example.sis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sis.entity.UserRole;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, Integer>{
}
