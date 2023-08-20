package com.thanhle;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;

import com.thanhle.domain.Employee;
import com.thanhle.repository.EmployeeRepository;

@SpringBootApplication
public class Day35Assignment8Application implements CommandLineRunner {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(Day35Assignment8Application.class, args);
	}
	
	@Override
	@Order(1)
	public void run(String... args) throws Exception {
		try (Scanner scanner = new Scanner(System.in)) { // use try-with-resources to close the scanner
			
			//System.out.println("Would you like to add a new employees? (yes/no)");
			String choice;
			
			do {
				System.out.println("Choose an operation: ");
				System.out.println("1. Create Employee");
				System.out.println("2. Read All Employees");
				System.out.println("3. Update Employee");
				System.out.println("4. Delete Employee");
				System.out.println("5. Find employees sorted by last name");
				System.out.println("6. Find employees sorted in descending order by first name");
				System.out.println("7. Find employee with the maximum empId");
				System.out.println("8. Find employees whose salary is between a given range");
				System.out.println("5. Exit");
				System.out.println();
				System.out.print("Enter your choice: ");
				choice = scanner.nextLine();
				
				switch (choice) {
					case "1":
						createEmployee(scanner);
						break;
					case "2":
						readAllEmployees();
						break;
					case "3":
						updateEmployee(scanner);
						break;
					case "4":
						deleteEmployee(scanner);
						break;
					
					case "5":
						findEmployeesSortedByLastName();
						break;
					case "6":
						findEmployeesSortedByFirstNameDesc();
						break;
					case "7":
						findEmployeeWithMaxEmpId();
						break;
					case "8":
						findEmployeesBySalaryRange(scanner);
						break;
					case "9":
						System.out.println("Existing...");
						break;
					default:
						System.out.println("Invalid choice! Please choose again");
				}
			} while (!choice.equals("9"));
			
			
		}
	}
	
	private void deleteEmployee(Scanner scanner) {
		System.out.println("Enter the employee ID to delete: ");
		try {
			long empId = Long.parseLong(scanner.nextLine());
			if (employeeRepository.existsById(empId)) {
				employeeRepository.deleteById(empId);
				System.out.println("Employee deleted!");
			}
			else {
				System.out.println("Employee not found.");
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid employee ID");
		}
		
	}

	private void updateEmployee(Scanner scanner) {
		System.out.println("Enter the employee ID to update: ");
		try {
			long empId = Long.parseLong(scanner.nextLine());
			if (employeeRepository.existsById(empId)) {
				Employee existingEmployee = employeeRepository.findById(empId).orElse(null);
				
				System.out.println("Enter new first name: ");
				String firstName = scanner.nextLine();
				if  (!firstName.isEmpty()) {
					existingEmployee.setFirstName(firstName);
				}
				
				System.out.println("Enter new last name: ");
				String lastName = scanner.nextLine();
				if  (!lastName.isEmpty()) {
					existingEmployee.setLastName(lastName);
				}
				
				System.out.println("Enter new designation: ");
				String designation = scanner.nextLine();
				if  (!designation.isEmpty()) {
					existingEmployee.setDesignation(designation);
				}
				
				System.out.println("Enter new salary: ");
				String salaryStr = scanner.nextLine();
				if  (!salaryStr.equalsIgnoreCase("no change")) {
					try {
						existingEmployee.setSalary(Double.parseDouble(salaryStr));
					} catch (NumberFormatException e) {
						System.out.println("Invalid input for salary");
					}
				}
				
			employeeRepository.save(existingEmployee);
			System.out.println("Employee updated");
			
			} else {
				System.out.println("Employee not found.");
			}
		
		} catch (NumberFormatException e) {
			System.out.println("Invalid employee ID.");
		}
	}

	private void readAllEmployees() {
		// Fetch all employees 
		List<Employee> employees = employeeRepository.findAll();
		if (employees.isEmpty()) {
			System.out.println("No employees found.");
		} else {
			employees.forEach(System.out::println);
		}	
		
	}

	private void createEmployee(Scanner scanner) {
		Employee newEmployee = new Employee();
		
		System.out.println("Enter first name: ");
		newEmployee.setFirstName(scanner.nextLine());
		
		System.out.println("Enter last name: ");
		newEmployee.setLastName(scanner.nextLine());
		
		System.out.println("Enter designation: ");
		newEmployee.setDesignation(scanner.nextLine());
		
		System.out.println("Enter salary: ");
		try {
			newEmployee.setSalary(Double.parseDouble(scanner.nextLine()));
		} catch (NumberFormatException e) {
			System.out.println("Invalid input for salary.");
			newEmployee.setSalary(0);
		}
		
		employeeRepository.save(newEmployee);
		System.out.println("Employee saved!");
		
		//System.out.println("Would you like to add another employee? (yes/no)");
		//response = scanner.nextLine();
	}
	
	private void findEmployeesSortedByLastName() {
		List<Employee> employees = employeeRepository.findByOrderByLastNameAsc();
		employees.forEach(System.out::println);
	}
	
	private void findEmployeesSortedByFirstNameDesc() {
		List<Employee> employees = employeeRepository.findByOrderByFirstNameDesc();
		employees.forEach(System.out::println);
	}
	
	private void findEmployeeWithMaxEmpId() {
		Employee employee = employeeRepository.findTopByOrderByEmpIdDesc();
		System.out.println(employee);
	}
	
	private void findEmployeesBySalaryRange(Scanner scanner) {
		System.out.println("Enter the starting salary range: ");
		Double startSalary = scanner.nextDouble();
		
		System.out.println("Enter the ending salary range: ");
		Double endSalary = scanner.nextDouble();
		
		List<Employee> employees = employeeRepository.findBySalaryBetween(startSalary, endSalary);
		employees.forEach(System.out::println);
	}
	
}




















