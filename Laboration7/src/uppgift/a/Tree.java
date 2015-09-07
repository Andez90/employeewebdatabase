package uppgift.a;

public class Tree {
	private class Node {
		public int key;
		public int presence = 0;
		public Node leftChild; //refferens till left child
		public Node rightChild; //refferens till rigt child
		
		//create node
		public Node(int key){
			this.key = key;
			this.presence = ++presence; //förekomsten för ett tal ökar med 1
			this.leftChild = null;
			this.rightChild = null;
		}
	}
	
	private Node root;//trädets root
	int sum; //för beräkning av trädets totala summa
	
	public void add(int key){
		//har trädet en root? om nej.
		if (root == null)
			root = new Node(key);//sätt key till root.
		//annars
		else
			addNode(key);//Annars leta rätt på rätt plats att lägga till key
	}
	
	public void addNode(int key){
		Node newNode = new Node(key);//new node to add.
		
		Node current = root; //börjar på root.
		Node parent; //för att holla koll på förälder.
		
		while(true){
			parent = current;
			//lägre
			if(key < current.key){
				current = current.leftChild;//gå till nästa på vänster sida
				// om nästa är ett löv.
				if(current == null){
					parent.leftChild = newNode;
					return;
				}
			}	
			//högre
			else if(key > current.key){
				current = current.rightChild;//gå till nästa på höger sida
				// om nästa är ett löv.
				if(current == null){
					parent.rightChild = newNode;
					return;
				}
			}
			//lika med
			else{
				++current.presence;//öka förekomsten med 1
				return;
			}
		}
	}
	
	public Boolean delete(int key){
		//om trädet är tomt
		if(root == null){
			System.out.println("tree is empty");
			return false;
		}
		
		//variabler för att hålla koll på vart i trädet man befinenr sig 
		//och om man är till vänster eller höger om föräldern
		Node current = root;
		Node parent = root;
		boolean isLeftChild = true;
		
		//letar efter den node som skall tas bort
		while(current.key != key){
			parent = current;
			
			//gå till vänster
			if(key < current.key){
				isLeftChild = true;
				current = current.leftChild;
			}
			//gå till höger
			else{
				isLeftChild = false;
				current = current.rightChild;
			}
			//om talet inte finns
			if(current == null){
				System.out.println("not found");
				return false;
			}
		}
		//om den nod som skall tas bort inte har några barn
		if(current.leftChild == null && current.rightChild == null){
			//om noden som skall tas bort är roten sätt värdet till null
			if(current == root)
				root = null;
			//om noden är ett vänster barn ta bort förälderns referens.
			else if (isLeftChild)
				parent.leftChild = null;
			//om noden är ett höger barn ta bort förälderns referens.
			else
				parent.rightChild = null;
		}
		//om noden som ska tas bort bara har ett vänsterbarn
		else if(current.rightChild == null){
			//om noden är roten
			if(current == root)
				root = current.leftChild; //roten är nu den tidigare rotens vänstra barn
			//om noden är ett vänsterbarn
			else if(isLeftChild)
				parent.leftChild = current.leftChild; //nodens förälder pekar nu till nodens vänstra barn istället för noden
			//om noden är ett högerbarn
			else
				parent.rightChild = current.leftChild; //nodens förälder pekar nu till nodens vänstra barn istället för noden
		}
		//om noden som skall tas bort bara har ett högerbarn
		else if(current.leftChild == null){
			//om noden är roten
			if(current == root)
				root = current.rightChild; //roten är nu den tidigare rotens högra barn
			//om noden är ett vänsterbarn
			else if(isLeftChild)
				parent.leftChild = current.rightChild; //nodens förälder pekar nu till nodens högra barn istället för till noden
			//om noden är ett högerbarn
			else
				parent.rightChild = current.rightChild; //nodens förälder pekar nu till nodens högra barn istället för till noden
		}
		//om noden har två barn
		else{
			Node sucsessor = getSucsessor(current);//leta rätt på närmaste högre värde
			//om noden som skall tas bort är roten
			if(current == root)
				root = sucsessor; //sucsessorn är den nya rooten
			//om noden som skall tas bort är vänsterbarn
			else if(isLeftChild)
				parent.leftChild = sucsessor; //föräldern pekar nu till sucsessorn istället för den borttagna noden
			//om noden som skall tas bort är högerbarn
			else
				parent.rightChild = sucsessor; //föräldern pekar nu till sucsessorn istället för den borttagna noden
			
			sucsessor.leftChild = current.leftChild; //sucsessorn får sin referens till den vänstra delen av trädet
		}
		return true; // noden är borttagen
	}
	
	//sucsessorn fins alltid på den borttagna nodens högra barns mest vänstra barn.
	public Node getSucsessor(Node nodeToDelete){
		Node sucsessorParent = nodeToDelete;
		Node sucsessor = nodeToDelete;
		Node current = nodeToDelete.rightChild;
		//leta efter sucsessorn med början på denborttagna nodens högerbarn
		while (current !=  null){
			sucsessorParent = sucsessor;
			sucsessor = current;
			current = current.leftChild;//gå till vänster
		}
		//om sucsessorn inte är den borttagna nodens högra värde
		if(sucsessor != nodeToDelete.rightChild){
			sucsessorParent = sucsessor.rightChild; //sucsessorns förälder pekar nu till sucsessorns högra barn då det kan finnas tal till höger om sucsessorn
			sucsessor.rightChild = nodeToDelete.rightChild;//sucsessorns pekar nu till den borttagna nodens högra barn.
		}
		return sucsessor;//returnerar sucsessorn
	}
	
	public Node serch(int key){
		Node current = root;
		//börjar på root
		while(current.key != key){
			//om key är mindre än den aktuella nodens värde
			if(key < current.key)
				current = current.leftChild;//gå åt vänster
			//annars
			else
				current = current.rightChild;//gå åt höger
			//om aktuella värdet är null
			if(current == null){
				System.out.println("key " + key + " not found");//key fanns inte i trädet
				return null;
			}
		}
		System.out.println("key " + current.key + " presence " + current.presence);
		return current;
	}
	
	public void traverse(int traverseType){
		switch(traverseType){
			case 1: 
				System.out.println("preOrder traversing");
				preOrder(root); //börja på från roten
				break;
			case 2:
				System.out.println("inOrder traversing");
				inOrder(root); //börja på från roten
				break;
			case 3:
				System.out.println("postOrder traversing");
				postOrder(root); //börja på från roten
				break;
			case 99:
				sum = 0; //sätter startsumman till 0
				System.out.println("calculating");
				calculate(root); //börja på från roten
				System.out.println("summan av trädets alla noder är " + sum); //skriver ut summan
				break;
		}
	}
	
	private void preOrder(Node localRoot){
		//medans noden inte är null
		if(localRoot != null){
			System.out.println(localRoot.key); //Skrivut
			
			preOrder(localRoot.leftChild); //gå rekusivt till vänster
			
			preOrder(localRoot.rightChild); //gå rekusivt till höger
		}
	}
	
	private void inOrder(Node localRoot){
		//medans noden inte är null
		if(localRoot != null){
			inOrder(localRoot.leftChild); //gå rekusivt till vänster
			
			System.out.println(localRoot.key); //skrivut
			
			inOrder(localRoot.rightChild); //gå rekusivt till höger
		}
	}
	
	private void postOrder(Node localRoot){
		//medans noden inte är null
		if(localRoot != null){
			postOrder(localRoot.leftChild); //gå rekusivt åt vänster
			
			postOrder(localRoot.rightChild); //gå rekusivt åt höger
			
			System.out.println(localRoot.key); //skriv ut
		}
	}
	
	//räkna ut summan (preOrder)
	public void calculate(Node localRoot){
		//mendans noden inte är null
		if(localRoot != null){
			sum = localRoot.key * localRoot.presence + sum; //beräkna summan
			System.out.println(sum); //skriv ut
			calculate(localRoot.leftChild); //gå rekusivt åt vänster
			calculate(localRoot.rightChild); //gå rekusivt åt höger
		}
	}
	
	public Boolean isBalanced(){
		return (maxHight(root) - minHight(root) <= 1); //om differensen mellan min och max är 1 eller mindre är trädet balanserat
	}
	
	//räknar ut längsta grenen
	private int maxHight(Node root){
		if(root == null)
			return 0;
		
		return Math.max(maxHight(root.leftChild), maxHight(root.rightChild)) +1;
	}
	
	//räknar ut kortaste grenen
	private int minHight(Node root){
		if(root == null)
			return 0;
		
		return Math.min(minHight(root.leftChild), minHight(root.rightChild)) +1;
	}
}
