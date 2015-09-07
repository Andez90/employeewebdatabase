package uppgift.a;

public class Link {
	public Employee emp;
	public Link next;
	
	public Link(Employee employee){
		emp = employee;
	}

	public void dispalyLink(){
		System.out.println(emp.getShortName() + " " + String.format("%04d", emp.getShortNumber()));
	}
}
