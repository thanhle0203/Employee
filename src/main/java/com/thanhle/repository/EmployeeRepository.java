package com.thanhle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thanhle.domain.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>  {
	// Find employees sorted by last name
	List<Employee> findByOrderByLastNameAsc();
	
	// Find employees sorted in descending order of their firstName
	List<Employee> findByOrderByFirstNameDesc();
	
	// Find employee with maximum empId
	Employee findTopByOrderByEmpIdDesc();
	
	// Find employee who salary is between a given range
	List<Employee> findBySalaryBetween(Double salaryStart, Double salaryEnd);
	
}
