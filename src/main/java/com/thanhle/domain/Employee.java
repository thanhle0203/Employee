package com.thanhle.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "EmployeeAug18")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long empId;
	private String firstName;
	private String lastName;
	private String designation;
	private double salary;
	
	
}