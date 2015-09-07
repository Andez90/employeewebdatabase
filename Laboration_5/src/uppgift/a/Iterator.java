package uppgift.a;

public class Iterator {
	private Link current;
	private Link previous;
	private LinkList linkList;
	private int size;
	
	public Iterator (LinkList list){
		linkList = list;
		reset ();
	}
	
	public void reset (){
		current = linkList.getFirstLink();
		previous = null;
	}
	
	public boolean atEnd (){
		return current.next == null;
	}
	
	public void nextLink (){
		previous = current;
		current = current.next;
	}
	
	public Link getCurrent (){
		return current;
	}
	
	public void insertAfter (Employee employee){
		Link newLink = new Link(employee);
		
		if (linkList.isEmpty()){
			linkList.addFirst(employee);
			current = newLink;
		}
		else{
			newLink.next = current.next;
			current.next = newLink;
			nextLink();
			++size;
		}
	}
	
	public void insertBefore (Employee employee){
		Link newLink = new Link(employee);
		
		if (previous == null){
			newLink.next = linkList.getFirstLink();
			linkList.addFirst(employee);
			reset();
		}
		else{
			newLink.next = previous.next;
			previous.next = newLink;
			current = newLink;
			++size;
		}
	}
	
	public Employee deleteCurrent (){
		Employee employee = current.emp;
		if (previous == null){
			linkList.deletFirst();
			reset ();
		}
		else{
			previous.next = current.next;
			if (atEnd()){
				reset ();
			}
			else{
				current = current.next;
			}
			--size;
		}
		return employee;
	}
	
	public int size(){
		return size;
	}
	
	public int Serch (String name){
		int counter = 1;
		reset ();
		if(linkList.isEmpty()){
			return -1;
		}
		
		while (name.compareTo(current.emp.getShortName()) != 0 && current.next != null){
			nextLink();
			counter++;
		}
		if (current.emp.getShortName().compareTo(name) == 0){
			System.out.println("Anställd; " + name + " Har nummer; " + String.format("%04d", current.emp.getShortNumber()) + ". " + counter + " Jämförelser utfördes.");
			return 0;
		}
		System.out.println(name + " finns inte i listan!");
		return -1;
	}
}