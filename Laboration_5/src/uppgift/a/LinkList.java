package uppgift.a;

public class LinkList {
	private Link first;
	private Link last;
	private int size;
	private Iterator iterator = new Iterator(this);
	
	public LinkList(){
		first = null;
		last = null;
	}

	public LinkList(LinkList oldList) {
		Link currentValue = oldList.first;
		
		while (currentValue != null){
			addLast(currentValue.emp);
			currentValue = currentValue.next;
		}
		
	}

	public void addFirst(Employee employee){
		Link link = new Link (employee);
		if (isEmpty()){
			last = link;
		}
		link.next = first;
		first = link;
		++size;
	}
	
	public void addLast(Employee employee){
		Link link = new Link(employee);
		if (isEmpty()){
			first = link;
			last = null;
		}
		else{
			last.next = link;
		}
		last = link;
		++size;
	}
	
	public void deletLast(){
			if (last == null){
			}
			else {
				if (last == first){
					last = null;
					first = null;
				}
				else{
					Link previousToTail = first;
					while (previousToTail.next != last){
						previousToTail = previousToTail.next;
					}
					last = previousToTail;
					last.next = null;
				}
				--size;
			}
	}
	
	public Employee deletFirst(){
		if (!isEmpty()){
			Link temporary = first;
			first = first.next;
			Employee employee = temporary.emp;
			--size;
			return employee;
		}
		else{
			System.out.println("List is Empty");
			return null;
		}
	}
	
	public void deletAll(){
		if (!isEmpty()){
			first = null;
			size = 0;
		}
		else
			System.out.println("List is empty");
	}
	
	public boolean isEmpty(){
		return first == null;
	}
	
	public int size(){
		size = size + iterator.size();
		return size;
	}
	
	public Iterator getIterator(){
		return iterator = new Iterator(this);
	}
	
	public void initiateWithLinkList(LinkList oldList){
		LinkList newList = new LinkList (oldList);
	}
	
	public Link getFirstLink(){
		return first;
	}

	public void displayList() {
		if (!isEmpty()){
			Link temporary = first;
			while (temporary != null){
				temporary.dispalyLink();
				temporary = temporary.next;
			}
		}
		else
			System.out.println("List is empty");
	}
	
	public void sort(Link sorting){
		last = sorting.next;
		
		if(last == null)
			return;
		
		Employee tempo;
		
		while (sorting.next != null){
			if(sorting.next.emp.getShortName().compareTo(last.emp.getShortName()) < 0){
				tempo = sorting.next.emp;
				sorting.next.emp = sorting.emp;
				sorting.emp = tempo;
			}
			sorting = sorting.next;
		}
		sort(sorting);
		displayList();
	}
}
