import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
public class LockFreeStack<T>{
	AtomicReference<Node<T>> top=new AtomicReference<Node<T>>(null);

	public void push(T value){
		Node<T> node=new Node<T>(value);
		while(true){
			Node<T> oldTop=top.get();
			node.next=oldTop;
			if(top.compareAndSet(oldTop,node)) return;
			Thread.yield();
		}
	}


	public T pop() throws NoSuchElementException{
		while(true){
			Node<T> oldTop=top.get();
			if(oldTop==null) throw new NoSuchElementException();
			T value=oldTop.val;
			if(top.compareAndSet(oldTop,oldTop.next)) return value;
			else 
				Thread.yield();
		}

	}
}
		
