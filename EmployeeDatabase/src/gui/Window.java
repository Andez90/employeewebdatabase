package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Extension;

import model.EmployeeModel;
import database.Connect;

public class Window extends JFrame implements ActionListener{
	private Connect database = new Connect();
	private JTextField employeeNumber = new JTextField(3);
	private JTextField firstName = new JTextField(10);
	private JTextField lastName = new JTextField(10);
	private JTextField extention = new JTextField(5);
	private JTextField email = new JTextField(18);
	private JTextField officeCode = new JTextField(3);
	private JTextField reportsTo = new JTextField(3);
	private JTextField jobbTitle = new JTextField(10);
	private JButton addToEmployee = new JButton("Läggtill");
	private JButton editAtEmployee = new JButton("Redigera");
	private JButton removeFromEmployee = new JButton("Tabort");
	private JLabel employeeList = new JLabel();
	private JScrollPane scrol;
	private int employeeIndex;
	
	public Window() throws SQLException{
		setTitle ("EmployeeList");
		setSize (900, 600);
		setLayout(new FlowLayout());
		add (employeeNumber);
		add (firstName);
		add (lastName);
		add (extention);
		add (email);
		add (officeCode);
		add (reportsTo);
		add (jobbTitle);
		add (addToEmployee);
		addToEmployee.addActionListener(this);
		add (editAtEmployee);
		editAtEmployee.addActionListener(this);
		add (removeFromEmployee);
		removeFromEmployee.addActionListener(this);
		getList();
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setVisible (true);
	}
	
	public void getList() throws SQLException{
		this.add (employeeList);
		database.connectToDatabase();
		
		ArrayList <EmployeeModel> employeeArray = new ArrayList <EmployeeModel> (database.getEmployeeList());

		DefaultListModel defaultList = new DefaultListModel();
		JList list = new JList(defaultList);
		
		for (EmployeeModel employee: employeeArray){
			defaultList.addElement(employee.getEmployeeNumber() + " - " + employee.getFirstName() + " - " + employee.getLastName() + " - " + employee.getExtension() + " - " + employee.getEmail() + " - " + employee.getOfficeCode() + " - " + employee.getReportsTo() + " - " + employee.getJobTitle());
		}
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		scrol = new JScrollPane(list);
		scrol.setPreferredSize(new Dimension(850,500));
		
		ListSelectionListener listSelectionListener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				boolean adjust = listSelectionEvent.getValueIsAdjusting();
				if (!adjust) {
					JList list = (JList) listSelectionEvent.getSource();
					int selection[] = list.getSelectedIndices();;
					for (int i = 0, n = selection.length; i < n; i++) {
						employeeIndex = selection[i];
					}
				}
			}
		};
	
		list.addListSelectionListener(listSelectionListener);
		add(scrol);
	}
	
	public void uppdateList() throws SQLException{
		remove(scrol);
		remove(employeeList);
		getList();
		revalidate();
		repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addToEmployee){
			try {
				EmployeeModel addEmployee = new EmployeeModel();
				addEmployee.setEmployeeNumber(Integer.parseInt(employeeNumber.getText()));
				addEmployee.setFirstName(firstName.getText());
				addEmployee.setLastName(lastName.getText());
				addEmployee.setExtension(extention.getText());
				addEmployee.setEmail(email.getText());
				addEmployee.setOfficeCode(Integer.parseInt(officeCode.getText()));
				addEmployee.setReportsTo(Integer.parseInt(reportsTo.getText()));
				addEmployee.setJobTitle(jobbTitle.getText());
			
				database.addToDatabase(addEmployee);
				uppdateList();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if (e.getSource() == editAtEmployee){
			try {
				EmployeeModel editEmployee = (EmployeeModel)database.getEmployeeList().get(this.employeeIndex);
				editEmployee.setEmployeeNumber(Integer.parseInt(employeeNumber.getText()));
				editEmployee.setFirstName(firstName.getText());
				editEmployee.setLastName(lastName.getText());
				editEmployee.setExtension(extention.getText());
				editEmployee.setEmail(email.getText());
				editEmployee.setOfficeCode(Integer.parseInt(officeCode.getText()));
				editEmployee.setReportsTo(Integer.parseInt(reportsTo.getText()));
				editEmployee.setJobTitle(jobbTitle.getText());
				System.out.println(editEmployee);
				database.editAtDatabase(editEmployee);
				uppdateList();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if (e.getSource() == removeFromEmployee){
			try {
				EmployeeModel employee = (EmployeeModel)database.getEmployeeList().get(this.employeeIndex);
				database.removeFromDatabase(employee);
				uppdateList();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else{	
		}
	}
}
