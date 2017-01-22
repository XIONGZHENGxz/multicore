public class SortedLinkList{
	Node head;
	public SortedLinkList(){
		head=null;
	}

	public boolean add(int item){
		
	}

	public boolean contains(int item){
		if(head==null) return false;
		Node curr=head;
		while(curr!=null){
			if(curr.val==item){
				if(curr.isDeleted==true) return false;
				else return true;
			}
			else if(curr.val>item) return false;
			else
				curr=curr.next;
		}
		return true;
	}

	public boolean remove(int item){
	}
}

class Node{
	boolean isDeleted;
	int val;
	Node next;
	public Node(int value){
		val=value;
		next=null;
	}
}

