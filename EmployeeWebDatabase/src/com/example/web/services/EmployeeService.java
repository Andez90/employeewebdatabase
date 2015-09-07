package com.example.web.services;

import java.util.List;

import javax.persistence.*;

import com.example.web.entities.Employee;

public class EmployeeService { 
	EntityManager manager;
	static EmployeeService service;
	
	private EmployeeService() { 
		EntityManagerFactory factory =Persistence.createEntityManagerFactory("company");
		manager = factory.createEntityManager();
	}
	
	public static EmployeeService getInstance() {
		if (service == null) {
			service = new EmployeeService();
		}
		return service; 
	}
	
	public Employee find(int id) { 
		manager.getTransaction().begin();
		Employee employee = manager.find(Employee.class, id); 
		manager.getTransaction().commit();
		return employee; 
	}
	
	public List<Employee> findAll() { 
		manager.getTransaction().begin();
		TypedQuery<Employee> query = manager.createQuery("SELECT e FROM Employee e", Employee.class);
		List<Employee> employees = query.getResultList(); 
		manager.getTransaction().commit();
		return employees; 
	}
	
	public void add(Employee employee) { 
		manager.getTransaction().begin(); 
		manager.persist(employee); 
		manager.getTransaction().commit();
	}
	public void remove(Employee employee) { 
		manager.getTransaction().begin();
		Employee managedEmployee = manager.find(Employee.class, employee.getId());
		if (managedEmployee != null) { 
			manager.remove(managedEmployee); 
			manager.getTransaction().commit();
		} 
	}
	
	public void update(Employee employee) { 
		manager.getTransaction().begin();
		Employee managedEmployee = manager.find(Employee.class, employee.getId());
		if (managedEmployee != null) {
			managedEmployee.setFirstname(employee.getFirstname());
			managedEmployee.setLastname(employee.getLastname()); managedEmployee.setSalary(employee.getSalary());
		}
		manager.getTransaction().commit(); 
	}
}