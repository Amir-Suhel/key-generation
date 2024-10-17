package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.constant.AppConstants;
import com.demo.dto.GlobalCustomerDto;
import com.demo.entity.GlobalCustomer;
import com.demo.repository.GlobalCustomerRepository;

@Service
public class GlobalCustomerServiceImpl implements GlobalCustomerService {

	@Autowired
	private GlobalCustomerRepository globalCustomerRepository;

	@Override
	public GlobalCustomer createGlobalCustomer(GlobalCustomerDto globalCustomerDto) {
		GlobalCustomer lastGlobalCustomer = globalCustomerRepository.findTopByOrderByIdDesc();
		String nextglobalID;
		if (lastGlobalCustomer != null)
			nextglobalID = generateGlobalID(lastGlobalCustomer.getGlobalId().toString());
		else
			nextglobalID = generateGlobalID(null);

		GlobalCustomer globalCustomer = new GlobalCustomer();
		globalCustomer.setGlobalId(Long.parseLong(nextglobalID));
		globalCustomer.setName(globalCustomerDto.getName());
		globalCustomer.setCity(globalCustomerDto.getCity());

		return globalCustomerRepository.save(globalCustomer);
	}

	private String generateGlobalID(String lastGlobalId) {
		long nextSequence;
		if (lastGlobalId != null && lastGlobalId.length() == 16 && lastGlobalId.startsWith(AppConstants.PREFIX)) {
			String lastSequenceStr = lastGlobalId.substring(4);
			long lastSequence = Long.parseLong(lastSequenceStr);
			nextSequence = lastSequence < AppConstants.MAX_DIGITS ? lastSequence + 1 : AppConstants.INITIAL_SEQUENCE;

		} else {
			nextSequence = AppConstants.INITIAL_SEQUENCE;
		}

		return AppConstants.PREFIX + String.format("%012d", nextSequence);
	}

	@Override
	public List<GlobalCustomer> getAll() {
		return globalCustomerRepository.findAll();
	}

	@Override
	public GlobalCustomer getGlobalCustomer(Long globalId) {
		return globalCustomerRepository.findByGlobalId(globalId);
	}

}
