package com.example.web.viewbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import com.example.web.entities.Employee;
import com.example.web.services.EmployeeService;

public class Index {
	List<Employee> employees = new ArrayList<Employee>();
	EmployeeService service;
	
	public void pageInit() {
		service = EmployeeService.getInstance();
		employees = service.findAll(); 
	}
	
	public List<Employee> getEmployees() { 
		return employees;
	}
	
	public String create() {
		return "OK"; 
	}
	
	public String delete(Employee employee) { 
		service.remove(employee);
		return "OK"; 
	}
}