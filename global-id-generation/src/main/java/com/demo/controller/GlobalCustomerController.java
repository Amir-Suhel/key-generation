package com.demo.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.constant.AppConstants;
import com.demo.dto.GlobalCustomerDto;
import com.demo.entity.GlobalCustomer;
import com.demo.service.GlobalCustomerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/global/customers")
public class GlobalCustomerController {

	@Autowired
	private GlobalCustomerService globalCustomerService;

	@GetMapping
	public ResponseEntity<?> getAll(@RequestHeader HttpHeaders headers) {

		log.info("Request Headers: {} " + headers);

		validateHeaders(headers);

		List<GlobalCustomer> list = globalCustomerService.getAll();

		log.info("list: {} " + list);

		HttpHeaders responseHeaders = new HttpHeaders();

		responseHeaders.set(AppConstants.HEADER_SYSTEM_ID, headers.getFirst(AppConstants.HEADER_SYSTEM_ID));
		responseHeaders.set(AppConstants.HEADER_RESPONSE_MESSAGE, "SUCCESS");
		responseHeaders.set(AppConstants.HEADER_RESPONSE_TIME, getCurrentDateTime());

		return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(list);
	}

	@GetMapping("/{globalId}")
	public ResponseEntity<?> getGlobalCustomer(@RequestHeader HttpHeaders headers, @PathVariable Long globalId) {
		validateHeaders(headers);
		GlobalCustomer globalCustomer = globalCustomerService.getGlobalCustomer(globalId);

		HttpHeaders responseHeaders = new HttpHeaders();

		responseHeaders.set(AppConstants.HEADER_SYSTEM_ID, headers.getFirst(AppConstants.HEADER_SYSTEM_ID));
		responseHeaders.set(AppConstants.HEADER_RESPONSE_MESSAGE, "SUCCESS");
		responseHeaders.set(AppConstants.HEADER_RESPONSE_TIME, getCurrentDateTime());

		return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(globalCustomer);

	}

	@PostMapping
	public ResponseEntity<?> createGlobalCustomer(@RequestHeader HttpHeaders headers,
			@RequestBody GlobalCustomerDto globalCustomerDto) {
		validateHeaders(headers);

		GlobalCustomer globalCustomer = globalCustomerService.createGlobalCustomer(globalCustomerDto);

		HttpHeaders responseHeaders = new HttpHeaders();

		responseHeaders.set(AppConstants.HEADER_SYSTEM_ID, headers.getFirst(AppConstants.HEADER_SYSTEM_ID));
		responseHeaders.set(AppConstants.HEADER_RESPONSE_MESSAGE, "SUCCESS");
		responseHeaders.set(AppConstants.HEADER_RESPONSE_TIME, getCurrentDateTime());

		return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).body(globalCustomer);

	}

	private void validateHeaders(HttpHeaders headers) {
		// Check for required headers and validate their values
		List<String> versions = headers.get(AppConstants.VERSION);
		List<String> messageIds = headers.get(AppConstants.HEADER_MESSAGE_ID);
		List<String> businessIds = headers.get(AppConstants.HEADER_BUSINESS_ID);
		List<String> systemIds = headers.get(AppConstants.HEADER_SYSTEM_ID);

		if (versions == null || versions.isEmpty() || versions.get(0).isBlank()) {
			throw new IllegalArgumentException("Header '" + AppConstants.VERSION + "' is required");
		}
		if (messageIds == null || messageIds.isEmpty() || messageIds.get(0).isBlank()) {
			throw new IllegalArgumentException("Header '" + AppConstants.HEADER_MESSAGE_ID + "' is required");
		}
		if (businessIds == null || businessIds.isEmpty() || businessIds.get(0).isBlank()) {
			throw new IllegalArgumentException("Header '" + AppConstants.HEADER_BUSINESS_ID + "' is required");
		}
		if (systemIds == null || systemIds.isEmpty() || systemIds.get(0).isBlank()) {
			throw new IllegalArgumentException("Header '" + AppConstants.HEADER_SYSTEM_ID + "' is required");
		}

	}

	private String getCurrentDateTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return LocalDateTime.now().format(formatter);
	}
}
