package database;

import java.sql.*;
import java.util.ArrayList;

import model.EmployeeModel;

public class Connect {
	private static Connection connection = null;
	private static Statement statement = null;
	private static ArrayList<EmployeeModel> employeeList;
	
	public void connectToDatabase() throws SQLException{
		try{
			employeeList = new ArrayList<EmployeeModel>();
			connection = getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * from employees");
			
			while(resultSet.next()){
				EmployeeModel employee = new EmployeeModel ();

				employee.setEmployeeNumber(resultSet.getInt("employeeNumber"));
				employee.setLastName(resultSet.getString("lastName"));
				employee.setFirstName(resultSet.getString("firstName"));
				employee.setExtension(resultSet.getString("extension"));
				employee.setEmail(resultSet.getString("email"));
				employee.setOfficeCode(resultSet.getInt("officeCode"));
				employee.setReportsTo(resultSet.getInt("reportsTo"));
				employee.setJobTitle(resultSet.getString("jobTitle"));
				
				employeeList.add(employee);
			}
		}
		finally{
			try{
				if (statement != null){
					statement.close();
				}
				if (connection != null){
					connection.close();
				}
			}
			catch(SQLException exception){
				throw exception;
			}
		}
	}
	
	public ArrayList<EmployeeModel> getEmployeeList(){
		return this.employeeList;
	}
	
	public void addToDatabase(EmployeeModel employee) throws SQLException{
		try{
			connection = getConnection();
			statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO employees VALUES (" + employee.getEmployeeNumber() + ",'" + employee.getFirstName() + "','" + employee.getLastName() + "','" + employee.getExtension() + "','" + employee.getEmail() + "'," + employee.getOfficeCode() + "," + employee.getReportsTo() + ",'" + employee.getJobTitle() + "');");
			connectToDatabase();
		}
		finally{
			try{
				if (statement != null){
					statement.close();
				}
				if (connection != null){
					connection.close();
				}
			}
			catch(SQLException exception){
				throw exception;
			}
		}
	}
	
	public void editAtDatabase(EmployeeModel employee) throws SQLException{
		try{
			connection = getConnection();
			statement = connection.createStatement();			
			
			PreparedStatement preStatement = connection.prepareStatement("UPDATE employees SET firstName = ?, lastName = ?, extension = ?, email = ?, officeCode = ?, reportsTo = ?, jobTitle = ? WHERE employeeNumber = " + employee.getEmployeeNumber() + ";");
			preStatement.setString(1, employee.getFirstName());
			preStatement.setString(2, employee.getLastName());
			preStatement.setString(3, employee.getExtension());
			preStatement.setString(4, employee.getEmail());
			preStatement.setInt(5, employee.getOfficeCode());
			preStatement.setInt(6, employee.getReportsTo());
			preStatement.setString(7, employee.getJobTitle());
			
			preStatement.executeUpdate();
			preStatement.close();
			connectToDatabase();
		}
		finally{
			try{
				if (statement != null){
					statement.close();
				}
				if (connection != null){
					connection.close();
				}
			}
			catch(SQLException exception){
				throw exception;
			}
		}
	}
	
	public void removeFromDatabase(EmployeeModel employee) throws SQLException{
		try{
			connection = getConnection();
			statement = connection.createStatement();
			
			statement.executeUpdate("DELETE FROM employees WHERE employeeNumber = " + employee.getEmployeeNumber() + ";");
			connectToDatabase();
		}
		finally{
			try{
				if (statement != null){
					statement.close();
				}
				if (connection != null){
					connection.close();
				}
			}
			catch(SQLException exception){
				throw exception;
			}
		}
	}
	
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/exampledatabase?user=root");
	}
}
