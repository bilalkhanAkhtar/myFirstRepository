package com.citiustech.employeeapi;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.mockito.Mockito.doAnswer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { EmployeeApiApplication.class })
public class EmployeeServiceTest {


	@MockBean
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeService employeeService;

	
	@Test
	public void testgetAllEmployees() throws Exception {

		Employee e1 = new Employee("1", "Ammi");
		Employee e2 = new Employee("2", "Aapa");

		List<Employee> list = Arrays.asList(e1, e2);

		when(employeeRepository.findAll()).thenReturn(list);

		assertEquals(2, employeeService.getAllEmployees().size());

	}

	@Test
	public void testgetEmployeeById() throws Exception {
		Employee e1 = new Employee("1", "Ammi");
		Optional <Employee> opt = Optional.ofNullable(e1);
		when(employeeRepository.findById("1")).thenReturn(opt);
		
		
		

		assertEquals(e1, employeeService.getEmployee("1"));

	}

	@Test
	public void testaddEmployee() throws Exception {
		Employee e1 = new Employee("1", "Ammi");

		doAnswer(invocation -> {
			Object arg0 = invocation.getArgument(0);

			assertEquals(e1, arg0);
			return null;
		}).when(employeeRepository).save(e1);

		employeeService.addEmployee(e1);

	}

	@Test
	public void testupdateEmployee() throws Exception {
		Employee e1 = new Employee("1", "Ammi");

		doAnswer(invocation -> {
			Object arg0 = invocation.getArgument(0);

			assertEquals(e1, arg0);
			return null;
		}).when(employeeRepository).save(e1);

		employeeService.updateEmployee(e1.getEmpId(),e1);

	}
	
	@Test
	public void testDeleteEmployee() throws Exception {
		Employee e1 = new Employee("1", "Ammi");

		doAnswer(invocation -> {
			Object arg0 = invocation.getArgument(0);
			
			assertEquals(e1.getEmpId(), arg0);
			return null;
		}).when(employeeRepository).deleteById(e1.getEmpId());

		employeeService.deleteEmployee(e1.getEmpId());

	}
	
}
