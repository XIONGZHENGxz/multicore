import java.util.NoSuchElementException;

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
		
