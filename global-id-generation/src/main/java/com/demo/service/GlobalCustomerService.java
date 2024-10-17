package com.demo.service;

import java.util.List;

import com.demo.dto.GlobalCustomerDto;
import com.demo.entity.GlobalCustomer;

public interface GlobalCustomerService {

	public GlobalCustomer createGlobalCustomer(GlobalCustomerDto globalCustomerDto);

	public List<GlobalCustomer> getAll();

	public GlobalCustomer getGlobalCustomer(Long globalId);

}
