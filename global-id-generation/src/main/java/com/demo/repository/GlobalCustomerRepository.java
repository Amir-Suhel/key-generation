package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entity.GlobalCustomer;

public interface GlobalCustomerRepository extends JpaRepository<GlobalCustomer, Long> {

	GlobalCustomer findTopByOrderByIdDesc();
	
	GlobalCustomer findByGlobalId(Long globalId);
}
