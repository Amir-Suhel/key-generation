package com.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
@Table(name = "gloabl_customer_map", uniqueConstraints = { @UniqueConstraint(columnNames = "global_id") })
public class GlobalCustomer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "global_id")
	private Long globalId;

	private String name;

	private String city;

}
