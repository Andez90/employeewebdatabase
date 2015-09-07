package com.example.web.entities; import javax.persistence.*;

@Entity
public class Employee {
	@Id
	@GeneratedValue 
	private int id;
	
	private String firstname;
	private String lastname;
	private double salary;
	
	public int getId() {
		return id;
	}
	
	public String getFirstname() { 
		return firstname;
	}
	
	public void setFirstname(String firstname) { 
		this.firstname = firstname;
	}
	
	public String getLastname() { 
		return lastname;
	}
	
	public void setLastname(String lastname) { 
		this.lastname = lastname;
	}
	
	public double getSalary() { 
		return salary;
	}
	
	public void setSalary(double salary) { 
		this.salary = salary;
	}
	
	@Transient
	public String getName() {
		return String.format("%s %s", getFirstname(), getLastname());
	} 
}