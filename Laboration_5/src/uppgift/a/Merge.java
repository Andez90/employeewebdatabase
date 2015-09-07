package uppgift.a;

public class Merge {
	public LinkList sort(LinkList toSort){
		LinkList left = new LinkList();
		LinkList right = new LinkList();
		
		if(toSort.size() <= 1){
			return toSort;
		}
		
		for(int i = 0; i <= (toSort.size() / 2); i++){
			left.addLast(toSort.deletFirst());
		}
		
		while(!toSort.isEmpty()){
			right.addLast(toSort.deletFirst());
		}
		
		left = sort(left);
		right = sort(right);
		
		return merge(left, right);
	}
	
	private LinkList merge(LinkList left, LinkList right){
		LinkList sorted = new LinkList();
		while(!left.isEmpty() && !right.isEmpty()){
			if(left.getFirstLink().emp.getShortName().compareToIgnoreCase(right.getFirstLink().emp.getShortName()) > 0){
				sorted.addLast(right.deletFirst());
			}
			else{
				sorted.addLast(left.deletFirst());
			}
		}
		
		while(!left.isEmpty()){
			sorted.addLast(left.deletFirst());
		}
		while(!right.isEmpty()){
			sorted.addLast(right.deletFirst());
		}
		return sorted;
	}
}
