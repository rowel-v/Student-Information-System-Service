package com.example.sis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sis.entity.User;
import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
	
	boolean existsByFirstnameAndMiddlenameAndLastname(String firstname, String middlename, String lastname);
	
	Optional<User> findByUsername(String username);

}
