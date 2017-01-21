public class MultiConsumerProducer{
	Object[] buff;
	int inbuf,outbuf,count;
	public MultiConsumerProducer(int size){
		buff=new Object[size];
		inbuf=0;
		outbuf=0;
		count=0;
	}

	public synchronized void deposit(Object item) throws InterruptedException{
		while(count==buff.length)
			wait();
		buff[inbuf]=item;
		inbuf=(inbuf+1)%buff.length;
		count++;
		notifyAll();
	}

	public synchronized Object fetch() throws InterruptedException{
		while(count==0) 
			wait();
		Object ret=buff[outbuf];
		outbuf=(outbuf+1)%buff.length;
		count--;
		notifyAll();
		return ret;
	}

}


