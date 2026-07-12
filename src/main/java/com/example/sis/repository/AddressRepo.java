package com.example.sis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sis.entity.Address;

@Repository
public interface AddressRepo extends JpaRepository<Address, Integer>{

}
