package com.assignment.sgotur.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.sgotur.employee.dao.GraphDAOImpl;
import com.assignment.sgotur.employee.entity.Employee;
import com.assignment.sgotur.employee.entity.EmployeeResponse;

@RestController
public class employeesController {
	
	@Autowired
	private GraphDAOImpl graphService;
	
	@RequestMapping(value="/employees", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<EmployeeResponse> getEmployees() {
		HttpHeaders headers = getHeaders();
		System.out.println("Inside GraphController");
		List<Employee> employees=graphService.getEmployees();
		EmployeeResponse employeeResponse=new EmployeeResponse();
		employeeResponse.setEmployeeList(employees);
		return new ResponseEntity<EmployeeResponse>(employeeResponse,headers, HttpStatus.OK);
	}
	
	@RequestMapping(value="/employee", method=RequestMethod.POST)
	public ResponseEntity<Boolean> createEmployee(@RequestHeader("name") final String name, @RequestHeader("empID") final String empID) {
		HttpHeaders headers = getHeaders();
		Boolean trxResult=graphService.createEmployee(name, empID);
		return new ResponseEntity<Boolean>(trxResult, headers, HttpStatus.OK);
	}
	
	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Access-Control-Allow-Headers", "Content-Type");
		headers.add("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE");
		headers.add("Access-Control-Allow-Origin", "*");
		return headers;
	}

}
