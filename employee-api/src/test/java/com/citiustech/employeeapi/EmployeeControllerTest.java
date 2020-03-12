package com.citiustech.employeeapi;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.Arrays;
import java.util.List;

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
public class EmployeeControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private EmployeeService employeeService;

	@Before
	public void setUp() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();// this stuff somebody was talking about
	}

	@Test
	public void testgetAllEmployees() throws Exception {

		Employee e1 = new Employee("1", "Ammi");
		Employee e2 = new Employee("2", "Aapa");

		List<Employee> list = Arrays.asList(e1, e2);

		when(employeeService.getAllEmployees()).thenReturn(list);

		MvcResult result = mockMvc.perform(get("/employees")).andExpect(status().isOk()).andReturn();

		ObjectMapper mapper = new ObjectMapper();

		List<Employee> actual = mapper.readValue(result.getResponse().getContentAsString(),
				new TypeReference<List<Employee>>() {
				});

		assertEquals(list, actual);

	}

	@Test
	public void testgetEmployeeById() throws Exception {
		Employee e1 = new Employee("1", "Ammi");
		when(employeeService.getEmployee("1")).thenReturn(e1);
		MvcResult result = mockMvc.perform(get("/employees/1")).andExpect(status().isOk()).andReturn();

		ObjectMapper mapper = new ObjectMapper();

		Employee actual = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Employee>() {
		});

		assertEquals(e1, actual);

	}

	@Test
	public void testaddEmployee() throws Exception {
		Employee e1 = new Employee("1", "Ammi");

		doAnswer(invocation -> {
			Object arg0 = invocation.getArgument(0);

			assertEquals(e1, arg0);
			return null;
		}).when(employeeService).addEmployee(e1);

		mockMvc.perform(MockMvcRequestBuilders.post("/employees").content(asJsonString(e1))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	
	@Test
	public void testUpdateEmployee() throws Exception {
		
		Employee e1 = new Employee("1", "Ammi");

		doAnswer(invocation -> {
			Object arg0 = invocation.getArgument(0);
			//Object arg1 = invocation.getArgument(1);
			assertEquals(e1.getEmpId(), arg0);
			//assertEquals(e1.getEmpId(), arg1);
			return null;
		}).when(employeeService).updateEmployee(e1.getEmpId(),e1);

		mockMvc.perform(MockMvcRequestBuilders.put("/employees/1").content(asJsonString(e1))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		
		
	}
	
	@Test
	public void testDeleteEmployee() throws Exception {
		
		Employee e1 = new Employee("1", "Ammi");

		doAnswer(invocation -> {
			Object arg0 = invocation.getArgument(0);
			//Object arg1 = invocation.getArgument(1);
			assertEquals(e1.getEmpId(), arg0);
			//assertEquals(e1.getEmpId(), arg1);
			return null;
		}).when(employeeService).deleteEmployee(e1.getEmpId());

		
		mockMvc.perform(delete("/employees/1")).andExpect(status().isOk()).andReturn();
		
		
	}
}
