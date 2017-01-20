public class SingleQueue{
	int head,tail;
	Object[] buff;
	public SingleQueue(int size){
		head=0;
		tail=0;
		buff=new Object[size];
	}

	public void add(Object obe){
		while(head-tail==buff.length);
		buff[head]=obe;
		head=(head+1)%buff.length;
	}

	public Object poll(){
		while(head==tail);
		Object ret=buff[tail];
		tail=(tail+1)%buff.length;
		return ret;
	}
}

