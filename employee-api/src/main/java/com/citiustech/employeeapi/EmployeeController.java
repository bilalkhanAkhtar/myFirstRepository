package com.citiustech.employeeapi;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping("/employees")
	public List<Employee> getAllEmployees() {

		return employeeService.getAllEmployees();

	}
	
	
	@RequestMapping("/employees/{id}")
	public Employee getEmployeeById(@PathVariable String id) {
		
		return employeeService.getEmployee(id);

	}
	
	
	
	@RequestMapping(method=RequestMethod.POST, value="/employees")
	public void addEmployee(@RequestBody Employee emp)
	{
		employeeService.addEmployee(emp);
		
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/employees/{id}")
	public void updateEmployee(@RequestBody Employee emp, @PathVariable String id)
	{
		employeeService.updateEmployee(id,emp);
		
	}
	
	
	@RequestMapping(method=RequestMethod.DELETE, value="/employees/{id}")
	public void deleteEmployee(@PathVariable String id)
	{
		employeeService.deleteEmployee(id);
		
	}

}
