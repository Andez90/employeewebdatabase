package uppgift.a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TreeComunicator {
	public TreeComunicator() throws IOException{
		Tree tree = new Tree();
		String execute;
		int number;
		InputStreamReader insert = new InputStreamReader(System.in);
		BufferedReader read = new BufferedReader(insert);
		while (true){
			System.out.println("Vad vill du göra?");
			System.out.println("a = Add, d = Delete, s = Serch, t = Traverse, b = Balanced,");
			System.out.println("c = Calculate sum of all nodes in the tree");
			
			insert = new InputStreamReader(System.in);
			read = new BufferedReader(insert);
			execute = read.readLine();
			int choice = execute.charAt(0);
			
			switch(choice){
			case 'a':
				System.out.println("Ange det heltal du vill lägga till i trädet");
				insert = new InputStreamReader(System.in);
				read = new BufferedReader(insert);
				number = Integer.parseInt(read.readLine());
				tree.add(number);
				break;
			case 'd':
				System.out.println("Ange det heltal du vill lägga tabort från trädet");
				insert = new InputStreamReader(System.in);
				read = new BufferedReader(insert);
				number = Integer.parseInt(read.readLine());
				tree.delete(number);
				break;
			case 's':
				System.out.println("Ange det heltal du vill söka efter");
				insert = new InputStreamReader(System.in);
				read = new BufferedReader(insert);
				number = Integer.parseInt(read.readLine());
				tree.serch(number);
				break;
			case 't':
				System.out.println("Ange 1 = preOrder, 2 = inOrder, 3 = postOrder");
				insert = new InputStreamReader(System.in);
				read = new BufferedReader(insert);
				number = Integer.parseInt(read.readLine());
				switch(number){
				case 1:
					tree.traverse(1);
					break;
				case 2:
					tree.traverse(2);
					break;
				case 3:
					tree.traverse(3);
					break;
				}
				break;
			case 'b':
				if(tree.isBalanced())
					System.out.println("trädet är balanserat");
				else
					System.out.println("trädet är obalanserat");
				break;
			case 'c':
				tree.traverse(99);
				break;
			}
		}
	}
}
