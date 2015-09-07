package model;

public class EmployeeModel {
	private int employeeNumber;
	private String firstName;
	private String lastName;
	private String extension;
	private String email;
	private int officeCode;
	private int reportsTo;
	private String jobTitle;
	
	public void Employee (int employeeNumber, String firstName, String lastName, String extention, String email, int officeCode, int reportsTo, String jobbTitle){
		setEmployeeNumber(employeeNumber);
		setFirstName(firstName);
		setLastName(lastName);
		setExtension(extention);
		setEmail(email);
		setOfficeCode(officeCode);
		setReportsTo(reportsTo);
		setJobTitle(jobbTitle);
	}

	public int getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(int employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(int officeCode) {
		this.officeCode = officeCode;
	}

	public int getReportsTo() {
		return reportsTo;
	}

	public void setReportsTo(int reportsTo) {
		this.reportsTo = reportsTo;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	@Override
	public String toString(){
		return "Anders";
	}
}
