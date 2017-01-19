import java.util.NoSuchElementException;
class Node<T>{

	T val;
	Node<T> next;
	public Node(T elem){
		this.val=elem;
		this.next=null;
	}
}

public class LockBasedStack<T>{
	Node<T> top=null;
	public synchronized void push(T elem){
		Node<T> node=new Node<T>(elem);
		node.next=top;
		top=node;
	}

	public synchronized T pop() throws NoSuchElementException{
		if(top==null) throw new NoSuchElementException();
		T value=top.val;
		top=top.next;
		return value;
	}

}
		
