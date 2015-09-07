package com.example.web.viewbeans;

import com.example.web.entities.Employee;
import com.example.web.services.EmployeeService;

public class Create {
	private Employee employee;
	
	public Create() { 
		setEmployee(new Employee());
	}
	
	public String create() {
		EmployeeService service = EmployeeService.getInstance(); 
		service.add(getEmployee());
		return "OK"; 
	}
	
	public Employee getEmployee() { 
		return employee;
	}
	
	public void setEmployee(Employee employee) { 
		this.employee = employee;
	} 
}