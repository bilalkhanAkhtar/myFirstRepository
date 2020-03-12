package com.citiustech.employeeapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
	
	
	@Autowired
	private EmployeeRepository employeeRepository;


	public List<Employee> getAllEmployees() {

		List <Employee> list = new ArrayList<>();
		employeeRepository.findAll().forEach(list :: add);
		
		return list;
	}

	public Employee getEmployee(String id)

	{
		return employeeRepository.findById(id).get();
		//return list.stream().filter(e -> e.getEmpId().equals(id)).findAny().get();

	}
	
	public void addEmployee(Employee emp)
	{
		employeeRepository.save(emp);
		
	}

	public void updateEmployee(String id, Employee emp) {
		
		employeeRepository.save(emp);
		
	}

	public void deleteEmployee(String id) {

		//list.removeIf(t -> t.getEmpId().equals(id));
		employeeRepository.deleteById(id);
	}

}
