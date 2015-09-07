package uppgift.a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PhoneBook {
	public PhoneBook() throws IOException{
		LinkList list = new LinkList();
		Iterator iterator = list.getIterator();
		Employee emp;
		String name;
		String string;
		int maxLenght = 4;
		int number;
		InputStreamReader insert = new InputStreamReader(System.in);
		BufferedReader read = new BufferedReader(insert);
		
		while (true){
			System.out.println("Vad vill du göra?");
			System.out.println("s = showList, r = reset, n = nextLink, g = getCurrentLink, b = addBefore,");
			System.out.println("a = addAfter, e = eraseCurrentLink, d = sortLinksDecending, f = findLink");
			
			insert = new InputStreamReader(System.in);
			read = new BufferedReader(insert);
			String execute = read.readLine();
			int choice = execute.charAt(0);
			
			switch(choice){
			case 's':
				if(!list.isEmpty()){
					list.displayList();
				}
				else{
					System.out.println("listan är tom!");
				}
				break;
			case 'r':
				iterator.reset();
				break;
			case 'n':
				if(!list.isEmpty() || iterator.atEnd()){
					iterator.nextLink();
				}
				else{
					System.out.println("Listan är tom eller du är på sista länken.");
				}
				break;
			case 'g':
				if(!list.isEmpty()){
					emp = iterator.getCurrent().emp;
					System.out.println("return: " + emp.getShortName() + " " + String.format("%04d", emp.getShortNumber()));
				}
				else{
					System.out.println("listan är tom!");
				}
				break;
			case 'b':
				System.out.println("Ange namnförkortningen på den person du vill läggatill;");
				insert = new InputStreamReader(System.in);
				read = new BufferedReader(insert);
				name = read.readLine();
				name = name.substring(0, maxLenght);
				
				System.out.println("Ange telefonnummer till den person du vill läggatill;");
				insert = new InputStreamReader(System.in);
				read = new BufferedReader(insert);
				string = read.readLine();
				string = string.substring(0, maxLenght);
				number = Integer.parseInt(string);
				
				while(iterator.Serch(name) == 0){
					System.out.println("Det finns redan en person med den namnförkortningen vänligen försök igen!");
					insert = new InputStreamReader(System.in);
					read = new BufferedReader(insert);
					name = read.readLine();
					name = name.substring(0, maxLenght);
				}
				
				emp = new Employee (name, number);
				iterator.insertBefore(emp);
				break;
			case 'a':
				System.out.println("Ange namnförkortningen på den person du vill läggatill;");
				insert = new InputStreamReader(System.in);
				read = new BufferedReader(insert);
				name = read.readLine();
				name = name.substring(0, maxLenght);
				
				System.out.println("Ange telefonnummer till den person du vill läggatill;");
				insert = new InputStreamReader(System.in);
				read = new BufferedReader(insert);
				string = read.readLine();
				string = string.substring(0, maxLenght);
				number = Integer.parseInt(string);
				
				while(iterator.Serch(name) == 0){
					System.out.println("Det finns redan en person med den namnförkortningen vänligen försök igen!");
					insert = new InputStreamReader(System.in);
					read = new BufferedReader(insert);
					name = read.readLine();
					name = name.substring(0, maxLenght);
				}
				emp = new Employee (name, number);
				iterator.insertAfter(emp);
				break;
			case 'e':
				if(!list.isEmpty()){
					emp = iterator.deleteCurrent();
					System.out.println(emp.getShortName() + " " + String.format("%04d", emp.getShortNumber()) + " har tagits bort.");
				}
				else{
					System.out.println("det går inte att ta bort!");
				}
				break;
			case 'd':
				Merge merge = new Merge();
				list = merge.sort(list);
				list.displayList();
				break;
			case 'f':
				System.out.println("Ange namnförkortningen på den person du vill ha kortnummer till!");
				insert = new InputStreamReader(System.in);
				read = new BufferedReader(insert);
				name = read.readLine();
				iterator.Serch(name);
				break;
			}
		}
	}
}