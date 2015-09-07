package com.example.web.viewbeans;

import com.example.web.entities.Employee;
import com.example.web.services.EmployeeService;

public class Edit { 
	private Integer id;
	private Employee employee = new Employee();
	
	public Integer getId() { 
		return id;
	}
	
	public void setId(Integer id) { 
		this.id = id;
	}
	
	public void init() {
		EmployeeService service = EmployeeService.getInstance();
		employee = service.find(id); 
	}
	
	public Employee getEmployee() { 
		return employee;
	}
	
	public void setEmployee(Employee employee) { 
		this.employee = employee;
	}
	
	public String update() {
		EmployeeService service = EmployeeService.getInstance();
		service.update(employee);
		return "OK"; 
	}
}